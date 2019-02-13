package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Calisan;
import com.yavuzturtelekom.repository.CalisanRepository;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yavuzturtelekom.domain.enumeration.CalisanTuru;
/**
 * Test class for the CalisanResource REST controller.
 *
 * @see CalisanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class CalisanResourceIntTest {

    private static final Long DEFAULT_TCKIMLIKNO = 1L;
    private static final Long UPDATED_TCKIMLIKNO = 2L;

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_SOYADI = "AAAAAAAAAA";
    private static final String UPDATED_SOYADI = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_GIRIS_TARIHI = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GIRIS_TARIHI = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CIKIS_TARIHI = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CIKIS_TARIHI = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_UCRET = new BigDecimal(1);
    private static final BigDecimal UPDATED_UCRET = new BigDecimal(2);

    private static final CalisanTuru DEFAULT_CALISAN_TURU = CalisanTuru.MAASLI;
    private static final CalisanTuru UPDATED_CALISAN_TURU = CalisanTuru.GOTURU;

    private static final Boolean DEFAULT_AKTIF_MI = false;
    private static final Boolean UPDATED_AKTIF_MI = true;

    @Autowired
    private CalisanRepository calisanRepository;

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

    private MockMvc restCalisanMockMvc;

    private Calisan calisan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalisanResource calisanResource = new CalisanResource(calisanRepository);
        this.restCalisanMockMvc = MockMvcBuilders.standaloneSetup(calisanResource)
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
    public static Calisan createEntity(EntityManager em) {
        Calisan calisan = new Calisan()
            .tckimlikno(DEFAULT_TCKIMLIKNO)
            .adi(DEFAULT_ADI)
            .soyadi(DEFAULT_SOYADI)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .girisTarihi(DEFAULT_GIRIS_TARIHI)
            .cikisTarihi(DEFAULT_CIKIS_TARIHI)
            .ucret(DEFAULT_UCRET)
            .calisanTuru(DEFAULT_CALISAN_TURU)
            .aktifMi(DEFAULT_AKTIF_MI);
        return calisan;
    }

    @Before
    public void initTest() {
        calisan = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalisan() throws Exception {
        int databaseSizeBeforeCreate = calisanRepository.findAll().size();

        // Create the Calisan
        restCalisanMockMvc.perform(post("/api/calisans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisan)))
            .andExpect(status().isCreated());

        // Validate the Calisan in the database
        List<Calisan> calisanList = calisanRepository.findAll();
        assertThat(calisanList).hasSize(databaseSizeBeforeCreate + 1);
        Calisan testCalisan = calisanList.get(calisanList.size() - 1);
        assertThat(testCalisan.getTckimlikno()).isEqualTo(DEFAULT_TCKIMLIKNO);
        assertThat(testCalisan.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testCalisan.getSoyadi()).isEqualTo(DEFAULT_SOYADI);
        assertThat(testCalisan.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCalisan.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCalisan.getGirisTarihi()).isEqualTo(DEFAULT_GIRIS_TARIHI);
        assertThat(testCalisan.getCikisTarihi()).isEqualTo(DEFAULT_CIKIS_TARIHI);
        assertThat(testCalisan.getUcret()).isEqualTo(DEFAULT_UCRET);
        assertThat(testCalisan.getCalisanTuru()).isEqualTo(DEFAULT_CALISAN_TURU);
        assertThat(testCalisan.isAktifMi()).isEqualTo(DEFAULT_AKTIF_MI);
    }

    @Test
    @Transactional
    public void createCalisanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calisanRepository.findAll().size();

        // Create the Calisan with an existing ID
        calisan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalisanMockMvc.perform(post("/api/calisans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisan)))
            .andExpect(status().isBadRequest());

        // Validate the Calisan in the database
        List<Calisan> calisanList = calisanRepository.findAll();
        assertThat(calisanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTckimliknoIsRequired() throws Exception {
        int databaseSizeBeforeTest = calisanRepository.findAll().size();
        // set the field null
        calisan.setTckimlikno(null);

        // Create the Calisan, which fails.

        restCalisanMockMvc.perform(post("/api/calisans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisan)))
            .andExpect(status().isBadRequest());

        List<Calisan> calisanList = calisanRepository.findAll();
        assertThat(calisanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = calisanRepository.findAll().size();
        // set the field null
        calisan.setAdi(null);

        // Create the Calisan, which fails.

        restCalisanMockMvc.perform(post("/api/calisans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisan)))
            .andExpect(status().isBadRequest());

        List<Calisan> calisanList = calisanRepository.findAll();
        assertThat(calisanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSoyadiIsRequired() throws Exception {
        int databaseSizeBeforeTest = calisanRepository.findAll().size();
        // set the field null
        calisan.setSoyadi(null);

        // Create the Calisan, which fails.

        restCalisanMockMvc.perform(post("/api/calisans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisan)))
            .andExpect(status().isBadRequest());

        List<Calisan> calisanList = calisanRepository.findAll();
        assertThat(calisanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalisans() throws Exception {
        // Initialize the database
        calisanRepository.saveAndFlush(calisan);

        // Get all the calisanList
        restCalisanMockMvc.perform(get("/api/calisans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calisan.getId().intValue())))
            .andExpect(jsonPath("$.[*].tckimlikno").value(hasItem(DEFAULT_TCKIMLIKNO.intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())))
            .andExpect(jsonPath("$.[*].soyadi").value(hasItem(DEFAULT_SOYADI.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].girisTarihi").value(hasItem(DEFAULT_GIRIS_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].cikisTarihi").value(hasItem(DEFAULT_CIKIS_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].ucret").value(hasItem(DEFAULT_UCRET.intValue())))
            .andExpect(jsonPath("$.[*].calisanTuru").value(hasItem(DEFAULT_CALISAN_TURU.toString())))
            .andExpect(jsonPath("$.[*].aktifMi").value(hasItem(DEFAULT_AKTIF_MI.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCalisan() throws Exception {
        // Initialize the database
        calisanRepository.saveAndFlush(calisan);

        // Get the calisan
        restCalisanMockMvc.perform(get("/api/calisans/{id}", calisan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calisan.getId().intValue()))
            .andExpect(jsonPath("$.tckimlikno").value(DEFAULT_TCKIMLIKNO.intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()))
            .andExpect(jsonPath("$.soyadi").value(DEFAULT_SOYADI.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.girisTarihi").value(DEFAULT_GIRIS_TARIHI.toString()))
            .andExpect(jsonPath("$.cikisTarihi").value(DEFAULT_CIKIS_TARIHI.toString()))
            .andExpect(jsonPath("$.ucret").value(DEFAULT_UCRET.intValue()))
            .andExpect(jsonPath("$.calisanTuru").value(DEFAULT_CALISAN_TURU.toString()))
            .andExpect(jsonPath("$.aktifMi").value(DEFAULT_AKTIF_MI.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCalisan() throws Exception {
        // Get the calisan
        restCalisanMockMvc.perform(get("/api/calisans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalisan() throws Exception {
        // Initialize the database
        calisanRepository.saveAndFlush(calisan);

        int databaseSizeBeforeUpdate = calisanRepository.findAll().size();

        // Update the calisan
        Calisan updatedCalisan = calisanRepository.findById(calisan.getId()).get();
        // Disconnect from session so that the updates on updatedCalisan are not directly saved in db
        em.detach(updatedCalisan);
        updatedCalisan
            .tckimlikno(UPDATED_TCKIMLIKNO)
            .adi(UPDATED_ADI)
            .soyadi(UPDATED_SOYADI)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .girisTarihi(UPDATED_GIRIS_TARIHI)
            .cikisTarihi(UPDATED_CIKIS_TARIHI)
            .ucret(UPDATED_UCRET)
            .calisanTuru(UPDATED_CALISAN_TURU)
            .aktifMi(UPDATED_AKTIF_MI);

        restCalisanMockMvc.perform(put("/api/calisans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalisan)))
            .andExpect(status().isOk());

        // Validate the Calisan in the database
        List<Calisan> calisanList = calisanRepository.findAll();
        assertThat(calisanList).hasSize(databaseSizeBeforeUpdate);
        Calisan testCalisan = calisanList.get(calisanList.size() - 1);
        assertThat(testCalisan.getTckimlikno()).isEqualTo(UPDATED_TCKIMLIKNO);
        assertThat(testCalisan.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testCalisan.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testCalisan.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCalisan.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCalisan.getGirisTarihi()).isEqualTo(UPDATED_GIRIS_TARIHI);
        assertThat(testCalisan.getCikisTarihi()).isEqualTo(UPDATED_CIKIS_TARIHI);
        assertThat(testCalisan.getUcret()).isEqualTo(UPDATED_UCRET);
        assertThat(testCalisan.getCalisanTuru()).isEqualTo(UPDATED_CALISAN_TURU);
        assertThat(testCalisan.isAktifMi()).isEqualTo(UPDATED_AKTIF_MI);
    }

    @Test
    @Transactional
    public void updateNonExistingCalisan() throws Exception {
        int databaseSizeBeforeUpdate = calisanRepository.findAll().size();

        // Create the Calisan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalisanMockMvc.perform(put("/api/calisans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calisan)))
            .andExpect(status().isBadRequest());

        // Validate the Calisan in the database
        List<Calisan> calisanList = calisanRepository.findAll();
        assertThat(calisanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalisan() throws Exception {
        // Initialize the database
        calisanRepository.saveAndFlush(calisan);

        int databaseSizeBeforeDelete = calisanRepository.findAll().size();

        // Delete the calisan
        restCalisanMockMvc.perform(delete("/api/calisans/{id}", calisan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Calisan> calisanList = calisanRepository.findAll();
        assertThat(calisanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calisan.class);
        Calisan calisan1 = new Calisan();
        calisan1.setId(1L);
        Calisan calisan2 = new Calisan();
        calisan2.setId(calisan1.getId());
        assertThat(calisan1).isEqualTo(calisan2);
        calisan2.setId(2L);
        assertThat(calisan1).isNotEqualTo(calisan2);
        calisan1.setId(null);
        assertThat(calisan1).isNotEqualTo(calisan2);
    }
}
