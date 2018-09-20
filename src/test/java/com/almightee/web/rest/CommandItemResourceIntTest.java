package com.almightee.web.rest;

import com.almightee.AlmighteeApp;

import com.almightee.domain.CommandItem;
import com.almightee.repository.CommandItemRepository;
import com.almightee.repository.search.CommandItemSearchRepository;
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

import com.almightee.domain.enumeration.Color;
import com.almightee.domain.enumeration.Size;
/**
 * Test class for the CommandItemResource REST controller.
 *
 * @see CommandItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlmighteeApp.class)
public class CommandItemResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color UPDATED_COLOR = Color.BLACK;

    private static final Size DEFAULT_SIZE = Size.S;
    private static final Size UPDATED_SIZE = Size.M;

    @Autowired
    private CommandItemRepository commandItemRepository;

    /**
     * This repository is mocked in the com.almightee.repository.search test package.
     *
     * @see com.almightee.repository.search.CommandItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommandItemSearchRepository mockCommandItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommandItemMockMvc;

    private CommandItem commandItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommandItemResource commandItemResource = new CommandItemResource(commandItemRepository, mockCommandItemSearchRepository);
        this.restCommandItemMockMvc = MockMvcBuilders.standaloneSetup(commandItemResource)
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
    public static CommandItem createEntity(EntityManager em) {
        CommandItem commandItem = new CommandItem()
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE)
            .color(DEFAULT_COLOR)
            .size(DEFAULT_SIZE);
        return commandItem;
    }

    @Before
    public void initTest() {
        commandItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandItem() throws Exception {
        int databaseSizeBeforeCreate = commandItemRepository.findAll().size();

        // Create the CommandItem
        restCommandItemMockMvc.perform(post("/api/command-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandItem)))
            .andExpect(status().isCreated());

        // Validate the CommandItem in the database
        List<CommandItem> commandItemList = commandItemRepository.findAll();
        assertThat(commandItemList).hasSize(databaseSizeBeforeCreate + 1);
        CommandItem testCommandItem = commandItemList.get(commandItemList.size() - 1);
        assertThat(testCommandItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCommandItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCommandItem.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testCommandItem.getSize()).isEqualTo(DEFAULT_SIZE);

        // Validate the CommandItem in Elasticsearch
        verify(mockCommandItemSearchRepository, times(1)).save(testCommandItem);
    }

    @Test
    @Transactional
    public void createCommandItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandItemRepository.findAll().size();

        // Create the CommandItem with an existing ID
        commandItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandItemMockMvc.perform(post("/api/command-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandItem)))
            .andExpect(status().isBadRequest());

        // Validate the CommandItem in the database
        List<CommandItem> commandItemList = commandItemRepository.findAll();
        assertThat(commandItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommandItem in Elasticsearch
        verify(mockCommandItemSearchRepository, times(0)).save(commandItem);
    }

    @Test
    @Transactional
    public void getAllCommandItems() throws Exception {
        // Initialize the database
        commandItemRepository.saveAndFlush(commandItem);

        // Get all the commandItemList
        restCommandItemMockMvc.perform(get("/api/command-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())));
    }
    
    @Test
    @Transactional
    public void getCommandItem() throws Exception {
        // Initialize the database
        commandItemRepository.saveAndFlush(commandItem);

        // Get the commandItem
        restCommandItemMockMvc.perform(get("/api/command-items/{id}", commandItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commandItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommandItem() throws Exception {
        // Get the commandItem
        restCommandItemMockMvc.perform(get("/api/command-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandItem() throws Exception {
        // Initialize the database
        commandItemRepository.saveAndFlush(commandItem);

        int databaseSizeBeforeUpdate = commandItemRepository.findAll().size();

        // Update the commandItem
        CommandItem updatedCommandItem = commandItemRepository.findById(commandItem.getId()).get();
        // Disconnect from session so that the updates on updatedCommandItem are not directly saved in db
        em.detach(updatedCommandItem);
        updatedCommandItem
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .color(UPDATED_COLOR)
            .size(UPDATED_SIZE);

        restCommandItemMockMvc.perform(put("/api/command-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommandItem)))
            .andExpect(status().isOk());

        // Validate the CommandItem in the database
        List<CommandItem> commandItemList = commandItemRepository.findAll();
        assertThat(commandItemList).hasSize(databaseSizeBeforeUpdate);
        CommandItem testCommandItem = commandItemList.get(commandItemList.size() - 1);
        assertThat(testCommandItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCommandItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCommandItem.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testCommandItem.getSize()).isEqualTo(UPDATED_SIZE);

        // Validate the CommandItem in Elasticsearch
        verify(mockCommandItemSearchRepository, times(1)).save(testCommandItem);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandItem() throws Exception {
        int databaseSizeBeforeUpdate = commandItemRepository.findAll().size();

        // Create the CommandItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandItemMockMvc.perform(put("/api/command-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandItem)))
            .andExpect(status().isBadRequest());

        // Validate the CommandItem in the database
        List<CommandItem> commandItemList = commandItemRepository.findAll();
        assertThat(commandItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommandItem in Elasticsearch
        verify(mockCommandItemSearchRepository, times(0)).save(commandItem);
    }

    @Test
    @Transactional
    public void deleteCommandItem() throws Exception {
        // Initialize the database
        commandItemRepository.saveAndFlush(commandItem);

        int databaseSizeBeforeDelete = commandItemRepository.findAll().size();

        // Get the commandItem
        restCommandItemMockMvc.perform(delete("/api/command-items/{id}", commandItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommandItem> commandItemList = commandItemRepository.findAll();
        assertThat(commandItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommandItem in Elasticsearch
        verify(mockCommandItemSearchRepository, times(1)).deleteById(commandItem.getId());
    }

    @Test
    @Transactional
    public void searchCommandItem() throws Exception {
        // Initialize the database
        commandItemRepository.saveAndFlush(commandItem);
        when(mockCommandItemSearchRepository.search(queryStringQuery("id:" + commandItem.getId())))
            .thenReturn(Collections.singletonList(commandItem));
        // Search the commandItem
        restCommandItemMockMvc.perform(get("/api/_search/command-items?query=id:" + commandItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandItem.class);
        CommandItem commandItem1 = new CommandItem();
        commandItem1.setId(1L);
        CommandItem commandItem2 = new CommandItem();
        commandItem2.setId(commandItem1.getId());
        assertThat(commandItem1).isEqualTo(commandItem2);
        commandItem2.setId(2L);
        assertThat(commandItem1).isNotEqualTo(commandItem2);
        commandItem1.setId(null);
        assertThat(commandItem1).isNotEqualTo(commandItem2);
    }
}
