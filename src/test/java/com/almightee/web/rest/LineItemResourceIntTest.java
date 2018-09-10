package com.almightee.web.rest;

import com.almightee.AlmighteeApp;

import com.almightee.domain.LineItem;
import com.almightee.repository.LineItemRepository;
import com.almightee.repository.search.LineItemSearchRepository;
import com.almightee.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.almightee.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LineItemResource REST controller.
 *
 * @see LineItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlmighteeApp.class)
public class LineItemResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private LineItemRepository lineItemRepository;

    /**
     * This repository is mocked in the com.almightee.repository.search test package.
     *
     * @see com.almightee.repository.search.LineItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private LineItemSearchRepository mockLineItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLineItemMockMvc;

    private LineItem lineItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LineItemResource lineItemResource = new LineItemResource(lineItemRepository, mockLineItemSearchRepository);
        this.restLineItemMockMvc = MockMvcBuilders.standaloneSetup(lineItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LineItem createEntity(EntityManager em) {
        LineItem lineItem = new LineItem()
            .quantity(DEFAULT_QUANTITY);
        return lineItem;
    }

    @Before
    public void initTest() {
        lineItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLineItem() throws Exception {
        int databaseSizeBeforeCreate = lineItemRepository.findAll().size();

        // Create the LineItem
        restLineItemMockMvc.perform(post("/api/line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineItem)))
            .andExpect(status().isCreated());

        // Validate the LineItem in the database
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeCreate + 1);
        LineItem testLineItem = lineItemList.get(lineItemList.size() - 1);
        assertThat(testLineItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);

        // Validate the LineItem in Elasticsearch
        verify(mockLineItemSearchRepository, times(1)).save(testLineItem);
    }

    @Test
    @Transactional
    public void createLineItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lineItemRepository.findAll().size();

        // Create the LineItem with an existing ID
        lineItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineItemMockMvc.perform(post("/api/line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineItem)))
            .andExpect(status().isBadRequest());

        // Validate the LineItem in the database
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the LineItem in Elasticsearch
        verify(mockLineItemSearchRepository, times(0)).save(lineItem);
    }

    @Test
    @Transactional
    public void getAllLineItems() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

        // Get all the lineItemList
        restLineItemMockMvc.perform(get("/api/line-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getLineItem() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

        // Get the lineItem
        restLineItemMockMvc.perform(get("/api/line-items/{id}", lineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lineItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingLineItem() throws Exception {
        // Get the lineItem
        restLineItemMockMvc.perform(get("/api/line-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineItem() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

        int databaseSizeBeforeUpdate = lineItemRepository.findAll().size();

        // Update the lineItem
        LineItem updatedLineItem = lineItemRepository.findById(lineItem.getId()).get();
        // Disconnect from session so that the updates on updatedLineItem are not directly saved in db
        em.detach(updatedLineItem);
        updatedLineItem
            .quantity(UPDATED_QUANTITY);

        restLineItemMockMvc.perform(put("/api/line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLineItem)))
            .andExpect(status().isOk());

        // Validate the LineItem in the database
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeUpdate);
        LineItem testLineItem = lineItemList.get(lineItemList.size() - 1);
        assertThat(testLineItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);

        // Validate the LineItem in Elasticsearch
        verify(mockLineItemSearchRepository, times(1)).save(testLineItem);
    }

    @Test
    @Transactional
    public void updateNonExistingLineItem() throws Exception {
        int databaseSizeBeforeUpdate = lineItemRepository.findAll().size();

        // Create the LineItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLineItemMockMvc.perform(put("/api/line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineItem)))
            .andExpect(status().isBadRequest());

        // Validate the LineItem in the database
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LineItem in Elasticsearch
        verify(mockLineItemSearchRepository, times(0)).save(lineItem);
    }

    @Test
    @Transactional
    public void deleteLineItem() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

        int databaseSizeBeforeDelete = lineItemRepository.findAll().size();

        // Get the lineItem
        restLineItemMockMvc.perform(delete("/api/line-items/{id}", lineItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LineItem> lineItemList = lineItemRepository.findAll();
        assertThat(lineItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LineItem in Elasticsearch
        verify(mockLineItemSearchRepository, times(1)).deleteById(lineItem.getId());
    }

    @Test
    @Transactional
    public void searchLineItem() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);
        when(mockLineItemSearchRepository.search(queryStringQuery("id:" + lineItem.getId())))
            .thenReturn(Collections.singletonList(lineItem));
        // Search the lineItem
        restLineItemMockMvc.perform(get("/api/_search/line-items?query=id:" + lineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineItem.class);
        LineItem lineItem1 = new LineItem();
        lineItem1.setId(1L);
        LineItem lineItem2 = new LineItem();
        lineItem2.setId(lineItem1.getId());
        assertThat(lineItem1).isEqualTo(lineItem2);
        lineItem2.setId(2L);
        assertThat(lineItem1).isNotEqualTo(lineItem2);
        lineItem1.setId(null);
        assertThat(lineItem1).isNotEqualTo(lineItem2);
    }
}
