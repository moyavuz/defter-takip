package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.domain.Birim;
import com.yavuzturtelekom.repository.MalzemeRepository;
import com.yavuzturtelekom.service.MalzemeService;
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

import com.yavuzturtelekom.domain.enumeration.ParaBirimi;
/**
 * Test class for the MalzemeResource REST controller.
 *
 * @see MalzemeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class MalzemeResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final Long DEFAULT_MALZEME_NO = 1L;
    private static final Long UPDATED_MALZEME_NO = 2L;

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_KISALTMA = "AAAAAAAAAA";
    private static final String UPDATED_KISALTMA = "BBBBBBBBBB";

    private static final Double DEFAULT_TENZILATSIZ_FIYAT = 1D;
    private static final Double UPDATED_TENZILATSIZ_FIYAT = 2D;

    private static final Double DEFAULT_TENZILATLI_FIYAT = 1D;
    private static final Double UPDATED_TENZILATLI_FIYAT = 2D;

    private static final Double DEFAULT_TASERON_FIYAT = 1D;
    private static final Double UPDATED_TASERON_FIYAT = 2D;

    private static final ParaBirimi DEFAULT_PARA_BIRIMI = ParaBirimi.TL;
    private static final ParaBirimi UPDATED_PARA_BIRIMI = ParaBirimi.USD;

    @Autowired
    private MalzemeRepository malzemeRepository;

    @Autowired
    private MalzemeService malzemeService;

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

    private MockMvc restMalzemeMockMvc;

    private Malzeme malzeme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MalzemeResource malzemeResource = new MalzemeResource(malzemeService);
        this.restMalzemeMockMvc = MockMvcBuilders.standaloneSetup(malzemeResource)
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
    public static Malzeme createEntity(EntityManager em) {
        Malzeme malzeme = new Malzeme()
            .ad(DEFAULT_AD)
            .malzemeNo(DEFAULT_MALZEME_NO)
            .aciklama(DEFAULT_ACIKLAMA)
            .kisaltma(DEFAULT_KISALTMA)
            .tenzilatsizFiyat(DEFAULT_TENZILATSIZ_FIYAT)
            .tenzilatliFiyat(DEFAULT_TENZILATLI_FIYAT)
            .taseronFiyat(DEFAULT_TASERON_FIYAT)
            .paraBirimi(DEFAULT_PARA_BIRIMI);
        // Add required entity
        Birim birim = BirimResourceIntTest.createEntity(em);
        em.persist(birim);
        em.flush();
        malzeme.setBirim(birim);
        return malzeme;
    }

    @Before
    public void initTest() {
        malzeme = createEntity(em);
    }

    @Test
    @Transactional
    public void createMalzeme() throws Exception {
        int databaseSizeBeforeCreate = malzemeRepository.findAll().size();

        // Create the Malzeme
        restMalzemeMockMvc.perform(post("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzeme)))
            .andExpect(status().isCreated());

        // Validate the Malzeme in the database
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeCreate + 1);
        Malzeme testMalzeme = malzemeList.get(malzemeList.size() - 1);
        assertThat(testMalzeme.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testMalzeme.getMalzemeNo()).isEqualTo(DEFAULT_MALZEME_NO);
        assertThat(testMalzeme.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testMalzeme.getKisaltma()).isEqualTo(DEFAULT_KISALTMA);
        assertThat(testMalzeme.getTenzilatsizFiyat()).isEqualTo(DEFAULT_TENZILATSIZ_FIYAT);
        assertThat(testMalzeme.getTenzilatliFiyat()).isEqualTo(DEFAULT_TENZILATLI_FIYAT);
        assertThat(testMalzeme.getTaseronFiyat()).isEqualTo(DEFAULT_TASERON_FIYAT);
        assertThat(testMalzeme.getParaBirimi()).isEqualTo(DEFAULT_PARA_BIRIMI);
    }

    @Test
    @Transactional
    public void createMalzemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = malzemeRepository.findAll().size();

        // Create the Malzeme with an existing ID
        malzeme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMalzemeMockMvc.perform(post("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzeme)))
            .andExpect(status().isBadRequest());

        // Validate the Malzeme in the database
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = malzemeRepository.findAll().size();
        // set the field null
        malzeme.setAd(null);

        // Create the Malzeme, which fails.

        restMalzemeMockMvc.perform(post("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzeme)))
            .andExpect(status().isBadRequest());

        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMalzemes() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList
        restMalzemeMockMvc.perform(get("/api/malzemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(malzeme.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].malzemeNo").value(hasItem(DEFAULT_MALZEME_NO.intValue())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA.toString())))
            .andExpect(jsonPath("$.[*].tenzilatsizFiyat").value(hasItem(DEFAULT_TENZILATSIZ_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].tenzilatliFiyat").value(hasItem(DEFAULT_TENZILATLI_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].taseronFiyat").value(hasItem(DEFAULT_TASERON_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].paraBirimi").value(hasItem(DEFAULT_PARA_BIRIMI.toString())));
    }
    
    @Test
    @Transactional
    public void getMalzeme() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get the malzeme
        restMalzemeMockMvc.perform(get("/api/malzemes/{id}", malzeme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(malzeme.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.malzemeNo").value(DEFAULT_MALZEME_NO.intValue()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.kisaltma").value(DEFAULT_KISALTMA.toString()))
            .andExpect(jsonPath("$.tenzilatsizFiyat").value(DEFAULT_TENZILATSIZ_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.tenzilatliFiyat").value(DEFAULT_TENZILATLI_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.taseronFiyat").value(DEFAULT_TASERON_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.paraBirimi").value(DEFAULT_PARA_BIRIMI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMalzeme() throws Exception {
        // Get the malzeme
        restMalzemeMockMvc.perform(get("/api/malzemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMalzeme() throws Exception {
        // Initialize the database
        malzemeService.save(malzeme);

        int databaseSizeBeforeUpdate = malzemeRepository.findAll().size();

        // Update the malzeme
        Malzeme updatedMalzeme = malzemeRepository.findById(malzeme.getId()).get();
        // Disconnect from session so that the updates on updatedMalzeme are not directly saved in db
        em.detach(updatedMalzeme);
        updatedMalzeme
            .ad(UPDATED_AD)
            .malzemeNo(UPDATED_MALZEME_NO)
            .aciklama(UPDATED_ACIKLAMA)
            .kisaltma(UPDATED_KISALTMA)
            .tenzilatsizFiyat(UPDATED_TENZILATSIZ_FIYAT)
            .tenzilatliFiyat(UPDATED_TENZILATLI_FIYAT)
            .taseronFiyat(UPDATED_TASERON_FIYAT)
            .paraBirimi(UPDATED_PARA_BIRIMI);

        restMalzemeMockMvc.perform(put("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMalzeme)))
            .andExpect(status().isOk());

        // Validate the Malzeme in the database
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeUpdate);
        Malzeme testMalzeme = malzemeList.get(malzemeList.size() - 1);
        assertThat(testMalzeme.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testMalzeme.getMalzemeNo()).isEqualTo(UPDATED_MALZEME_NO);
        assertThat(testMalzeme.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testMalzeme.getKisaltma()).isEqualTo(UPDATED_KISALTMA);
        assertThat(testMalzeme.getTenzilatsizFiyat()).isEqualTo(UPDATED_TENZILATSIZ_FIYAT);
        assertThat(testMalzeme.getTenzilatliFiyat()).isEqualTo(UPDATED_TENZILATLI_FIYAT);
        assertThat(testMalzeme.getTaseronFiyat()).isEqualTo(UPDATED_TASERON_FIYAT);
        assertThat(testMalzeme.getParaBirimi()).isEqualTo(UPDATED_PARA_BIRIMI);
    }

    @Test
    @Transactional
    public void updateNonExistingMalzeme() throws Exception {
        int databaseSizeBeforeUpdate = malzemeRepository.findAll().size();

        // Create the Malzeme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMalzemeMockMvc.perform(put("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzeme)))
            .andExpect(status().isBadRequest());

        // Validate the Malzeme in the database
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMalzeme() throws Exception {
        // Initialize the database
        malzemeService.save(malzeme);

        int databaseSizeBeforeDelete = malzemeRepository.findAll().size();

        // Delete the malzeme
        restMalzemeMockMvc.perform(delete("/api/malzemes/{id}", malzeme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Malzeme.class);
        Malzeme malzeme1 = new Malzeme();
        malzeme1.setId(1L);
        Malzeme malzeme2 = new Malzeme();
        malzeme2.setId(malzeme1.getId());
        assertThat(malzeme1).isEqualTo(malzeme2);
        malzeme2.setId(2L);
        assertThat(malzeme1).isNotEqualTo(malzeme2);
        malzeme1.setId(null);
        assertThat(malzeme1).isNotEqualTo(malzeme2);
    }
}
