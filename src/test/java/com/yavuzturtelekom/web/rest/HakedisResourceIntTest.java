package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Hakedis;
import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.domain.Proje;
import com.yavuzturtelekom.repository.HakedisRepository;
import com.yavuzturtelekom.service.HakedisService;
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

import com.yavuzturtelekom.domain.enumeration.OnemDurumu;
import com.yavuzturtelekom.domain.enumeration.IsDurumu;
import com.yavuzturtelekom.domain.enumeration.OdemeDurumu;
/**
 * Test class for the HakedisResource REST controller.
 *
 * @see HakedisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class HakedisResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_SERI_NO = 1L;
    private static final Long UPDATED_SERI_NO = 2L;

    private static final String DEFAULT_DEFTER_NO = "AAAAAAAAAA";
    private static final String UPDATED_DEFTER_NO = "BBBBBBBBBB";

    private static final Long DEFAULT_CIZIM_NO = 1L;
    private static final Long UPDATED_CIZIM_NO = 2L;

    private static final OnemDurumu DEFAULT_ONEM_DERECESI = OnemDurumu.ACIL;
    private static final OnemDurumu UPDATED_ONEM_DERECESI = OnemDurumu.IVEDI;

    private static final IsDurumu DEFAULT_IS_DURUMU = IsDurumu.BEKLIYOR;
    private static final IsDurumu UPDATED_IS_DURUMU = IsDurumu.BEKLIYOR_MALZEME;

    private static final OdemeDurumu DEFAULT_ODEME_DURUMU = OdemeDurumu.BEKLIYOR;
    private static final OdemeDurumu UPDATED_ODEME_DURUMU = OdemeDurumu.HAZIR;

    private static final String DEFAULT_ODEME_NO = "AAAAAAAAAA";
    private static final String UPDATED_ODEME_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DETAY = "AAAAAAAAAA";
    private static final String UPDATED_DETAY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_RESIM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESIM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESIM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESIM_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOSYA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYA_CONTENT_TYPE = "image/png";

    @Autowired
    private HakedisRepository hakedisRepository;

    @Autowired
    private HakedisService hakedisService;

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

    private MockMvc restHakedisMockMvc;

    private Hakedis hakedis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HakedisResource hakedisResource = new HakedisResource(hakedisService);
        this.restHakedisMockMvc = MockMvcBuilders.standaloneSetup(hakedisResource)
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
    public static Hakedis createEntity(EntityManager em) {
        Hakedis hakedis = new Hakedis()
            .ad(DEFAULT_AD)
            .tarih(DEFAULT_TARIH)
            .seriNo(DEFAULT_SERI_NO)
            .defterNo(DEFAULT_DEFTER_NO)
            .cizimNo(DEFAULT_CIZIM_NO)
            .onemDerecesi(DEFAULT_ONEM_DERECESI)
            .isDurumu(DEFAULT_IS_DURUMU)
            .odemeDurumu(DEFAULT_ODEME_DURUMU)
            .odemeNo(DEFAULT_ODEME_NO)
            .aciklama(DEFAULT_ACIKLAMA)
            .detay(DEFAULT_DETAY)
            .resim(DEFAULT_RESIM)
            .resimContentType(DEFAULT_RESIM_CONTENT_TYPE)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE);
        // Add required entity
        Ekip ekip = EkipResourceIntTest.createEntity(em);
        em.persist(ekip);
        em.flush();
        hakedis.setEkip(ekip);
        // Add required entity
        Proje proje = ProjeResourceIntTest.createEntity(em);
        em.persist(proje);
        em.flush();
        hakedis.setProje(proje);
        return hakedis;
    }

    @Before
    public void initTest() {
        hakedis = createEntity(em);
    }

    @Test
    @Transactional
    public void createHakedis() throws Exception {
        int databaseSizeBeforeCreate = hakedisRepository.findAll().size();

        // Create the Hakedis
        restHakedisMockMvc.perform(post("/api/hakedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedis)))
            .andExpect(status().isCreated());

        // Validate the Hakedis in the database
        List<Hakedis> hakedisList = hakedisRepository.findAll();
        assertThat(hakedisList).hasSize(databaseSizeBeforeCreate + 1);
        Hakedis testHakedis = hakedisList.get(hakedisList.size() - 1);
        assertThat(testHakedis.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testHakedis.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testHakedis.getSeriNo()).isEqualTo(DEFAULT_SERI_NO);
        assertThat(testHakedis.getDefterNo()).isEqualTo(DEFAULT_DEFTER_NO);
        assertThat(testHakedis.getCizimNo()).isEqualTo(DEFAULT_CIZIM_NO);
        assertThat(testHakedis.getOnemDerecesi()).isEqualTo(DEFAULT_ONEM_DERECESI);
        assertThat(testHakedis.getIsDurumu()).isEqualTo(DEFAULT_IS_DURUMU);
        assertThat(testHakedis.getOdemeDurumu()).isEqualTo(DEFAULT_ODEME_DURUMU);
        assertThat(testHakedis.getOdemeNo()).isEqualTo(DEFAULT_ODEME_NO);
        assertThat(testHakedis.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testHakedis.getDetay()).isEqualTo(DEFAULT_DETAY);
        assertThat(testHakedis.getResim()).isEqualTo(DEFAULT_RESIM);
        assertThat(testHakedis.getResimContentType()).isEqualTo(DEFAULT_RESIM_CONTENT_TYPE);
        assertThat(testHakedis.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testHakedis.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createHakedisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hakedisRepository.findAll().size();

        // Create the Hakedis with an existing ID
        hakedis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHakedisMockMvc.perform(post("/api/hakedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedis)))
            .andExpect(status().isBadRequest());

        // Validate the Hakedis in the database
        List<Hakedis> hakedisList = hakedisRepository.findAll();
        assertThat(hakedisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = hakedisRepository.findAll().size();
        // set the field null
        hakedis.setAd(null);

        // Create the Hakedis, which fails.

        restHakedisMockMvc.perform(post("/api/hakedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedis)))
            .andExpect(status().isBadRequest());

        List<Hakedis> hakedisList = hakedisRepository.findAll();
        assertThat(hakedisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHakedis() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList
        restHakedisMockMvc.perform(get("/api/hakedis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hakedis.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].seriNo").value(hasItem(DEFAULT_SERI_NO.intValue())))
            .andExpect(jsonPath("$.[*].defterNo").value(hasItem(DEFAULT_DEFTER_NO.toString())))
            .andExpect(jsonPath("$.[*].cizimNo").value(hasItem(DEFAULT_CIZIM_NO.intValue())))
            .andExpect(jsonPath("$.[*].onemDerecesi").value(hasItem(DEFAULT_ONEM_DERECESI.toString())))
            .andExpect(jsonPath("$.[*].isDurumu").value(hasItem(DEFAULT_IS_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].odemeDurumu").value(hasItem(DEFAULT_ODEME_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].odemeNo").value(hasItem(DEFAULT_ODEME_NO.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].detay").value(hasItem(DEFAULT_DETAY.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))));
    }
    
    @Test
    @Transactional
    public void getHakedis() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get the hakedis
        restHakedisMockMvc.perform(get("/api/hakedis/{id}", hakedis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hakedis.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.seriNo").value(DEFAULT_SERI_NO.intValue()))
            .andExpect(jsonPath("$.defterNo").value(DEFAULT_DEFTER_NO.toString()))
            .andExpect(jsonPath("$.cizimNo").value(DEFAULT_CIZIM_NO.intValue()))
            .andExpect(jsonPath("$.onemDerecesi").value(DEFAULT_ONEM_DERECESI.toString()))
            .andExpect(jsonPath("$.isDurumu").value(DEFAULT_IS_DURUMU.toString()))
            .andExpect(jsonPath("$.odemeDurumu").value(DEFAULT_ODEME_DURUMU.toString()))
            .andExpect(jsonPath("$.odemeNo").value(DEFAULT_ODEME_NO.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.detay").value(DEFAULT_DETAY.toString()))
            .andExpect(jsonPath("$.resimContentType").value(DEFAULT_RESIM_CONTENT_TYPE))
            .andExpect(jsonPath("$.resim").value(Base64Utils.encodeToString(DEFAULT_RESIM)))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)));
    }

    @Test
    @Transactional
    public void getNonExistingHakedis() throws Exception {
        // Get the hakedis
        restHakedisMockMvc.perform(get("/api/hakedis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHakedis() throws Exception {
        // Initialize the database
        hakedisService.save(hakedis);

        int databaseSizeBeforeUpdate = hakedisRepository.findAll().size();

        // Update the hakedis
        Hakedis updatedHakedis = hakedisRepository.findById(hakedis.getId()).get();
        // Disconnect from session so that the updates on updatedHakedis are not directly saved in db
        em.detach(updatedHakedis);
        updatedHakedis
            .ad(UPDATED_AD)
            .tarih(UPDATED_TARIH)
            .seriNo(UPDATED_SERI_NO)
            .defterNo(UPDATED_DEFTER_NO)
            .cizimNo(UPDATED_CIZIM_NO)
            .onemDerecesi(UPDATED_ONEM_DERECESI)
            .isDurumu(UPDATED_IS_DURUMU)
            .odemeDurumu(UPDATED_ODEME_DURUMU)
            .odemeNo(UPDATED_ODEME_NO)
            .aciklama(UPDATED_ACIKLAMA)
            .detay(UPDATED_DETAY)
            .resim(UPDATED_RESIM)
            .resimContentType(UPDATED_RESIM_CONTENT_TYPE)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE);

        restHakedisMockMvc.perform(put("/api/hakedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHakedis)))
            .andExpect(status().isOk());

        // Validate the Hakedis in the database
        List<Hakedis> hakedisList = hakedisRepository.findAll();
        assertThat(hakedisList).hasSize(databaseSizeBeforeUpdate);
        Hakedis testHakedis = hakedisList.get(hakedisList.size() - 1);
        assertThat(testHakedis.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testHakedis.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testHakedis.getSeriNo()).isEqualTo(UPDATED_SERI_NO);
        assertThat(testHakedis.getDefterNo()).isEqualTo(UPDATED_DEFTER_NO);
        assertThat(testHakedis.getCizimNo()).isEqualTo(UPDATED_CIZIM_NO);
        assertThat(testHakedis.getOnemDerecesi()).isEqualTo(UPDATED_ONEM_DERECESI);
        assertThat(testHakedis.getIsDurumu()).isEqualTo(UPDATED_IS_DURUMU);
        assertThat(testHakedis.getOdemeDurumu()).isEqualTo(UPDATED_ODEME_DURUMU);
        assertThat(testHakedis.getOdemeNo()).isEqualTo(UPDATED_ODEME_NO);
        assertThat(testHakedis.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testHakedis.getDetay()).isEqualTo(UPDATED_DETAY);
        assertThat(testHakedis.getResim()).isEqualTo(UPDATED_RESIM);
        assertThat(testHakedis.getResimContentType()).isEqualTo(UPDATED_RESIM_CONTENT_TYPE);
        assertThat(testHakedis.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testHakedis.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingHakedis() throws Exception {
        int databaseSizeBeforeUpdate = hakedisRepository.findAll().size();

        // Create the Hakedis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHakedisMockMvc.perform(put("/api/hakedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedis)))
            .andExpect(status().isBadRequest());

        // Validate the Hakedis in the database
        List<Hakedis> hakedisList = hakedisRepository.findAll();
        assertThat(hakedisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHakedis() throws Exception {
        // Initialize the database
        hakedisService.save(hakedis);

        int databaseSizeBeforeDelete = hakedisRepository.findAll().size();

        // Delete the hakedis
        restHakedisMockMvc.perform(delete("/api/hakedis/{id}", hakedis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hakedis> hakedisList = hakedisRepository.findAll();
        assertThat(hakedisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hakedis.class);
        Hakedis hakedis1 = new Hakedis();
        hakedis1.setId(1L);
        Hakedis hakedis2 = new Hakedis();
        hakedis2.setId(hakedis1.getId());
        assertThat(hakedis1).isEqualTo(hakedis2);
        hakedis2.setId(2L);
        assertThat(hakedis1).isNotEqualTo(hakedis2);
        hakedis1.setId(null);
        assertThat(hakedis1).isNotEqualTo(hakedis2);
    }
}
