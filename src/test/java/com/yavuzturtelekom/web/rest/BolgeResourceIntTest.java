package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Bolge;
import com.yavuzturtelekom.repository.BolgeRepository;
import com.yavuzturtelekom.service.BolgeService;
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
 * Test class for the BolgeResource REST controller.
 *
 * @see BolgeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class BolgeResourceIntTest {

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESI = "AAAAAAAAAA";
    private static final String UPDATED_ADRESI = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AKTIF_MI = false;
    private static final Boolean UPDATED_AKTIF_MI = true;

    @Autowired
    private BolgeRepository bolgeRepository;

    @Autowired
    private BolgeService bolgeService;

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

    private MockMvc restBolgeMockMvc;

    private Bolge bolge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BolgeResource bolgeResource = new BolgeResource(bolgeService);
        this.restBolgeMockMvc = MockMvcBuilders.standaloneSetup(bolgeResource)
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
    public static Bolge createEntity(EntityManager em) {
        Bolge bolge = new Bolge()
            .adi(DEFAULT_ADI)
            .adresi(DEFAULT_ADRESI)
            .aktifMi(DEFAULT_AKTIF_MI);
        return bolge;
    }

    @Before
    public void initTest() {
        bolge = createEntity(em);
    }

    @Test
    @Transactional
    public void createBolge() throws Exception {
        int databaseSizeBeforeCreate = bolgeRepository.findAll().size();

        // Create the Bolge
        restBolgeMockMvc.perform(post("/api/bolges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolge)))
            .andExpect(status().isCreated());

        // Validate the Bolge in the database
        List<Bolge> bolgeList = bolgeRepository.findAll();
        assertThat(bolgeList).hasSize(databaseSizeBeforeCreate + 1);
        Bolge testBolge = bolgeList.get(bolgeList.size() - 1);
        assertThat(testBolge.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testBolge.getAdresi()).isEqualTo(DEFAULT_ADRESI);
        assertThat(testBolge.isAktifMi()).isEqualTo(DEFAULT_AKTIF_MI);
    }

    @Test
    @Transactional
    public void createBolgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bolgeRepository.findAll().size();

        // Create the Bolge with an existing ID
        bolge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBolgeMockMvc.perform(post("/api/bolges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolge)))
            .andExpect(status().isBadRequest());

        // Validate the Bolge in the database
        List<Bolge> bolgeList = bolgeRepository.findAll();
        assertThat(bolgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = bolgeRepository.findAll().size();
        // set the field null
        bolge.setAdi(null);

        // Create the Bolge, which fails.

        restBolgeMockMvc.perform(post("/api/bolges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolge)))
            .andExpect(status().isBadRequest());

        List<Bolge> bolgeList = bolgeRepository.findAll();
        assertThat(bolgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBolges() throws Exception {
        // Initialize the database
        bolgeRepository.saveAndFlush(bolge);

        // Get all the bolgeList
        restBolgeMockMvc.perform(get("/api/bolges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bolge.getId().intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())))
            .andExpect(jsonPath("$.[*].adresi").value(hasItem(DEFAULT_ADRESI.toString())))
            .andExpect(jsonPath("$.[*].aktifMi").value(hasItem(DEFAULT_AKTIF_MI.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBolge() throws Exception {
        // Initialize the database
        bolgeRepository.saveAndFlush(bolge);

        // Get the bolge
        restBolgeMockMvc.perform(get("/api/bolges/{id}", bolge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bolge.getId().intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()))
            .andExpect(jsonPath("$.adresi").value(DEFAULT_ADRESI.toString()))
            .andExpect(jsonPath("$.aktifMi").value(DEFAULT_AKTIF_MI.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBolge() throws Exception {
        // Get the bolge
        restBolgeMockMvc.perform(get("/api/bolges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBolge() throws Exception {
        // Initialize the database
        bolgeService.save(bolge);

        int databaseSizeBeforeUpdate = bolgeRepository.findAll().size();

        // Update the bolge
        Bolge updatedBolge = bolgeRepository.findById(bolge.getId()).get();
        // Disconnect from session so that the updates on updatedBolge are not directly saved in db
        em.detach(updatedBolge);
        updatedBolge
            .adi(UPDATED_ADI)
            .adresi(UPDATED_ADRESI)
            .aktifMi(UPDATED_AKTIF_MI);

        restBolgeMockMvc.perform(put("/api/bolges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBolge)))
            .andExpect(status().isOk());

        // Validate the Bolge in the database
        List<Bolge> bolgeList = bolgeRepository.findAll();
        assertThat(bolgeList).hasSize(databaseSizeBeforeUpdate);
        Bolge testBolge = bolgeList.get(bolgeList.size() - 1);
        assertThat(testBolge.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testBolge.getAdresi()).isEqualTo(UPDATED_ADRESI);
        assertThat(testBolge.isAktifMi()).isEqualTo(UPDATED_AKTIF_MI);
    }

    @Test
    @Transactional
    public void updateNonExistingBolge() throws Exception {
        int databaseSizeBeforeUpdate = bolgeRepository.findAll().size();

        // Create the Bolge

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBolgeMockMvc.perform(put("/api/bolges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolge)))
            .andExpect(status().isBadRequest());

        // Validate the Bolge in the database
        List<Bolge> bolgeList = bolgeRepository.findAll();
        assertThat(bolgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBolge() throws Exception {
        // Initialize the database
        bolgeService.save(bolge);

        int databaseSizeBeforeDelete = bolgeRepository.findAll().size();

        // Delete the bolge
        restBolgeMockMvc.perform(delete("/api/bolges/{id}", bolge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bolge> bolgeList = bolgeRepository.findAll();
        assertThat(bolgeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bolge.class);
        Bolge bolge1 = new Bolge();
        bolge1.setId(1L);
        Bolge bolge2 = new Bolge();
        bolge2.setId(bolge1.getId());
        assertThat(bolge1).isEqualTo(bolge2);
        bolge2.setId(2L);
        assertThat(bolge1).isNotEqualTo(bolge2);
        bolge1.setId(null);
        assertThat(bolge1).isNotEqualTo(bolge2);
    }
}
