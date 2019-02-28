package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.EskalasyonTuru;
import com.yavuzturtelekom.repository.EskalasyonTuruRepository;
import com.yavuzturtelekom.service.EskalasyonTuruService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.EskalasyonTuruCriteria;
import com.yavuzturtelekom.service.EskalasyonTuruQueryService;

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
 * Test class for the EskalasyonTuruResource REST controller.
 *
 * @see EskalasyonTuruResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class EskalasyonTuruResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_KISALTMA = "AAAAAAAAAA";
    private static final String UPDATED_KISALTMA = "BBBBBBBBBB";

    @Autowired
    private EskalasyonTuruRepository eskalasyonTuruRepository;

    @Autowired
    private EskalasyonTuruService eskalasyonTuruService;

    @Autowired
    private EskalasyonTuruQueryService eskalasyonTuruQueryService;

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

    private MockMvc restEskalasyonTuruMockMvc;

    private EskalasyonTuru eskalasyonTuru;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EskalasyonTuruResource eskalasyonTuruResource = new EskalasyonTuruResource(eskalasyonTuruService, eskalasyonTuruQueryService);
        this.restEskalasyonTuruMockMvc = MockMvcBuilders.standaloneSetup(eskalasyonTuruResource)
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
    public static EskalasyonTuru createEntity(EntityManager em) {
        EskalasyonTuru eskalasyonTuru = new EskalasyonTuru()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .kisaltma(DEFAULT_KISALTMA);
        return eskalasyonTuru;
    }

    @Before
    public void initTest() {
        eskalasyonTuru = createEntity(em);
    }

    @Test
    @Transactional
    public void createEskalasyonTuru() throws Exception {
        int databaseSizeBeforeCreate = eskalasyonTuruRepository.findAll().size();

        // Create the EskalasyonTuru
        restEskalasyonTuruMockMvc.perform(post("/api/eskalasyon-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eskalasyonTuru)))
            .andExpect(status().isCreated());

        // Validate the EskalasyonTuru in the database
        List<EskalasyonTuru> eskalasyonTuruList = eskalasyonTuruRepository.findAll();
        assertThat(eskalasyonTuruList).hasSize(databaseSizeBeforeCreate + 1);
        EskalasyonTuru testEskalasyonTuru = eskalasyonTuruList.get(eskalasyonTuruList.size() - 1);
        assertThat(testEskalasyonTuru.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testEskalasyonTuru.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testEskalasyonTuru.getKisaltma()).isEqualTo(DEFAULT_KISALTMA);
    }

    @Test
    @Transactional
    public void createEskalasyonTuruWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eskalasyonTuruRepository.findAll().size();

        // Create the EskalasyonTuru with an existing ID
        eskalasyonTuru.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEskalasyonTuruMockMvc.perform(post("/api/eskalasyon-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eskalasyonTuru)))
            .andExpect(status().isBadRequest());

        // Validate the EskalasyonTuru in the database
        List<EskalasyonTuru> eskalasyonTuruList = eskalasyonTuruRepository.findAll();
        assertThat(eskalasyonTuruList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = eskalasyonTuruRepository.findAll().size();
        // set the field null
        eskalasyonTuru.setAd(null);

        // Create the EskalasyonTuru, which fails.

        restEskalasyonTuruMockMvc.perform(post("/api/eskalasyon-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eskalasyonTuru)))
            .andExpect(status().isBadRequest());

        List<EskalasyonTuru> eskalasyonTuruList = eskalasyonTuruRepository.findAll();
        assertThat(eskalasyonTuruList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurus() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList
        restEskalasyonTuruMockMvc.perform(get("/api/eskalasyon-turus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eskalasyonTuru.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA.toString())));
    }
    
    @Test
    @Transactional
    public void getEskalasyonTuru() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get the eskalasyonTuru
        restEskalasyonTuruMockMvc.perform(get("/api/eskalasyon-turus/{id}", eskalasyonTuru.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eskalasyonTuru.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.kisaltma").value(DEFAULT_KISALTMA.toString()));
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurusByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList where ad equals to DEFAULT_AD
        defaultEskalasyonTuruShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the eskalasyonTuruList where ad equals to UPDATED_AD
        defaultEskalasyonTuruShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurusByAdIsInShouldWork() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList where ad in DEFAULT_AD or UPDATED_AD
        defaultEskalasyonTuruShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the eskalasyonTuruList where ad equals to UPDATED_AD
        defaultEskalasyonTuruShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurusByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList where ad is not null
        defaultEskalasyonTuruShouldBeFound("ad.specified=true");

        // Get all the eskalasyonTuruList where ad is null
        defaultEskalasyonTuruShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurusByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList where aciklama equals to DEFAULT_ACIKLAMA
        defaultEskalasyonTuruShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the eskalasyonTuruList where aciklama equals to UPDATED_ACIKLAMA
        defaultEskalasyonTuruShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurusByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultEskalasyonTuruShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the eskalasyonTuruList where aciklama equals to UPDATED_ACIKLAMA
        defaultEskalasyonTuruShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurusByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList where aciklama is not null
        defaultEskalasyonTuruShouldBeFound("aciklama.specified=true");

        // Get all the eskalasyonTuruList where aciklama is null
        defaultEskalasyonTuruShouldNotBeFound("aciklama.specified=false");
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurusByKisaltmaIsEqualToSomething() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList where kisaltma equals to DEFAULT_KISALTMA
        defaultEskalasyonTuruShouldBeFound("kisaltma.equals=" + DEFAULT_KISALTMA);

        // Get all the eskalasyonTuruList where kisaltma equals to UPDATED_KISALTMA
        defaultEskalasyonTuruShouldNotBeFound("kisaltma.equals=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurusByKisaltmaIsInShouldWork() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList where kisaltma in DEFAULT_KISALTMA or UPDATED_KISALTMA
        defaultEskalasyonTuruShouldBeFound("kisaltma.in=" + DEFAULT_KISALTMA + "," + UPDATED_KISALTMA);

        // Get all the eskalasyonTuruList where kisaltma equals to UPDATED_KISALTMA
        defaultEskalasyonTuruShouldNotBeFound("kisaltma.in=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllEskalasyonTurusByKisaltmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        eskalasyonTuruRepository.saveAndFlush(eskalasyonTuru);

        // Get all the eskalasyonTuruList where kisaltma is not null
        defaultEskalasyonTuruShouldBeFound("kisaltma.specified=true");

        // Get all the eskalasyonTuruList where kisaltma is null
        defaultEskalasyonTuruShouldNotBeFound("kisaltma.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEskalasyonTuruShouldBeFound(String filter) throws Exception {
        restEskalasyonTuruMockMvc.perform(get("/api/eskalasyon-turus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eskalasyonTuru.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA)));

        // Check, that the count call also returns 1
        restEskalasyonTuruMockMvc.perform(get("/api/eskalasyon-turus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEskalasyonTuruShouldNotBeFound(String filter) throws Exception {
        restEskalasyonTuruMockMvc.perform(get("/api/eskalasyon-turus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEskalasyonTuruMockMvc.perform(get("/api/eskalasyon-turus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEskalasyonTuru() throws Exception {
        // Get the eskalasyonTuru
        restEskalasyonTuruMockMvc.perform(get("/api/eskalasyon-turus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEskalasyonTuru() throws Exception {
        // Initialize the database
        eskalasyonTuruService.save(eskalasyonTuru);

        int databaseSizeBeforeUpdate = eskalasyonTuruRepository.findAll().size();

        // Update the eskalasyonTuru
        EskalasyonTuru updatedEskalasyonTuru = eskalasyonTuruRepository.findById(eskalasyonTuru.getId()).get();
        // Disconnect from session so that the updates on updatedEskalasyonTuru are not directly saved in db
        em.detach(updatedEskalasyonTuru);
        updatedEskalasyonTuru
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .kisaltma(UPDATED_KISALTMA);

        restEskalasyonTuruMockMvc.perform(put("/api/eskalasyon-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEskalasyonTuru)))
            .andExpect(status().isOk());

        // Validate the EskalasyonTuru in the database
        List<EskalasyonTuru> eskalasyonTuruList = eskalasyonTuruRepository.findAll();
        assertThat(eskalasyonTuruList).hasSize(databaseSizeBeforeUpdate);
        EskalasyonTuru testEskalasyonTuru = eskalasyonTuruList.get(eskalasyonTuruList.size() - 1);
        assertThat(testEskalasyonTuru.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testEskalasyonTuru.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testEskalasyonTuru.getKisaltma()).isEqualTo(UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void updateNonExistingEskalasyonTuru() throws Exception {
        int databaseSizeBeforeUpdate = eskalasyonTuruRepository.findAll().size();

        // Create the EskalasyonTuru

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEskalasyonTuruMockMvc.perform(put("/api/eskalasyon-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eskalasyonTuru)))
            .andExpect(status().isBadRequest());

        // Validate the EskalasyonTuru in the database
        List<EskalasyonTuru> eskalasyonTuruList = eskalasyonTuruRepository.findAll();
        assertThat(eskalasyonTuruList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEskalasyonTuru() throws Exception {
        // Initialize the database
        eskalasyonTuruService.save(eskalasyonTuru);

        int databaseSizeBeforeDelete = eskalasyonTuruRepository.findAll().size();

        // Delete the eskalasyonTuru
        restEskalasyonTuruMockMvc.perform(delete("/api/eskalasyon-turus/{id}", eskalasyonTuru.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EskalasyonTuru> eskalasyonTuruList = eskalasyonTuruRepository.findAll();
        assertThat(eskalasyonTuruList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EskalasyonTuru.class);
        EskalasyonTuru eskalasyonTuru1 = new EskalasyonTuru();
        eskalasyonTuru1.setId(1L);
        EskalasyonTuru eskalasyonTuru2 = new EskalasyonTuru();
        eskalasyonTuru2.setId(eskalasyonTuru1.getId());
        assertThat(eskalasyonTuru1).isEqualTo(eskalasyonTuru2);
        eskalasyonTuru2.setId(2L);
        assertThat(eskalasyonTuru1).isNotEqualTo(eskalasyonTuru2);
        eskalasyonTuru1.setId(null);
        assertThat(eskalasyonTuru1).isNotEqualTo(eskalasyonTuru2);
    }
}
