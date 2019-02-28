package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Eskalasyon;
import com.yavuzturtelekom.domain.EskalasyonTuru;
import com.yavuzturtelekom.repository.EskalasyonRepository;
import com.yavuzturtelekom.service.EskalasyonService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.EskalasyonCriteria;
import com.yavuzturtelekom.service.EskalasyonQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EskalasyonResource REST controller.
 *
 * @see EskalasyonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class EskalasyonResourceIntTest {

    private static final Double DEFAULT_DEGER = 1D;
    private static final Double UPDATED_DEGER = 2D;

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EskalasyonRepository eskalasyonRepository;

    @Autowired
    private EskalasyonService eskalasyonService;

    @Autowired
    private EskalasyonQueryService eskalasyonQueryService;

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

    private MockMvc restEskalasyonMockMvc;

    private Eskalasyon eskalasyon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EskalasyonResource eskalasyonResource = new EskalasyonResource(eskalasyonService, eskalasyonQueryService);
        this.restEskalasyonMockMvc = MockMvcBuilders.standaloneSetup(eskalasyonResource)
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
    public static Eskalasyon createEntity(EntityManager em) {
        Eskalasyon eskalasyon = new Eskalasyon()
            .deger(DEFAULT_DEGER)
            .tarih(DEFAULT_TARIH);
        // Add required entity
        EskalasyonTuru eskalasyonTuru = EskalasyonTuruResourceIntTest.createEntity(em);
        em.persist(eskalasyonTuru);
        em.flush();
        eskalasyon.setTuru(eskalasyonTuru);
        return eskalasyon;
    }

    @Before
    public void initTest() {
        eskalasyon = createEntity(em);
    }

    @Test
    @Transactional
    public void createEskalasyon() throws Exception {
        int databaseSizeBeforeCreate = eskalasyonRepository.findAll().size();

        // Create the Eskalasyon
        restEskalasyonMockMvc.perform(post("/api/eskalasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eskalasyon)))
            .andExpect(status().isCreated());

        // Validate the Eskalasyon in the database
        List<Eskalasyon> eskalasyonList = eskalasyonRepository.findAll();
        assertThat(eskalasyonList).hasSize(databaseSizeBeforeCreate + 1);
        Eskalasyon testEskalasyon = eskalasyonList.get(eskalasyonList.size() - 1);
        assertThat(testEskalasyon.getDeger()).isEqualTo(DEFAULT_DEGER);
        assertThat(testEskalasyon.getTarih()).isEqualTo(DEFAULT_TARIH);
    }

    @Test
    @Transactional
    public void createEskalasyonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eskalasyonRepository.findAll().size();

        // Create the Eskalasyon with an existing ID
        eskalasyon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEskalasyonMockMvc.perform(post("/api/eskalasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eskalasyon)))
            .andExpect(status().isBadRequest());

        // Validate the Eskalasyon in the database
        List<Eskalasyon> eskalasyonList = eskalasyonRepository.findAll();
        assertThat(eskalasyonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDegerIsRequired() throws Exception {
        int databaseSizeBeforeTest = eskalasyonRepository.findAll().size();
        // set the field null
        eskalasyon.setDeger(null);

        // Create the Eskalasyon, which fails.

        restEskalasyonMockMvc.perform(post("/api/eskalasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eskalasyon)))
            .andExpect(status().isBadRequest());

        List<Eskalasyon> eskalasyonList = eskalasyonRepository.findAll();
        assertThat(eskalasyonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTarihIsRequired() throws Exception {
        int databaseSizeBeforeTest = eskalasyonRepository.findAll().size();
        // set the field null
        eskalasyon.setTarih(null);

        // Create the Eskalasyon, which fails.

        restEskalasyonMockMvc.perform(post("/api/eskalasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eskalasyon)))
            .andExpect(status().isBadRequest());

        List<Eskalasyon> eskalasyonList = eskalasyonRepository.findAll();
        assertThat(eskalasyonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEskalasyons() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get all the eskalasyonList
        restEskalasyonMockMvc.perform(get("/api/eskalasyons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eskalasyon.getId().intValue())))
            .andExpect(jsonPath("$.[*].deger").value(hasItem(DEFAULT_DEGER.doubleValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())));
    }
    
    @Test
    @Transactional
    public void getEskalasyon() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get the eskalasyon
        restEskalasyonMockMvc.perform(get("/api/eskalasyons/{id}", eskalasyon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eskalasyon.getId().intValue()))
            .andExpect(jsonPath("$.deger").value(DEFAULT_DEGER.doubleValue()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()));
    }

    @Test
    @Transactional
    public void getAllEskalasyonsByDegerIsEqualToSomething() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get all the eskalasyonList where deger equals to DEFAULT_DEGER
        defaultEskalasyonShouldBeFound("deger.equals=" + DEFAULT_DEGER);

        // Get all the eskalasyonList where deger equals to UPDATED_DEGER
        defaultEskalasyonShouldNotBeFound("deger.equals=" + UPDATED_DEGER);
    }

    @Test
    @Transactional
    public void getAllEskalasyonsByDegerIsInShouldWork() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get all the eskalasyonList where deger in DEFAULT_DEGER or UPDATED_DEGER
        defaultEskalasyonShouldBeFound("deger.in=" + DEFAULT_DEGER + "," + UPDATED_DEGER);

        // Get all the eskalasyonList where deger equals to UPDATED_DEGER
        defaultEskalasyonShouldNotBeFound("deger.in=" + UPDATED_DEGER);
    }

    @Test
    @Transactional
    public void getAllEskalasyonsByDegerIsNullOrNotNull() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get all the eskalasyonList where deger is not null
        defaultEskalasyonShouldBeFound("deger.specified=true");

        // Get all the eskalasyonList where deger is null
        defaultEskalasyonShouldNotBeFound("deger.specified=false");
    }

    @Test
    @Transactional
    public void getAllEskalasyonsByTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get all the eskalasyonList where tarih equals to DEFAULT_TARIH
        defaultEskalasyonShouldBeFound("tarih.equals=" + DEFAULT_TARIH);

        // Get all the eskalasyonList where tarih equals to UPDATED_TARIH
        defaultEskalasyonShouldNotBeFound("tarih.equals=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllEskalasyonsByTarihIsInShouldWork() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get all the eskalasyonList where tarih in DEFAULT_TARIH or UPDATED_TARIH
        defaultEskalasyonShouldBeFound("tarih.in=" + DEFAULT_TARIH + "," + UPDATED_TARIH);

        // Get all the eskalasyonList where tarih equals to UPDATED_TARIH
        defaultEskalasyonShouldNotBeFound("tarih.in=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllEskalasyonsByTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get all the eskalasyonList where tarih is not null
        defaultEskalasyonShouldBeFound("tarih.specified=true");

        // Get all the eskalasyonList where tarih is null
        defaultEskalasyonShouldNotBeFound("tarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllEskalasyonsByTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get all the eskalasyonList where tarih greater than or equals to DEFAULT_TARIH
        defaultEskalasyonShouldBeFound("tarih.greaterOrEqualThan=" + DEFAULT_TARIH);

        // Get all the eskalasyonList where tarih greater than or equals to UPDATED_TARIH
        defaultEskalasyonShouldNotBeFound("tarih.greaterOrEqualThan=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllEskalasyonsByTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        eskalasyonRepository.saveAndFlush(eskalasyon);

        // Get all the eskalasyonList where tarih less than or equals to DEFAULT_TARIH
        defaultEskalasyonShouldNotBeFound("tarih.lessThan=" + DEFAULT_TARIH);

        // Get all the eskalasyonList where tarih less than or equals to UPDATED_TARIH
        defaultEskalasyonShouldBeFound("tarih.lessThan=" + UPDATED_TARIH);
    }


    @Test
    @Transactional
    public void getAllEskalasyonsByTuruIsEqualToSomething() throws Exception {
        // Initialize the database
        EskalasyonTuru turu = EskalasyonTuruResourceIntTest.createEntity(em);
        em.persist(turu);
        em.flush();
        eskalasyon.setTuru(turu);
        eskalasyonRepository.saveAndFlush(eskalasyon);
        Long turuId = turu.getId();

        // Get all the eskalasyonList where turu equals to turuId
        defaultEskalasyonShouldBeFound("turuId.equals=" + turuId);

        // Get all the eskalasyonList where turu equals to turuId + 1
        defaultEskalasyonShouldNotBeFound("turuId.equals=" + (turuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEskalasyonShouldBeFound(String filter) throws Exception {
        restEskalasyonMockMvc.perform(get("/api/eskalasyons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eskalasyon.getId().intValue())))
            .andExpect(jsonPath("$.[*].deger").value(hasItem(DEFAULT_DEGER.doubleValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())));

        // Check, that the count call also returns 1
        restEskalasyonMockMvc.perform(get("/api/eskalasyons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEskalasyonShouldNotBeFound(String filter) throws Exception {
        restEskalasyonMockMvc.perform(get("/api/eskalasyons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEskalasyonMockMvc.perform(get("/api/eskalasyons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEskalasyon() throws Exception {
        // Get the eskalasyon
        restEskalasyonMockMvc.perform(get("/api/eskalasyons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEskalasyon() throws Exception {
        // Initialize the database
        eskalasyonService.save(eskalasyon);

        int databaseSizeBeforeUpdate = eskalasyonRepository.findAll().size();

        // Update the eskalasyon
        Eskalasyon updatedEskalasyon = eskalasyonRepository.findById(eskalasyon.getId()).get();
        // Disconnect from session so that the updates on updatedEskalasyon are not directly saved in db
        em.detach(updatedEskalasyon);
        updatedEskalasyon
            .deger(UPDATED_DEGER)
            .tarih(UPDATED_TARIH);

        restEskalasyonMockMvc.perform(put("/api/eskalasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEskalasyon)))
            .andExpect(status().isOk());

        // Validate the Eskalasyon in the database
        List<Eskalasyon> eskalasyonList = eskalasyonRepository.findAll();
        assertThat(eskalasyonList).hasSize(databaseSizeBeforeUpdate);
        Eskalasyon testEskalasyon = eskalasyonList.get(eskalasyonList.size() - 1);
        assertThat(testEskalasyon.getDeger()).isEqualTo(UPDATED_DEGER);
        assertThat(testEskalasyon.getTarih()).isEqualTo(UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void updateNonExistingEskalasyon() throws Exception {
        int databaseSizeBeforeUpdate = eskalasyonRepository.findAll().size();

        // Create the Eskalasyon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEskalasyonMockMvc.perform(put("/api/eskalasyons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eskalasyon)))
            .andExpect(status().isBadRequest());

        // Validate the Eskalasyon in the database
        List<Eskalasyon> eskalasyonList = eskalasyonRepository.findAll();
        assertThat(eskalasyonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEskalasyon() throws Exception {
        // Initialize the database
        eskalasyonService.save(eskalasyon);

        int databaseSizeBeforeDelete = eskalasyonRepository.findAll().size();

        // Delete the eskalasyon
        restEskalasyonMockMvc.perform(delete("/api/eskalasyons/{id}", eskalasyon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Eskalasyon> eskalasyonList = eskalasyonRepository.findAll();
        assertThat(eskalasyonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eskalasyon.class);
        Eskalasyon eskalasyon1 = new Eskalasyon();
        eskalasyon1.setId(1L);
        Eskalasyon eskalasyon2 = new Eskalasyon();
        eskalasyon2.setId(eskalasyon1.getId());
        assertThat(eskalasyon1).isEqualTo(eskalasyon2);
        eskalasyon2.setId(2L);
        assertThat(eskalasyon1).isNotEqualTo(eskalasyon2);
        eskalasyon1.setId(null);
        assertThat(eskalasyon1).isNotEqualTo(eskalasyon2);
    }
}
