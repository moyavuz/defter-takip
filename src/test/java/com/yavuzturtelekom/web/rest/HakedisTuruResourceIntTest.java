package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.HakedisTuru;
import com.yavuzturtelekom.repository.HakedisTuruRepository;
import com.yavuzturtelekom.service.HakedisTuruService;
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
 * Test class for the HakedisTuruResource REST controller.
 *
 * @see HakedisTuruResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class HakedisTuruResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    @Autowired
    private HakedisTuruRepository hakedisTuruRepository;

    @Autowired
    private HakedisTuruService hakedisTuruService;

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

    private MockMvc restHakedisTuruMockMvc;

    private HakedisTuru hakedisTuru;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HakedisTuruResource hakedisTuruResource = new HakedisTuruResource(hakedisTuruService);
        this.restHakedisTuruMockMvc = MockMvcBuilders.standaloneSetup(hakedisTuruResource)
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
    public static HakedisTuru createEntity(EntityManager em) {
        HakedisTuru hakedisTuru = new HakedisTuru()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA);
        return hakedisTuru;
    }

    @Before
    public void initTest() {
        hakedisTuru = createEntity(em);
    }

    @Test
    @Transactional
    public void createHakedisTuru() throws Exception {
        int databaseSizeBeforeCreate = hakedisTuruRepository.findAll().size();

        // Create the HakedisTuru
        restHakedisTuruMockMvc.perform(post("/api/hakedis-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedisTuru)))
            .andExpect(status().isCreated());

        // Validate the HakedisTuru in the database
        List<HakedisTuru> hakedisTuruList = hakedisTuruRepository.findAll();
        assertThat(hakedisTuruList).hasSize(databaseSizeBeforeCreate + 1);
        HakedisTuru testHakedisTuru = hakedisTuruList.get(hakedisTuruList.size() - 1);
        assertThat(testHakedisTuru.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testHakedisTuru.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
    }

    @Test
    @Transactional
    public void createHakedisTuruWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hakedisTuruRepository.findAll().size();

        // Create the HakedisTuru with an existing ID
        hakedisTuru.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHakedisTuruMockMvc.perform(post("/api/hakedis-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedisTuru)))
            .andExpect(status().isBadRequest());

        // Validate the HakedisTuru in the database
        List<HakedisTuru> hakedisTuruList = hakedisTuruRepository.findAll();
        assertThat(hakedisTuruList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = hakedisTuruRepository.findAll().size();
        // set the field null
        hakedisTuru.setAd(null);

        // Create the HakedisTuru, which fails.

        restHakedisTuruMockMvc.perform(post("/api/hakedis-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedisTuru)))
            .andExpect(status().isBadRequest());

        List<HakedisTuru> hakedisTuruList = hakedisTuruRepository.findAll();
        assertThat(hakedisTuruList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHakedisTurus() throws Exception {
        // Initialize the database
        hakedisTuruRepository.saveAndFlush(hakedisTuru);

        // Get all the hakedisTuruList
        restHakedisTuruMockMvc.perform(get("/api/hakedis-turus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hakedisTuru.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())));
    }
    
    @Test
    @Transactional
    public void getHakedisTuru() throws Exception {
        // Initialize the database
        hakedisTuruRepository.saveAndFlush(hakedisTuru);

        // Get the hakedisTuru
        restHakedisTuruMockMvc.perform(get("/api/hakedis-turus/{id}", hakedisTuru.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hakedisTuru.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHakedisTuru() throws Exception {
        // Get the hakedisTuru
        restHakedisTuruMockMvc.perform(get("/api/hakedis-turus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHakedisTuru() throws Exception {
        // Initialize the database
        hakedisTuruService.save(hakedisTuru);

        int databaseSizeBeforeUpdate = hakedisTuruRepository.findAll().size();

        // Update the hakedisTuru
        HakedisTuru updatedHakedisTuru = hakedisTuruRepository.findById(hakedisTuru.getId()).get();
        // Disconnect from session so that the updates on updatedHakedisTuru are not directly saved in db
        em.detach(updatedHakedisTuru);
        updatedHakedisTuru
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA);

        restHakedisTuruMockMvc.perform(put("/api/hakedis-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHakedisTuru)))
            .andExpect(status().isOk());

        // Validate the HakedisTuru in the database
        List<HakedisTuru> hakedisTuruList = hakedisTuruRepository.findAll();
        assertThat(hakedisTuruList).hasSize(databaseSizeBeforeUpdate);
        HakedisTuru testHakedisTuru = hakedisTuruList.get(hakedisTuruList.size() - 1);
        assertThat(testHakedisTuru.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testHakedisTuru.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void updateNonExistingHakedisTuru() throws Exception {
        int databaseSizeBeforeUpdate = hakedisTuruRepository.findAll().size();

        // Create the HakedisTuru

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHakedisTuruMockMvc.perform(put("/api/hakedis-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedisTuru)))
            .andExpect(status().isBadRequest());

        // Validate the HakedisTuru in the database
        List<HakedisTuru> hakedisTuruList = hakedisTuruRepository.findAll();
        assertThat(hakedisTuruList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHakedisTuru() throws Exception {
        // Initialize the database
        hakedisTuruService.save(hakedisTuru);

        int databaseSizeBeforeDelete = hakedisTuruRepository.findAll().size();

        // Delete the hakedisTuru
        restHakedisTuruMockMvc.perform(delete("/api/hakedis-turus/{id}", hakedisTuru.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HakedisTuru> hakedisTuruList = hakedisTuruRepository.findAll();
        assertThat(hakedisTuruList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HakedisTuru.class);
        HakedisTuru hakedisTuru1 = new HakedisTuru();
        hakedisTuru1.setId(1L);
        HakedisTuru hakedisTuru2 = new HakedisTuru();
        hakedisTuru2.setId(hakedisTuru1.getId());
        assertThat(hakedisTuru1).isEqualTo(hakedisTuru2);
        hakedisTuru2.setId(2L);
        assertThat(hakedisTuru1).isNotEqualTo(hakedisTuru2);
        hakedisTuru1.setId(null);
        assertThat(hakedisTuru1).isNotEqualTo(hakedisTuru2);
    }
}
