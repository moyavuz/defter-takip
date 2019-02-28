package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Arac;
import com.yavuzturtelekom.domain.Model;
import com.yavuzturtelekom.repository.AracRepository;
import com.yavuzturtelekom.service.AracService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.AracCriteria;
import com.yavuzturtelekom.service.AracQueryService;

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
    private AracQueryService aracQueryService;

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
        final AracResource aracResource = new AracResource(aracService, aracQueryService);
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
    public void getAllAracsByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where ad equals to DEFAULT_AD
        defaultAracShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the aracList where ad equals to UPDATED_AD
        defaultAracShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllAracsByAdIsInShouldWork() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where ad in DEFAULT_AD or UPDATED_AD
        defaultAracShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the aracList where ad equals to UPDATED_AD
        defaultAracShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllAracsByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where ad is not null
        defaultAracShouldBeFound("ad.specified=true");

        // Get all the aracList where ad is null
        defaultAracShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllAracsByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where aciklama equals to DEFAULT_ACIKLAMA
        defaultAracShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the aracList where aciklama equals to UPDATED_ACIKLAMA
        defaultAracShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllAracsByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultAracShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the aracList where aciklama equals to UPDATED_ACIKLAMA
        defaultAracShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllAracsByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where aciklama is not null
        defaultAracShouldBeFound("aciklama.specified=true");

        // Get all the aracList where aciklama is null
        defaultAracShouldNotBeFound("aciklama.specified=false");
    }

    @Test
    @Transactional
    public void getAllAracsByModelYiliIsEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where modelYili equals to DEFAULT_MODEL_YILI
        defaultAracShouldBeFound("modelYili.equals=" + DEFAULT_MODEL_YILI);

        // Get all the aracList where modelYili equals to UPDATED_MODEL_YILI
        defaultAracShouldNotBeFound("modelYili.equals=" + UPDATED_MODEL_YILI);
    }

    @Test
    @Transactional
    public void getAllAracsByModelYiliIsInShouldWork() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where modelYili in DEFAULT_MODEL_YILI or UPDATED_MODEL_YILI
        defaultAracShouldBeFound("modelYili.in=" + DEFAULT_MODEL_YILI + "," + UPDATED_MODEL_YILI);

        // Get all the aracList where modelYili equals to UPDATED_MODEL_YILI
        defaultAracShouldNotBeFound("modelYili.in=" + UPDATED_MODEL_YILI);
    }

    @Test
    @Transactional
    public void getAllAracsByModelYiliIsNullOrNotNull() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where modelYili is not null
        defaultAracShouldBeFound("modelYili.specified=true");

        // Get all the aracList where modelYili is null
        defaultAracShouldNotBeFound("modelYili.specified=false");
    }

    @Test
    @Transactional
    public void getAllAracsByModelYiliIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where modelYili greater than or equals to DEFAULT_MODEL_YILI
        defaultAracShouldBeFound("modelYili.greaterOrEqualThan=" + DEFAULT_MODEL_YILI);

        // Get all the aracList where modelYili greater than or equals to UPDATED_MODEL_YILI
        defaultAracShouldNotBeFound("modelYili.greaterOrEqualThan=" + UPDATED_MODEL_YILI);
    }

    @Test
    @Transactional
    public void getAllAracsByModelYiliIsLessThanSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where modelYili less than or equals to DEFAULT_MODEL_YILI
        defaultAracShouldNotBeFound("modelYili.lessThan=" + DEFAULT_MODEL_YILI);

        // Get all the aracList where modelYili less than or equals to UPDATED_MODEL_YILI
        defaultAracShouldBeFound("modelYili.lessThan=" + UPDATED_MODEL_YILI);
    }


    @Test
    @Transactional
    public void getAllAracsByYakitTuruIsEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where yakitTuru equals to DEFAULT_YAKIT_TURU
        defaultAracShouldBeFound("yakitTuru.equals=" + DEFAULT_YAKIT_TURU);

        // Get all the aracList where yakitTuru equals to UPDATED_YAKIT_TURU
        defaultAracShouldNotBeFound("yakitTuru.equals=" + UPDATED_YAKIT_TURU);
    }

    @Test
    @Transactional
    public void getAllAracsByYakitTuruIsInShouldWork() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where yakitTuru in DEFAULT_YAKIT_TURU or UPDATED_YAKIT_TURU
        defaultAracShouldBeFound("yakitTuru.in=" + DEFAULT_YAKIT_TURU + "," + UPDATED_YAKIT_TURU);

        // Get all the aracList where yakitTuru equals to UPDATED_YAKIT_TURU
        defaultAracShouldNotBeFound("yakitTuru.in=" + UPDATED_YAKIT_TURU);
    }

    @Test
    @Transactional
    public void getAllAracsByYakitTuruIsNullOrNotNull() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where yakitTuru is not null
        defaultAracShouldBeFound("yakitTuru.specified=true");

        // Get all the aracList where yakitTuru is null
        defaultAracShouldNotBeFound("yakitTuru.specified=false");
    }

    @Test
    @Transactional
    public void getAllAracsByTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where tarih equals to DEFAULT_TARIH
        defaultAracShouldBeFound("tarih.equals=" + DEFAULT_TARIH);

        // Get all the aracList where tarih equals to UPDATED_TARIH
        defaultAracShouldNotBeFound("tarih.equals=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByTarihIsInShouldWork() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where tarih in DEFAULT_TARIH or UPDATED_TARIH
        defaultAracShouldBeFound("tarih.in=" + DEFAULT_TARIH + "," + UPDATED_TARIH);

        // Get all the aracList where tarih equals to UPDATED_TARIH
        defaultAracShouldNotBeFound("tarih.in=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where tarih is not null
        defaultAracShouldBeFound("tarih.specified=true");

        // Get all the aracList where tarih is null
        defaultAracShouldNotBeFound("tarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllAracsByTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where tarih greater than or equals to DEFAULT_TARIH
        defaultAracShouldBeFound("tarih.greaterOrEqualThan=" + DEFAULT_TARIH);

        // Get all the aracList where tarih greater than or equals to UPDATED_TARIH
        defaultAracShouldNotBeFound("tarih.greaterOrEqualThan=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where tarih less than or equals to DEFAULT_TARIH
        defaultAracShouldNotBeFound("tarih.lessThan=" + DEFAULT_TARIH);

        // Get all the aracList where tarih less than or equals to UPDATED_TARIH
        defaultAracShouldBeFound("tarih.lessThan=" + UPDATED_TARIH);
    }


    @Test
    @Transactional
    public void getAllAracsByMuayeneTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where muayeneTarih equals to DEFAULT_MUAYENE_TARIH
        defaultAracShouldBeFound("muayeneTarih.equals=" + DEFAULT_MUAYENE_TARIH);

        // Get all the aracList where muayeneTarih equals to UPDATED_MUAYENE_TARIH
        defaultAracShouldNotBeFound("muayeneTarih.equals=" + UPDATED_MUAYENE_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByMuayeneTarihIsInShouldWork() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where muayeneTarih in DEFAULT_MUAYENE_TARIH or UPDATED_MUAYENE_TARIH
        defaultAracShouldBeFound("muayeneTarih.in=" + DEFAULT_MUAYENE_TARIH + "," + UPDATED_MUAYENE_TARIH);

        // Get all the aracList where muayeneTarih equals to UPDATED_MUAYENE_TARIH
        defaultAracShouldNotBeFound("muayeneTarih.in=" + UPDATED_MUAYENE_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByMuayeneTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where muayeneTarih is not null
        defaultAracShouldBeFound("muayeneTarih.specified=true");

        // Get all the aracList where muayeneTarih is null
        defaultAracShouldNotBeFound("muayeneTarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllAracsByMuayeneTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where muayeneTarih greater than or equals to DEFAULT_MUAYENE_TARIH
        defaultAracShouldBeFound("muayeneTarih.greaterOrEqualThan=" + DEFAULT_MUAYENE_TARIH);

        // Get all the aracList where muayeneTarih greater than or equals to UPDATED_MUAYENE_TARIH
        defaultAracShouldNotBeFound("muayeneTarih.greaterOrEqualThan=" + UPDATED_MUAYENE_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByMuayeneTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where muayeneTarih less than or equals to DEFAULT_MUAYENE_TARIH
        defaultAracShouldNotBeFound("muayeneTarih.lessThan=" + DEFAULT_MUAYENE_TARIH);

        // Get all the aracList where muayeneTarih less than or equals to UPDATED_MUAYENE_TARIH
        defaultAracShouldBeFound("muayeneTarih.lessThan=" + UPDATED_MUAYENE_TARIH);
    }


    @Test
    @Transactional
    public void getAllAracsByKaskoTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where kaskoTarih equals to DEFAULT_KASKO_TARIH
        defaultAracShouldBeFound("kaskoTarih.equals=" + DEFAULT_KASKO_TARIH);

        // Get all the aracList where kaskoTarih equals to UPDATED_KASKO_TARIH
        defaultAracShouldNotBeFound("kaskoTarih.equals=" + UPDATED_KASKO_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByKaskoTarihIsInShouldWork() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where kaskoTarih in DEFAULT_KASKO_TARIH or UPDATED_KASKO_TARIH
        defaultAracShouldBeFound("kaskoTarih.in=" + DEFAULT_KASKO_TARIH + "," + UPDATED_KASKO_TARIH);

        // Get all the aracList where kaskoTarih equals to UPDATED_KASKO_TARIH
        defaultAracShouldNotBeFound("kaskoTarih.in=" + UPDATED_KASKO_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByKaskoTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where kaskoTarih is not null
        defaultAracShouldBeFound("kaskoTarih.specified=true");

        // Get all the aracList where kaskoTarih is null
        defaultAracShouldNotBeFound("kaskoTarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllAracsByKaskoTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where kaskoTarih greater than or equals to DEFAULT_KASKO_TARIH
        defaultAracShouldBeFound("kaskoTarih.greaterOrEqualThan=" + DEFAULT_KASKO_TARIH);

        // Get all the aracList where kaskoTarih greater than or equals to UPDATED_KASKO_TARIH
        defaultAracShouldNotBeFound("kaskoTarih.greaterOrEqualThan=" + UPDATED_KASKO_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByKaskoTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where kaskoTarih less than or equals to DEFAULT_KASKO_TARIH
        defaultAracShouldNotBeFound("kaskoTarih.lessThan=" + DEFAULT_KASKO_TARIH);

        // Get all the aracList where kaskoTarih less than or equals to UPDATED_KASKO_TARIH
        defaultAracShouldBeFound("kaskoTarih.lessThan=" + UPDATED_KASKO_TARIH);
    }


    @Test
    @Transactional
    public void getAllAracsBySigortaTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where sigortaTarih equals to DEFAULT_SIGORTA_TARIH
        defaultAracShouldBeFound("sigortaTarih.equals=" + DEFAULT_SIGORTA_TARIH);

        // Get all the aracList where sigortaTarih equals to UPDATED_SIGORTA_TARIH
        defaultAracShouldNotBeFound("sigortaTarih.equals=" + UPDATED_SIGORTA_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsBySigortaTarihIsInShouldWork() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where sigortaTarih in DEFAULT_SIGORTA_TARIH or UPDATED_SIGORTA_TARIH
        defaultAracShouldBeFound("sigortaTarih.in=" + DEFAULT_SIGORTA_TARIH + "," + UPDATED_SIGORTA_TARIH);

        // Get all the aracList where sigortaTarih equals to UPDATED_SIGORTA_TARIH
        defaultAracShouldNotBeFound("sigortaTarih.in=" + UPDATED_SIGORTA_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsBySigortaTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where sigortaTarih is not null
        defaultAracShouldBeFound("sigortaTarih.specified=true");

        // Get all the aracList where sigortaTarih is null
        defaultAracShouldNotBeFound("sigortaTarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllAracsBySigortaTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where sigortaTarih greater than or equals to DEFAULT_SIGORTA_TARIH
        defaultAracShouldBeFound("sigortaTarih.greaterOrEqualThan=" + DEFAULT_SIGORTA_TARIH);

        // Get all the aracList where sigortaTarih greater than or equals to UPDATED_SIGORTA_TARIH
        defaultAracShouldNotBeFound("sigortaTarih.greaterOrEqualThan=" + UPDATED_SIGORTA_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsBySigortaTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where sigortaTarih less than or equals to DEFAULT_SIGORTA_TARIH
        defaultAracShouldNotBeFound("sigortaTarih.lessThan=" + DEFAULT_SIGORTA_TARIH);

        // Get all the aracList where sigortaTarih less than or equals to UPDATED_SIGORTA_TARIH
        defaultAracShouldBeFound("sigortaTarih.lessThan=" + UPDATED_SIGORTA_TARIH);
    }


    @Test
    @Transactional
    public void getAllAracsByBakimTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where bakimTarih equals to DEFAULT_BAKIM_TARIH
        defaultAracShouldBeFound("bakimTarih.equals=" + DEFAULT_BAKIM_TARIH);

        // Get all the aracList where bakimTarih equals to UPDATED_BAKIM_TARIH
        defaultAracShouldNotBeFound("bakimTarih.equals=" + UPDATED_BAKIM_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByBakimTarihIsInShouldWork() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where bakimTarih in DEFAULT_BAKIM_TARIH or UPDATED_BAKIM_TARIH
        defaultAracShouldBeFound("bakimTarih.in=" + DEFAULT_BAKIM_TARIH + "," + UPDATED_BAKIM_TARIH);

        // Get all the aracList where bakimTarih equals to UPDATED_BAKIM_TARIH
        defaultAracShouldNotBeFound("bakimTarih.in=" + UPDATED_BAKIM_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByBakimTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where bakimTarih is not null
        defaultAracShouldBeFound("bakimTarih.specified=true");

        // Get all the aracList where bakimTarih is null
        defaultAracShouldNotBeFound("bakimTarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllAracsByBakimTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where bakimTarih greater than or equals to DEFAULT_BAKIM_TARIH
        defaultAracShouldBeFound("bakimTarih.greaterOrEqualThan=" + DEFAULT_BAKIM_TARIH);

        // Get all the aracList where bakimTarih greater than or equals to UPDATED_BAKIM_TARIH
        defaultAracShouldNotBeFound("bakimTarih.greaterOrEqualThan=" + UPDATED_BAKIM_TARIH);
    }

    @Test
    @Transactional
    public void getAllAracsByBakimTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        aracRepository.saveAndFlush(arac);

        // Get all the aracList where bakimTarih less than or equals to DEFAULT_BAKIM_TARIH
        defaultAracShouldNotBeFound("bakimTarih.lessThan=" + DEFAULT_BAKIM_TARIH);

        // Get all the aracList where bakimTarih less than or equals to UPDATED_BAKIM_TARIH
        defaultAracShouldBeFound("bakimTarih.lessThan=" + UPDATED_BAKIM_TARIH);
    }


    @Test
    @Transactional
    public void getAllAracsByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        Model model = ModelResourceIntTest.createEntity(em);
        em.persist(model);
        em.flush();
        arac.setModel(model);
        aracRepository.saveAndFlush(arac);
        Long modelId = model.getId();

        // Get all the aracList where model equals to modelId
        defaultAracShouldBeFound("modelId.equals=" + modelId);

        // Get all the aracList where model equals to modelId + 1
        defaultAracShouldNotBeFound("modelId.equals=" + (modelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAracShouldBeFound(String filter) throws Exception {
        restAracMockMvc.perform(get("/api/aracs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arac.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)))
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

        // Check, that the count call also returns 1
        restAracMockMvc.perform(get("/api/aracs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAracShouldNotBeFound(String filter) throws Exception {
        restAracMockMvc.perform(get("/api/aracs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAracMockMvc.perform(get("/api/aracs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
