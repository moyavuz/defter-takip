package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Birim;
import com.yavuzturtelekom.repository.BirimRepository;
import com.yavuzturtelekom.service.BirimService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.BirimCriteria;
import com.yavuzturtelekom.service.BirimQueryService;

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
 * Test class for the BirimResource REST controller.
 *
 * @see BirimResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class BirimResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_KISALTMA = "AAAAAAAAAA";
    private static final String UPDATED_KISALTMA = "BBBBBBBBBB";

    private static final Double DEFAULT_CARPAN = 1D;
    private static final Double UPDATED_CARPAN = 2D;

    @Autowired
    private BirimRepository birimRepository;

    @Autowired
    private BirimService birimService;

    @Autowired
    private BirimQueryService birimQueryService;

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

    private MockMvc restBirimMockMvc;

    private Birim birim;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BirimResource birimResource = new BirimResource(birimService, birimQueryService);
        this.restBirimMockMvc = MockMvcBuilders.standaloneSetup(birimResource)
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
    public static Birim createEntity(EntityManager em) {
        Birim birim = new Birim()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .kisaltma(DEFAULT_KISALTMA)
            .carpan(DEFAULT_CARPAN);
        return birim;
    }

    @Before
    public void initTest() {
        birim = createEntity(em);
    }

    @Test
    @Transactional
    public void createBirim() throws Exception {
        int databaseSizeBeforeCreate = birimRepository.findAll().size();

        // Create the Birim
        restBirimMockMvc.perform(post("/api/birims")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(birim)))
            .andExpect(status().isCreated());

        // Validate the Birim in the database
        List<Birim> birimList = birimRepository.findAll();
        assertThat(birimList).hasSize(databaseSizeBeforeCreate + 1);
        Birim testBirim = birimList.get(birimList.size() - 1);
        assertThat(testBirim.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testBirim.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testBirim.getKisaltma()).isEqualTo(DEFAULT_KISALTMA);
        assertThat(testBirim.getCarpan()).isEqualTo(DEFAULT_CARPAN);
    }

    @Test
    @Transactional
    public void createBirimWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = birimRepository.findAll().size();

        // Create the Birim with an existing ID
        birim.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBirimMockMvc.perform(post("/api/birims")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(birim)))
            .andExpect(status().isBadRequest());

        // Validate the Birim in the database
        List<Birim> birimList = birimRepository.findAll();
        assertThat(birimList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = birimRepository.findAll().size();
        // set the field null
        birim.setAd(null);

        // Create the Birim, which fails.

        restBirimMockMvc.perform(post("/api/birims")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(birim)))
            .andExpect(status().isBadRequest());

        List<Birim> birimList = birimRepository.findAll();
        assertThat(birimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBirims() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList
        restBirimMockMvc.perform(get("/api/birims?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(birim.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA.toString())))
            .andExpect(jsonPath("$.[*].carpan").value(hasItem(DEFAULT_CARPAN.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getBirim() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get the birim
        restBirimMockMvc.perform(get("/api/birims/{id}", birim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(birim.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.kisaltma").value(DEFAULT_KISALTMA.toString()))
            .andExpect(jsonPath("$.carpan").value(DEFAULT_CARPAN.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllBirimsByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where ad equals to DEFAULT_AD
        defaultBirimShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the birimList where ad equals to UPDATED_AD
        defaultBirimShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllBirimsByAdIsInShouldWork() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where ad in DEFAULT_AD or UPDATED_AD
        defaultBirimShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the birimList where ad equals to UPDATED_AD
        defaultBirimShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllBirimsByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where ad is not null
        defaultBirimShouldBeFound("ad.specified=true");

        // Get all the birimList where ad is null
        defaultBirimShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllBirimsByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where aciklama equals to DEFAULT_ACIKLAMA
        defaultBirimShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the birimList where aciklama equals to UPDATED_ACIKLAMA
        defaultBirimShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllBirimsByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultBirimShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the birimList where aciklama equals to UPDATED_ACIKLAMA
        defaultBirimShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllBirimsByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where aciklama is not null
        defaultBirimShouldBeFound("aciklama.specified=true");

        // Get all the birimList where aciklama is null
        defaultBirimShouldNotBeFound("aciklama.specified=false");
    }

    @Test
    @Transactional
    public void getAllBirimsByKisaltmaIsEqualToSomething() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where kisaltma equals to DEFAULT_KISALTMA
        defaultBirimShouldBeFound("kisaltma.equals=" + DEFAULT_KISALTMA);

        // Get all the birimList where kisaltma equals to UPDATED_KISALTMA
        defaultBirimShouldNotBeFound("kisaltma.equals=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllBirimsByKisaltmaIsInShouldWork() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where kisaltma in DEFAULT_KISALTMA or UPDATED_KISALTMA
        defaultBirimShouldBeFound("kisaltma.in=" + DEFAULT_KISALTMA + "," + UPDATED_KISALTMA);

        // Get all the birimList where kisaltma equals to UPDATED_KISALTMA
        defaultBirimShouldNotBeFound("kisaltma.in=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllBirimsByKisaltmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where kisaltma is not null
        defaultBirimShouldBeFound("kisaltma.specified=true");

        // Get all the birimList where kisaltma is null
        defaultBirimShouldNotBeFound("kisaltma.specified=false");
    }

    @Test
    @Transactional
    public void getAllBirimsByCarpanIsEqualToSomething() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where carpan equals to DEFAULT_CARPAN
        defaultBirimShouldBeFound("carpan.equals=" + DEFAULT_CARPAN);

        // Get all the birimList where carpan equals to UPDATED_CARPAN
        defaultBirimShouldNotBeFound("carpan.equals=" + UPDATED_CARPAN);
    }

    @Test
    @Transactional
    public void getAllBirimsByCarpanIsInShouldWork() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where carpan in DEFAULT_CARPAN or UPDATED_CARPAN
        defaultBirimShouldBeFound("carpan.in=" + DEFAULT_CARPAN + "," + UPDATED_CARPAN);

        // Get all the birimList where carpan equals to UPDATED_CARPAN
        defaultBirimShouldNotBeFound("carpan.in=" + UPDATED_CARPAN);
    }

    @Test
    @Transactional
    public void getAllBirimsByCarpanIsNullOrNotNull() throws Exception {
        // Initialize the database
        birimRepository.saveAndFlush(birim);

        // Get all the birimList where carpan is not null
        defaultBirimShouldBeFound("carpan.specified=true");

        // Get all the birimList where carpan is null
        defaultBirimShouldNotBeFound("carpan.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBirimShouldBeFound(String filter) throws Exception {
        restBirimMockMvc.perform(get("/api/birims?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(birim.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA)))
            .andExpect(jsonPath("$.[*].carpan").value(hasItem(DEFAULT_CARPAN.doubleValue())));

        // Check, that the count call also returns 1
        restBirimMockMvc.perform(get("/api/birims/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBirimShouldNotBeFound(String filter) throws Exception {
        restBirimMockMvc.perform(get("/api/birims?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBirimMockMvc.perform(get("/api/birims/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBirim() throws Exception {
        // Get the birim
        restBirimMockMvc.perform(get("/api/birims/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBirim() throws Exception {
        // Initialize the database
        birimService.save(birim);

        int databaseSizeBeforeUpdate = birimRepository.findAll().size();

        // Update the birim
        Birim updatedBirim = birimRepository.findById(birim.getId()).get();
        // Disconnect from session so that the updates on updatedBirim are not directly saved in db
        em.detach(updatedBirim);
        updatedBirim
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .kisaltma(UPDATED_KISALTMA)
            .carpan(UPDATED_CARPAN);

        restBirimMockMvc.perform(put("/api/birims")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBirim)))
            .andExpect(status().isOk());

        // Validate the Birim in the database
        List<Birim> birimList = birimRepository.findAll();
        assertThat(birimList).hasSize(databaseSizeBeforeUpdate);
        Birim testBirim = birimList.get(birimList.size() - 1);
        assertThat(testBirim.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testBirim.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testBirim.getKisaltma()).isEqualTo(UPDATED_KISALTMA);
        assertThat(testBirim.getCarpan()).isEqualTo(UPDATED_CARPAN);
    }

    @Test
    @Transactional
    public void updateNonExistingBirim() throws Exception {
        int databaseSizeBeforeUpdate = birimRepository.findAll().size();

        // Create the Birim

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBirimMockMvc.perform(put("/api/birims")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(birim)))
            .andExpect(status().isBadRequest());

        // Validate the Birim in the database
        List<Birim> birimList = birimRepository.findAll();
        assertThat(birimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBirim() throws Exception {
        // Initialize the database
        birimService.save(birim);

        int databaseSizeBeforeDelete = birimRepository.findAll().size();

        // Delete the birim
        restBirimMockMvc.perform(delete("/api/birims/{id}", birim.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Birim> birimList = birimRepository.findAll();
        assertThat(birimList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Birim.class);
        Birim birim1 = new Birim();
        birim1.setId(1L);
        Birim birim2 = new Birim();
        birim2.setId(birim1.getId());
        assertThat(birim1).isEqualTo(birim2);
        birim2.setId(2L);
        assertThat(birim1).isNotEqualTo(birim2);
        birim1.setId(null);
        assertThat(birim1).isNotEqualTo(birim2);
    }
}
