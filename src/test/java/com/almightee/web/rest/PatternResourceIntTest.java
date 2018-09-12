package com.almightee.web.rest;

import com.almightee.AlmighteeApp;

import com.almightee.domain.Pattern;
import com.almightee.repository.PatternRepository;
import com.almightee.repository.search.PatternSearchRepository;
import com.almightee.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

import com.almightee.domain.enumeration.Theme;
/**
 * Test class for the PatternResource REST controller.
 *
 * @see PatternResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlmighteeApp.class)
public class PatternResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final Theme DEFAULT_THEME = Theme.NATURE;
    private static final Theme UPDATED_THEME = Theme.ANIME;

    @Autowired
    private PatternRepository patternRepository;

    /**
     * This repository is mocked in the com.almightee.repository.search test package.
     *
     * @see com.almightee.repository.search.PatternSearchRepositoryMockConfiguration
     */
    @Autowired
    private PatternSearchRepository mockPatternSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPatternMockMvc;

    private Pattern pattern;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatternResource patternResource = new PatternResource(patternRepository, mockPatternSearchRepository);
        this.restPatternMockMvc = MockMvcBuilders.standaloneSetup(patternResource)
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
    public static Pattern createEntity(EntityManager em) {
        Pattern pattern = new Pattern()
            .name(DEFAULT_NAME)
            .author(DEFAULT_AUTHOR)
            .imageURL(DEFAULT_IMAGE_URL)
            .price(DEFAULT_PRICE)
            .theme(DEFAULT_THEME);
        return pattern;
    }

    @Before
    public void initTest() {
        pattern = createEntity(em);
    }

    @Test
    @Transactional
    public void createPattern() throws Exception {
        int databaseSizeBeforeCreate = patternRepository.findAll().size();

        // Create the Pattern
        restPatternMockMvc.perform(post("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pattern)))
            .andExpect(status().isCreated());

        // Validate the Pattern in the database
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeCreate + 1);
        Pattern testPattern = patternList.get(patternList.size() - 1);
        assertThat(testPattern.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPattern.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testPattern.getImageURL()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testPattern.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPattern.getTheme()).isEqualTo(DEFAULT_THEME);

        // Validate the Pattern in Elasticsearch
        verify(mockPatternSearchRepository, times(1)).save(testPattern);
    }

    @Test
    @Transactional
    public void createPatternWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patternRepository.findAll().size();

        // Create the Pattern with an existing ID
        pattern.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatternMockMvc.perform(post("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pattern)))
            .andExpect(status().isBadRequest());

        // Validate the Pattern in the database
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pattern in Elasticsearch
        verify(mockPatternSearchRepository, times(0)).save(pattern);
    }

    @Test
    @Transactional
    public void getAllPatterns() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);

        // Get all the patternList
        restPatternMockMvc.perform(get("/api/patterns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pattern.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].imageURL").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME.toString())));
    }
    
    @Test
    @Transactional
    public void getPattern() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);

        // Get the pattern
        restPatternMockMvc.perform(get("/api/patterns/{id}", pattern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pattern.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.imageURL").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPattern() throws Exception {
        // Get the pattern
        restPatternMockMvc.perform(get("/api/patterns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePattern() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);

        int databaseSizeBeforeUpdate = patternRepository.findAll().size();

        // Update the pattern
        Pattern updatedPattern = patternRepository.findById(pattern.getId()).get();
        // Disconnect from session so that the updates on updatedPattern are not directly saved in db
        em.detach(updatedPattern);
        updatedPattern
            .name(UPDATED_NAME)
            .author(UPDATED_AUTHOR)
            .imageURL(UPDATED_IMAGE_URL)
            .price(UPDATED_PRICE)
            .theme(UPDATED_THEME);

        restPatternMockMvc.perform(put("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPattern)))
            .andExpect(status().isOk());

        // Validate the Pattern in the database
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeUpdate);
        Pattern testPattern = patternList.get(patternList.size() - 1);
        assertThat(testPattern.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPattern.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testPattern.getImageURL()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testPattern.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPattern.getTheme()).isEqualTo(UPDATED_THEME);

        // Validate the Pattern in Elasticsearch
        verify(mockPatternSearchRepository, times(1)).save(testPattern);
    }

    @Test
    @Transactional
    public void updateNonExistingPattern() throws Exception {
        int databaseSizeBeforeUpdate = patternRepository.findAll().size();

        // Create the Pattern

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatternMockMvc.perform(put("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pattern)))
            .andExpect(status().isBadRequest());

        // Validate the Pattern in the database
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pattern in Elasticsearch
        verify(mockPatternSearchRepository, times(0)).save(pattern);
    }

    @Test
    @Transactional
    public void deletePattern() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);

        int databaseSizeBeforeDelete = patternRepository.findAll().size();

        // Get the pattern
        restPatternMockMvc.perform(delete("/api/patterns/{id}", pattern.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pattern in Elasticsearch
        verify(mockPatternSearchRepository, times(1)).deleteById(pattern.getId());
    }

    @Test
    @Transactional
    public void searchPattern() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);
        when(mockPatternSearchRepository.search(queryStringQuery("id:" + pattern.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pattern), PageRequest.of(0, 1), 1));
        // Search the pattern
        restPatternMockMvc.perform(get("/api/_search/patterns?query=id:" + pattern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pattern.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].imageURL").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pattern.class);
        Pattern pattern1 = new Pattern();
        pattern1.setId(1L);
        Pattern pattern2 = new Pattern();
        pattern2.setId(pattern1.getId());
        assertThat(pattern1).isEqualTo(pattern2);
        pattern2.setId(2L);
        assertThat(pattern1).isNotEqualTo(pattern2);
        pattern1.setId(null);
        assertThat(pattern1).isNotEqualTo(pattern2);
    }
}
