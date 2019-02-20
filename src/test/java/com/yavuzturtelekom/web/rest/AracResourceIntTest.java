package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Arac;
import com.yavuzturtelekom.repository.AracRepository;
import com.yavuzturtelekom.service.AracService;
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

import com.yavuzturtelekom.domain.enumeration.YakitTuru;
/**
 * Test class for the AracResource REST controller.
 *
 * @see AracResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class AracResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DETAY = "AAAAAAAAAA";
    private static final String UPDATED_DETAY = "BBBBBBBBBB";

    private static final Long DEFAULT_MODEL_YILI = 1L;
    private static final Long UPDATED_MODEL_YILI = 2L;

    private static final YakitTuru DEFAULT_YAKIT_TURU = YakitTuru.DIZEL;
    private static final YakitTuru UPDATED_YAKIT_TURU = YakitTuru.BENZIN;

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MUAYENE_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MUAYENE_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_KASKO_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_KASKO_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SIGORTA_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SIGORTA_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BAKIM_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BAKIM_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_RESIM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESIM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESIM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESIM_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOSYA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYA_CONTENT_TYPE = "image/png";

    @Autowired
    private AracRepository aracRepository;

    @Autowired
    private AracService aracService;

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

    private MockMvc restAracMockMvc;

    private Arac arac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AracResource aracResource = new AracResource(aracService);
        this.restAracMockMvc = MockMvcBuilders.standaloneSetup(aracResource)
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
    public static Arac createEntity(EntityManager em) {
        Arac arac = new Arac()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .detay(DEFAULT_DETAY)
            .modelYili(DEFAULT_MODEL_YILI)
            .yakitTuru(DEFAULT_YAKIT_TURU)
            .tarih(DEFAULT_TARIH)
            .muayeneTarih(DEFAULT_MUAYENE_TARIH)
            .kaskoTarih(DEFAULT_KASKO_TARIH)
            .sigortaTarih(DEFAULT_SIGORTA_TARIH)
            .bakimTarih(DEFAULT_BAKIM_TARIH)
            .resim(DEFAULT_RESIM)
            .resimContentType(DEFAULT_RESIM_CONTENT_TYPE)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE);
        return arac;
    }

    @Before
    public void initTest() {
        arac = createEntity(em);
    }

    @Test
    @Transactional
    public void createArac() throws Exception {
        int databaseSizeBeforeCreate = aracRepository.findAll().size();

        // Create the Arac
        restAracMockMvc.perform(post("/api/aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arac)))
            .andExpect(status().isCreated());

        // Validate the Arac in the database
        List<Arac> aracList = aracRepository.findAll();
        assertThat(aracList).hasSize(databaseSizeBeforeCreate + 1);
        Arac testArac = aracList.get(aracList.size() - 1);
        assertThat(testArac.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testArac.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testArac.getDetay()).isEqualTo(DEFAULT_DETAY);
        assertThat(testArac.getModelYili()).isEqualTo(DEFAULT_MODEL_YILI);
        assertThat(testArac.getYakitTuru()).isEqualTo(DEFAULT_YAKIT_TURU);
        assertThat(testArac.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testArac.getMuayeneTarih()).isEqualTo(DEFAULT_MUAYENE_TARIH);
        assertThat(testArac.getKaskoTarih()).isEqualTo(DEFAULT_KASKO_TARIH);
        assertThat(testArac.getSigortaTarih()).isEqualTo(DEFAULT_SIGORTA_TARIH);
        assertThat(testArac.getBakimTarih()).isEqualTo(DEFAULT_BAKIM_TARIH);
        assertThat(testArac.getResim()).isEqualTo(DEFAULT_RESIM);
        assertThat(testArac.getResimContentType()).isEqualTo(DEFAULT_RESIM_CONTENT_TYPE);
        assertThat(testArac.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testArac.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAracWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aracRepository.findAll().size();

        // Create the Arac with an existing ID
        arac.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAracMockMvc.perform(post("/api/aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arac)))
            .andExpect(status().isBadRequest());

        // Validate the Arac in the database
        List<Arac> aracList = aracRepository.findAll();
        assertThat(aracList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAracs() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList
        restAracMockMvc.perform(get("/api/aracs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arac.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].detay").value(hasItem(DEFAULT_DETAY.toString())))
            .andExpect(jsonPath("$.[*].modelYili").value(hasItem(DEFAULT_MODEL_YILI.intValue())))
            .andExpect(jsonPath("$.[*].yakitTuru").value(hasItem(DEFAULT_YAKIT_TURU.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].muayeneTarih").value(hasItem(DEFAULT_MUAYENE_TARIH.toString())))
            .andExpect(jsonPath("$.[*].kaskoTarih").value(hasItem(DEFAULT_KASKO_TARIH.toString())))
            .andExpect(jsonPath("$.[*].sigortaTarih").value(hasItem(DEFAULT_SIGORTA_TARIH.toString())))
            .andExpect(jsonPath("$.[*].bakimTarih").value(hasItem(DEFAULT_BAKIM_TARIH.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))));
    }
    
    @Test
    @Transactional
    public void getArac() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get the arac
        restAracMockMvc.perform(get("/api/aracs/{id}", arac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(arac.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.detay").value(DEFAULT_DETAY.toString()))
            .andExpect(jsonPath("$.modelYili").value(DEFAULT_MODEL_YILI.intValue()))
            .andExpect(jsonPath("$.yakitTuru").value(DEFAULT_YAKIT_TURU.toString()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.muayeneTarih").value(DEFAULT_MUAYENE_TARIH.toString()))
            .andExpect(jsonPath("$.kaskoTarih").value(DEFAULT_KASKO_TARIH.toString()))
            .andExpect(jsonPath("$.sigortaTarih").value(DEFAULT_SIGORTA_TARIH.toString()))
            .andExpect(jsonPath("$.bakimTarih").value(DEFAULT_BAKIM_TARIH.toString()))
            .andExpect(jsonPath("$.resimContentType").value(DEFAULT_RESIM_CONTENT_TYPE))
            .andExpect(jsonPath("$.resim").value(Base64Utils.encodeToString(DEFAULT_RESIM)))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)));
    }

    @Test
    @Transactional
    public void getNonExistingArac() throws Exception {
        // Get the arac
        restAracMockMvc.perform(get("/api/aracs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArac() throws Exception {
        // Initialize the database
        aracService.save(arac);

        int databaseSizeBeforeUpdate = aracRepository.findAll().size();

        // Update the arac
        Arac updatedArac = aracRepository.findById(arac.getId()).get();
        // Disconnect from session so that the updates on updatedArac are not directly saved in db
        em.detach(updatedArac);
        updatedArac
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .detay(UPDATED_DETAY)
            .modelYili(UPDATED_MODEL_YILI)
            .yakitTuru(UPDATED_YAKIT_TURU)
            .tarih(UPDATED_TARIH)
            .muayeneTarih(UPDATED_MUAYENE_TARIH)
            .kaskoTarih(UPDATED_KASKO_TARIH)
            .sigortaTarih(UPDATED_SIGORTA_TARIH)
            .bakimTarih(UPDATED_BAKIM_TARIH)
            .resim(UPDATED_RESIM)
            .resimContentType(UPDATED_RESIM_CONTENT_TYPE)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE);

        restAracMockMvc.perform(put("/api/aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArac)))
            .andExpect(status().isOk());

        // Validate the Arac in the database
        List<Arac> aracList = aracRepository.findAll();
        assertThat(aracList).hasSize(databaseSizeBeforeUpdate);
        Arac testArac = aracList.get(aracList.size() - 1);
        assertThat(testArac.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testArac.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testArac.getDetay()).isEqualTo(UPDATED_DETAY);
        assertThat(testArac.getModelYili()).isEqualTo(UPDATED_MODEL_YILI);
        assertThat(testArac.getYakitTuru()).isEqualTo(UPDATED_YAKIT_TURU);
        assertThat(testArac.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testArac.getMuayeneTarih()).isEqualTo(UPDATED_MUAYENE_TARIH);
        assertThat(testArac.getKaskoTarih()).isEqualTo(UPDATED_KASKO_TARIH);
        assertThat(testArac.getSigortaTarih()).isEqualTo(UPDATED_SIGORTA_TARIH);
        assertThat(testArac.getBakimTarih()).isEqualTo(UPDATED_BAKIM_TARIH);
        assertThat(testArac.getResim()).isEqualTo(UPDATED_RESIM);
        assertThat(testArac.getResimContentType()).isEqualTo(UPDATED_RESIM_CONTENT_TYPE);
        assertThat(testArac.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testArac.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingArac() throws Exception {
        int databaseSizeBeforeUpdate = aracRepository.findAll().size();

        // Create the Arac

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAracMockMvc.perform(put("/api/aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arac)))
            .andExpect(status().isBadRequest());

        // Validate the Arac in the database
        List<Arac> aracList = aracRepository.findAll();
        assertThat(aracList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArac() throws Exception {
        // Initialize the database
        aracService.save(arac);

        int databaseSizeBeforeDelete = aracRepository.findAll().size();

        // Delete the arac
        restAracMockMvc.perform(delete("/api/aracs/{id}", arac.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Arac> aracList = aracRepository.findAll();
        assertThat(aracList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arac.class);
        Arac arac1 = new Arac();
        arac1.setId(1L);
        Arac arac2 = new Arac();
        arac2.setId(arac1.getId());
        assertThat(arac1).isEqualTo(arac2);
        arac2.setId(2L);
        assertThat(arac1).isNotEqualTo(arac2);
        arac1.setId(null);
        assertThat(arac1).isNotEqualTo(arac2);
    }
}
