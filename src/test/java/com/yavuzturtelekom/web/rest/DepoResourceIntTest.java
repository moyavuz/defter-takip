package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Depo;
import com.yavuzturtelekom.repository.DepoRepository;
import com.yavuzturtelekom.service.DepoService;
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

import com.yavuzturtelekom.domain.enumeration.DepoTuru;
/**
 * Test class for the DepoResource REST controller.
 *
 * @see DepoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class DepoResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final DepoTuru DEFAULT_TURU = DepoTuru.MERKEZ_DEPO;
    private static final DepoTuru UPDATED_TURU = DepoTuru.BOLGE_DEPO;

    @Autowired
    private DepoRepository depoRepository;

    @Autowired
    private DepoService depoService;

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

    private MockMvc restDepoMockMvc;

    private Depo depo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepoResource depoResource = new DepoResource(depoService);
        this.restDepoMockMvc = MockMvcBuilders.standaloneSetup(depoResource)
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
    public static Depo createEntity(EntityManager em) {
        Depo depo = new Depo()
            .ad(DEFAULT_AD)
            .adres(DEFAULT_ADRES)
            .turu(DEFAULT_TURU);
        return depo;
    }

    @Before
    public void initTest() {
        depo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepo() throws Exception {
        int databaseSizeBeforeCreate = depoRepository.findAll().size();

        // Create the Depo
        restDepoMockMvc.perform(post("/api/depos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depo)))
            .andExpect(status().isCreated());

        // Validate the Depo in the database
        List<Depo> depoList = depoRepository.findAll();
        assertThat(depoList).hasSize(databaseSizeBeforeCreate + 1);
        Depo testDepo = depoList.get(depoList.size() - 1);
        assertThat(testDepo.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testDepo.getAdres()).isEqualTo(DEFAULT_ADRES);
        assertThat(testDepo.getTuru()).isEqualTo(DEFAULT_TURU);
    }

    @Test
    @Transactional
    public void createDepoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depoRepository.findAll().size();

        // Create the Depo with an existing ID
        depo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepoMockMvc.perform(post("/api/depos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depo)))
            .andExpect(status().isBadRequest());

        // Validate the Depo in the database
        List<Depo> depoList = depoRepository.findAll();
        assertThat(depoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = depoRepository.findAll().size();
        // set the field null
        depo.setAd(null);

        // Create the Depo, which fails.

        restDepoMockMvc.perform(post("/api/depos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depo)))
            .andExpect(status().isBadRequest());

        List<Depo> depoList = depoRepository.findAll();
        assertThat(depoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepos() throws Exception {
        // Initialize the database
        depoRepository.saveAndFlush(depo);

        // Get all the depoList
        restDepoMockMvc.perform(get("/api/depos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depo.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES.toString())))
            .andExpect(jsonPath("$.[*].turu").value(hasItem(DEFAULT_TURU.toString())));
    }
    
    @Test
    @Transactional
    public void getDepo() throws Exception {
        // Initialize the database
        depoRepository.saveAndFlush(depo);

        // Get the depo
        restDepoMockMvc.perform(get("/api/depos/{id}", depo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depo.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES.toString()))
            .andExpect(jsonPath("$.turu").value(DEFAULT_TURU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepo() throws Exception {
        // Get the depo
        restDepoMockMvc.perform(get("/api/depos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepo() throws Exception {
        // Initialize the database
        depoService.save(depo);

        int databaseSizeBeforeUpdate = depoRepository.findAll().size();

        // Update the depo
        Depo updatedDepo = depoRepository.findById(depo.getId()).get();
        // Disconnect from session so that the updates on updatedDepo are not directly saved in db
        em.detach(updatedDepo);
        updatedDepo
            .ad(UPDATED_AD)
            .adres(UPDATED_ADRES)
            .turu(UPDATED_TURU);

        restDepoMockMvc.perform(put("/api/depos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepo)))
            .andExpect(status().isOk());

        // Validate the Depo in the database
        List<Depo> depoList = depoRepository.findAll();
        assertThat(depoList).hasSize(databaseSizeBeforeUpdate);
        Depo testDepo = depoList.get(depoList.size() - 1);
        assertThat(testDepo.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testDepo.getAdres()).isEqualTo(UPDATED_ADRES);
        assertThat(testDepo.getTuru()).isEqualTo(UPDATED_TURU);
    }

    @Test
    @Transactional
    public void updateNonExistingDepo() throws Exception {
        int databaseSizeBeforeUpdate = depoRepository.findAll().size();

        // Create the Depo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepoMockMvc.perform(put("/api/depos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depo)))
            .andExpect(status().isBadRequest());

        // Validate the Depo in the database
        List<Depo> depoList = depoRepository.findAll();
        assertThat(depoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepo() throws Exception {
        // Initialize the database
        depoService.save(depo);

        int databaseSizeBeforeDelete = depoRepository.findAll().size();

        // Delete the depo
        restDepoMockMvc.perform(delete("/api/depos/{id}", depo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Depo> depoList = depoRepository.findAll();
        assertThat(depoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Depo.class);
        Depo depo1 = new Depo();
        depo1.setId(1L);
        Depo depo2 = new Depo();
        depo2.setId(depo1.getId());
        assertThat(depo1).isEqualTo(depo2);
        depo2.setId(2L);
        assertThat(depo1).isNotEqualTo(depo2);
        depo1.setId(null);
        assertThat(depo1).isNotEqualTo(depo2);
    }
}
