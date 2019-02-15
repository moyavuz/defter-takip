package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Calisan;
import com.yavuzturtelekom.repository.CalisanRepository;
import com.yavuzturtelekom.service.CalisanService;
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
import org.springframework.util.Base64Utils;
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

import com.yavuzturtelekom.domain.enumeration.CalismaTuru;
import com.yavuzturtelekom.domain.enumeration.MedeniHali;
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

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_EPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_EPOSTA = "BBBBBBBBBB";

    private static final CalismaTuru DEFAULT_CALISMA_TURU = CalismaTuru.MAASLI;
    private static final CalismaTuru UPDATED_CALISMA_TURU = CalismaTuru.GOTURU;

    private static final Double DEFAULT_UCRET = 1D;
    private static final Double UPDATED_UCRET = 2D;

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final MedeniHali DEFAULT_MEDENI_HALI = MedeniHali.BEKAR;
    private static final MedeniHali UPDATED_MEDENI_HALI = MedeniHali.EVLI;

    private static final LocalDate DEFAULT_DOGUM_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOGUM_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_GIRIS_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GIRIS_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CIKIS_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CIKIS_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_RESIM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESIM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESIM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESIM_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOSYA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYA_CONTENT_TYPE = "image/png";

    @Autowired
    private CalisanRepository calisanRepository;

    @Autowired
    private CalisanService calisanService;

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
        final CalisanResource calisanResource = new CalisanResource(calisanService);
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
            .ad(DEFAULT_AD)
            .telefon(DEFAULT_TELEFON)
            .eposta(DEFAULT_EPOSTA)
            .calismaTuru(DEFAULT_CALISMA_TURU)
            .ucret(DEFAULT_UCRET)
            .iban(DEFAULT_IBAN)
            .medeniHali(DEFAULT_MEDENI_HALI)
            .dogumTarihi(DEFAULT_DOGUM_TARIHI)
            .girisTarihi(DEFAULT_GIRIS_TARIHI)
            .cikisTarihi(DEFAULT_CIKIS_TARIHI)
            .resim(DEFAULT_RESIM)
            .resimContentType(DEFAULT_RESIM_CONTENT_TYPE)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE);
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
        assertThat(testCalisan.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testCalisan.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testCalisan.getEposta()).isEqualTo(DEFAULT_EPOSTA);
        assertThat(testCalisan.getCalismaTuru()).isEqualTo(DEFAULT_CALISMA_TURU);
        assertThat(testCalisan.getUcret()).isEqualTo(DEFAULT_UCRET);
        assertThat(testCalisan.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testCalisan.getMedeniHali()).isEqualTo(DEFAULT_MEDENI_HALI);
        assertThat(testCalisan.getDogumTarihi()).isEqualTo(DEFAULT_DOGUM_TARIHI);
        assertThat(testCalisan.getGirisTarihi()).isEqualTo(DEFAULT_GIRIS_TARIHI);
        assertThat(testCalisan.getCikisTarihi()).isEqualTo(DEFAULT_CIKIS_TARIHI);
        assertThat(testCalisan.getResim()).isEqualTo(DEFAULT_RESIM);
        assertThat(testCalisan.getResimContentType()).isEqualTo(DEFAULT_RESIM_CONTENT_TYPE);
        assertThat(testCalisan.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testCalisan.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
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
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = calisanRepository.findAll().size();
        // set the field null
        calisan.setAd(null);

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
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA.toString())))
            .andExpect(jsonPath("$.[*].calismaTuru").value(hasItem(DEFAULT_CALISMA_TURU.toString())))
            .andExpect(jsonPath("$.[*].ucret").value(hasItem(DEFAULT_UCRET.doubleValue())))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN.toString())))
            .andExpect(jsonPath("$.[*].medeniHali").value(hasItem(DEFAULT_MEDENI_HALI.toString())))
            .andExpect(jsonPath("$.[*].dogumTarihi").value(hasItem(DEFAULT_DOGUM_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].girisTarihi").value(hasItem(DEFAULT_GIRIS_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].cikisTarihi").value(hasItem(DEFAULT_CIKIS_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))));
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
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.eposta").value(DEFAULT_EPOSTA.toString()))
            .andExpect(jsonPath("$.calismaTuru").value(DEFAULT_CALISMA_TURU.toString()))
            .andExpect(jsonPath("$.ucret").value(DEFAULT_UCRET.doubleValue()))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN.toString()))
            .andExpect(jsonPath("$.medeniHali").value(DEFAULT_MEDENI_HALI.toString()))
            .andExpect(jsonPath("$.dogumTarihi").value(DEFAULT_DOGUM_TARIHI.toString()))
            .andExpect(jsonPath("$.girisTarihi").value(DEFAULT_GIRIS_TARIHI.toString()))
            .andExpect(jsonPath("$.cikisTarihi").value(DEFAULT_CIKIS_TARIHI.toString()))
            .andExpect(jsonPath("$.resimContentType").value(DEFAULT_RESIM_CONTENT_TYPE))
            .andExpect(jsonPath("$.resim").value(Base64Utils.encodeToString(DEFAULT_RESIM)))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)));
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
        calisanService.save(calisan);

        int databaseSizeBeforeUpdate = calisanRepository.findAll().size();

        // Update the calisan
        Calisan updatedCalisan = calisanRepository.findById(calisan.getId()).get();
        // Disconnect from session so that the updates on updatedCalisan are not directly saved in db
        em.detach(updatedCalisan);
        updatedCalisan
            .tckimlikno(UPDATED_TCKIMLIKNO)
            .ad(UPDATED_AD)
            .telefon(UPDATED_TELEFON)
            .eposta(UPDATED_EPOSTA)
            .calismaTuru(UPDATED_CALISMA_TURU)
            .ucret(UPDATED_UCRET)
            .iban(UPDATED_IBAN)
            .medeniHali(UPDATED_MEDENI_HALI)
            .dogumTarihi(UPDATED_DOGUM_TARIHI)
            .girisTarihi(UPDATED_GIRIS_TARIHI)
            .cikisTarihi(UPDATED_CIKIS_TARIHI)
            .resim(UPDATED_RESIM)
            .resimContentType(UPDATED_RESIM_CONTENT_TYPE)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE);

        restCalisanMockMvc.perform(put("/api/calisans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalisan)))
            .andExpect(status().isOk());

        // Validate the Calisan in the database
        List<Calisan> calisanList = calisanRepository.findAll();
        assertThat(calisanList).hasSize(databaseSizeBeforeUpdate);
        Calisan testCalisan = calisanList.get(calisanList.size() - 1);
        assertThat(testCalisan.getTckimlikno()).isEqualTo(UPDATED_TCKIMLIKNO);
        assertThat(testCalisan.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testCalisan.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testCalisan.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testCalisan.getCalismaTuru()).isEqualTo(UPDATED_CALISMA_TURU);
        assertThat(testCalisan.getUcret()).isEqualTo(UPDATED_UCRET);
        assertThat(testCalisan.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testCalisan.getMedeniHali()).isEqualTo(UPDATED_MEDENI_HALI);
        assertThat(testCalisan.getDogumTarihi()).isEqualTo(UPDATED_DOGUM_TARIHI);
        assertThat(testCalisan.getGirisTarihi()).isEqualTo(UPDATED_GIRIS_TARIHI);
        assertThat(testCalisan.getCikisTarihi()).isEqualTo(UPDATED_CIKIS_TARIHI);
        assertThat(testCalisan.getResim()).isEqualTo(UPDATED_RESIM);
        assertThat(testCalisan.getResimContentType()).isEqualTo(UPDATED_RESIM_CONTENT_TYPE);
        assertThat(testCalisan.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testCalisan.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
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
        calisanService.save(calisan);

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
