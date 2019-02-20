package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.DefterTuru;
import com.yavuzturtelekom.repository.DefterTuruRepository;
import com.yavuzturtelekom.service.DefterTuruService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DefterTuruResource REST controller.
 *
 * @see DefterTuruResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class DefterTuruResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    @Autowired
    private DefterTuruRepository defterTuruRepository;

    @Autowired
    private DefterTuruService defterTuruService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDefterTuruMockMvc;

    private DefterTuru defterTuru;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DefterTuruResource defterTuruResource = new DefterTuruResource(defterTuruService);
        this.restDefterTuruMockMvc = MockMvcBuilders.standaloneSetup(defterTuruResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DefterTuru createEntity(EntityManager em) {
        DefterTuru defterTuru = new DefterTuru()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA);
        return defterTuru;
    }

    @Before
    public void initTest() {
        defterTuru = createEntity(em);
    }

    @Test
    @Transactional
    public void createDefterTuru() throws Exception {
        int databaseSizeBeforeCreate = defterTuruRepository.findAll().size();

        // Create the DefterTuru
        restDefterTuruMockMvc.perform(post("/api/defter-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defterTuru)))
            .andExpect(status().isCreated());

        // Validate the DefterTuru in the database
        List<DefterTuru> defterTuruList = defterTuruRepository.findAll();
        assertThat(defterTuruList).hasSize(databaseSizeBeforeCreate + 1);
        DefterTuru testDefterTuru = defterTuruList.get(defterTuruList.size() - 1);
        assertThat(testDefterTuru.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testDefterTuru.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
    }

    @Test
    @Transactional
    public void createDefterTuruWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = defterTuruRepository.findAll().size();

        // Create the DefterTuru with an existing ID
        defterTuru.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDefterTuruMockMvc.perform(post("/api/defter-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defterTuru)))
            .andExpect(status().isBadRequest());

        // Validate the DefterTuru in the database
        List<DefterTuru> defterTuruList = defterTuruRepository.findAll();
        assertThat(defterTuruList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = defterTuruRepository.findAll().size();
        // set the field null
        defterTuru.setAd(null);

        // Create the DefterTuru, which fails.

        restDefterTuruMockMvc.perform(post("/api/defter-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defterTuru)))
            .andExpect(status().isBadRequest());

        List<DefterTuru> defterTuruList = defterTuruRepository.findAll();
        assertThat(defterTuruList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDefterTurus() throws Exception {
        // Initialize the database
        defterTuruRepository.saveAndFlush(defterTuru);

        // Get all the defterTuruList
        restDefterTuruMockMvc.perform(get("/api/defter-turus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defterTuru.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())));
    }
    
    @Test
    @Transactional
    public void getDefterTuru() throws Exception {
        // Initialize the database
        defterTuruRepository.saveAndFlush(defterTuru);

        // Get the defterTuru
        restDefterTuruMockMvc.perform(get("/api/defter-turus/{id}", defterTuru.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(defterTuru.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDefterTuru() throws Exception {
        // Get the defterTuru
        restDefterTuruMockMvc.perform(get("/api/defter-turus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDefterTuru() throws Exception {
        // Initialize the database
        defterTuruService.save(defterTuru);

        int databaseSizeBeforeUpdate = defterTuruRepository.findAll().size();

        // Update the defterTuru
        DefterTuru updatedDefterTuru = defterTuruRepository.findById(defterTuru.getId()).get();
        // Disconnect from session so that the updates on updatedDefterTuru are not directly saved in db
        em.detach(updatedDefterTuru);
        updatedDefterTuru
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA);

        restDefterTuruMockMvc.perform(put("/api/defter-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDefterTuru)))
            .andExpect(status().isOk());

        // Validate the DefterTuru in the database
        List<DefterTuru> defterTuruList = defterTuruRepository.findAll();
        assertThat(defterTuruList).hasSize(databaseSizeBeforeUpdate);
        DefterTuru testDefterTuru = defterTuruList.get(defterTuruList.size() - 1);
        assertThat(testDefterTuru.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testDefterTuru.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void updateNonExistingDefterTuru() throws Exception {
        int databaseSizeBeforeUpdate = defterTuruRepository.findAll().size();

        // Create the DefterTuru

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDefterTuruMockMvc.perform(put("/api/defter-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(defterTuru)))
            .andExpect(status().isBadRequest());

        // Validate the DefterTuru in the database
        List<DefterTuru> defterTuruList = defterTuruRepository.findAll();
        assertThat(defterTuruList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDefterTuru() throws Exception {
        // Initialize the database
        defterTuruService.save(defterTuru);

        int databaseSizeBeforeDelete = defterTuruRepository.findAll().size();

        // Delete the defterTuru
        restDefterTuruMockMvc.perform(delete("/api/defter-turus/{id}", defterTuru.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DefterTuru> defterTuruList = defterTuruRepository.findAll();
        assertThat(defterTuruList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefterTuru.class);
        DefterTuru defterTuru1 = new DefterTuru();
        defterTuru1.setId(1L);
        DefterTuru defterTuru2 = new DefterTuru();
        defterTuru2.setId(defterTuru1.getId());
        assertThat(defterTuru1).isEqualTo(defterTuru2);
        defterTuru2.setId(2L);
        assertThat(defterTuru1).isNotEqualTo(defterTuru2);
        defterTuru1.setId(null);
        assertThat(defterTuru1).isNotEqualTo(defterTuru2);
    }
}
