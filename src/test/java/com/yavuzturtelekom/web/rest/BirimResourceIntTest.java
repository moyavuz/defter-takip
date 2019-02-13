package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Birim;
import com.yavuzturtelekom.repository.BirimRepository;
import com.yavuzturtelekom.service.BirimService;
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

    private static final Long DEFAULT_CARPANI = 1L;
    private static final Long UPDATED_CARPANI = 2L;

    private static final Boolean DEFAULT_AKTIF_MI = false;
    private static final Boolean UPDATED_AKTIF_MI = true;

    @Autowired
    private BirimRepository birimRepository;

    @Autowired
    private BirimService birimService;

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
        final BirimResource birimResource = new BirimResource(birimService);
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
            .carpani(DEFAULT_CARPANI)
            .aktifMi(DEFAULT_AKTIF_MI);
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
        assertThat(testBirim.getCarpani()).isEqualTo(DEFAULT_CARPANI);
        assertThat(testBirim.isAktifMi()).isEqualTo(DEFAULT_AKTIF_MI);
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
            .andExpect(jsonPath("$.[*].carpani").value(hasItem(DEFAULT_CARPANI.intValue())))
            .andExpect(jsonPath("$.[*].aktifMi").value(hasItem(DEFAULT_AKTIF_MI.booleanValue())));
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
            .andExpect(jsonPath("$.carpani").value(DEFAULT_CARPANI.intValue()))
            .andExpect(jsonPath("$.aktifMi").value(DEFAULT_AKTIF_MI.booleanValue()));
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
            .carpani(UPDATED_CARPANI)
            .aktifMi(UPDATED_AKTIF_MI);

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
        assertThat(testBirim.getCarpani()).isEqualTo(UPDATED_CARPANI);
        assertThat(testBirim.isAktifMi()).isEqualTo(UPDATED_AKTIF_MI);
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
