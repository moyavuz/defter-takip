package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Hakedis;
import com.yavuzturtelekom.domain.HakedisDetay;
import com.yavuzturtelekom.domain.Santral;
import com.yavuzturtelekom.domain.HakedisTuru;
import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.domain.Proje;
import com.yavuzturtelekom.repository.HakedisRepository;
import com.yavuzturtelekom.service.HakedisService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.HakedisCriteria;
import com.yavuzturtelekom.service.HakedisQueryService;

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
    private HakedisQueryService hakedisQueryService;

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
        final HakedisResource hakedisResource = new HakedisResource(hakedisService, hakedisQueryService);
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
    public void getAllHakedisByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where ad equals to DEFAULT_AD
        defaultHakedisShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the hakedisList where ad equals to UPDATED_AD
        defaultHakedisShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllHakedisByAdIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where ad in DEFAULT_AD or UPDATED_AD
        defaultHakedisShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the hakedisList where ad equals to UPDATED_AD
        defaultHakedisShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllHakedisByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where ad is not null
        defaultHakedisShouldBeFound("ad.specified=true");

        // Get all the hakedisList where ad is null
        defaultHakedisShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisByTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where tarih equals to DEFAULT_TARIH
        defaultHakedisShouldBeFound("tarih.equals=" + DEFAULT_TARIH);

        // Get all the hakedisList where tarih equals to UPDATED_TARIH
        defaultHakedisShouldNotBeFound("tarih.equals=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllHakedisByTarihIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where tarih in DEFAULT_TARIH or UPDATED_TARIH
        defaultHakedisShouldBeFound("tarih.in=" + DEFAULT_TARIH + "," + UPDATED_TARIH);

        // Get all the hakedisList where tarih equals to UPDATED_TARIH
        defaultHakedisShouldNotBeFound("tarih.in=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllHakedisByTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where tarih is not null
        defaultHakedisShouldBeFound("tarih.specified=true");

        // Get all the hakedisList where tarih is null
        defaultHakedisShouldNotBeFound("tarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisByTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where tarih greater than or equals to DEFAULT_TARIH
        defaultHakedisShouldBeFound("tarih.greaterOrEqualThan=" + DEFAULT_TARIH);

        // Get all the hakedisList where tarih greater than or equals to UPDATED_TARIH
        defaultHakedisShouldNotBeFound("tarih.greaterOrEqualThan=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllHakedisByTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where tarih less than or equals to DEFAULT_TARIH
        defaultHakedisShouldNotBeFound("tarih.lessThan=" + DEFAULT_TARIH);

        // Get all the hakedisList where tarih less than or equals to UPDATED_TARIH
        defaultHakedisShouldBeFound("tarih.lessThan=" + UPDATED_TARIH);
    }


    @Test
    @Transactional
    public void getAllHakedisBySeriNoIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where seriNo equals to DEFAULT_SERI_NO
        defaultHakedisShouldBeFound("seriNo.equals=" + DEFAULT_SERI_NO);

        // Get all the hakedisList where seriNo equals to UPDATED_SERI_NO
        defaultHakedisShouldNotBeFound("seriNo.equals=" + UPDATED_SERI_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisBySeriNoIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where seriNo in DEFAULT_SERI_NO or UPDATED_SERI_NO
        defaultHakedisShouldBeFound("seriNo.in=" + DEFAULT_SERI_NO + "," + UPDATED_SERI_NO);

        // Get all the hakedisList where seriNo equals to UPDATED_SERI_NO
        defaultHakedisShouldNotBeFound("seriNo.in=" + UPDATED_SERI_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisBySeriNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where seriNo is not null
        defaultHakedisShouldBeFound("seriNo.specified=true");

        // Get all the hakedisList where seriNo is null
        defaultHakedisShouldNotBeFound("seriNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisBySeriNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where seriNo greater than or equals to DEFAULT_SERI_NO
        defaultHakedisShouldBeFound("seriNo.greaterOrEqualThan=" + DEFAULT_SERI_NO);

        // Get all the hakedisList where seriNo greater than or equals to UPDATED_SERI_NO
        defaultHakedisShouldNotBeFound("seriNo.greaterOrEqualThan=" + UPDATED_SERI_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisBySeriNoIsLessThanSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where seriNo less than or equals to DEFAULT_SERI_NO
        defaultHakedisShouldNotBeFound("seriNo.lessThan=" + DEFAULT_SERI_NO);

        // Get all the hakedisList where seriNo less than or equals to UPDATED_SERI_NO
        defaultHakedisShouldBeFound("seriNo.lessThan=" + UPDATED_SERI_NO);
    }


    @Test
    @Transactional
    public void getAllHakedisByDefterNoIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where defterNo equals to DEFAULT_DEFTER_NO
        defaultHakedisShouldBeFound("defterNo.equals=" + DEFAULT_DEFTER_NO);

        // Get all the hakedisList where defterNo equals to UPDATED_DEFTER_NO
        defaultHakedisShouldNotBeFound("defterNo.equals=" + UPDATED_DEFTER_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisByDefterNoIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where defterNo in DEFAULT_DEFTER_NO or UPDATED_DEFTER_NO
        defaultHakedisShouldBeFound("defterNo.in=" + DEFAULT_DEFTER_NO + "," + UPDATED_DEFTER_NO);

        // Get all the hakedisList where defterNo equals to UPDATED_DEFTER_NO
        defaultHakedisShouldNotBeFound("defterNo.in=" + UPDATED_DEFTER_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisByDefterNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where defterNo is not null
        defaultHakedisShouldBeFound("defterNo.specified=true");

        // Get all the hakedisList where defterNo is null
        defaultHakedisShouldNotBeFound("defterNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisByCizimNoIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where cizimNo equals to DEFAULT_CIZIM_NO
        defaultHakedisShouldBeFound("cizimNo.equals=" + DEFAULT_CIZIM_NO);

        // Get all the hakedisList where cizimNo equals to UPDATED_CIZIM_NO
        defaultHakedisShouldNotBeFound("cizimNo.equals=" + UPDATED_CIZIM_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisByCizimNoIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where cizimNo in DEFAULT_CIZIM_NO or UPDATED_CIZIM_NO
        defaultHakedisShouldBeFound("cizimNo.in=" + DEFAULT_CIZIM_NO + "," + UPDATED_CIZIM_NO);

        // Get all the hakedisList where cizimNo equals to UPDATED_CIZIM_NO
        defaultHakedisShouldNotBeFound("cizimNo.in=" + UPDATED_CIZIM_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisByCizimNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where cizimNo is not null
        defaultHakedisShouldBeFound("cizimNo.specified=true");

        // Get all the hakedisList where cizimNo is null
        defaultHakedisShouldNotBeFound("cizimNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisByCizimNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where cizimNo greater than or equals to DEFAULT_CIZIM_NO
        defaultHakedisShouldBeFound("cizimNo.greaterOrEqualThan=" + DEFAULT_CIZIM_NO);

        // Get all the hakedisList where cizimNo greater than or equals to UPDATED_CIZIM_NO
        defaultHakedisShouldNotBeFound("cizimNo.greaterOrEqualThan=" + UPDATED_CIZIM_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisByCizimNoIsLessThanSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where cizimNo less than or equals to DEFAULT_CIZIM_NO
        defaultHakedisShouldNotBeFound("cizimNo.lessThan=" + DEFAULT_CIZIM_NO);

        // Get all the hakedisList where cizimNo less than or equals to UPDATED_CIZIM_NO
        defaultHakedisShouldBeFound("cizimNo.lessThan=" + UPDATED_CIZIM_NO);
    }


    @Test
    @Transactional
    public void getAllHakedisByOnemDerecesiIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where onemDerecesi equals to DEFAULT_ONEM_DERECESI
        defaultHakedisShouldBeFound("onemDerecesi.equals=" + DEFAULT_ONEM_DERECESI);

        // Get all the hakedisList where onemDerecesi equals to UPDATED_ONEM_DERECESI
        defaultHakedisShouldNotBeFound("onemDerecesi.equals=" + UPDATED_ONEM_DERECESI);
    }

    @Test
    @Transactional
    public void getAllHakedisByOnemDerecesiIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where onemDerecesi in DEFAULT_ONEM_DERECESI or UPDATED_ONEM_DERECESI
        defaultHakedisShouldBeFound("onemDerecesi.in=" + DEFAULT_ONEM_DERECESI + "," + UPDATED_ONEM_DERECESI);

        // Get all the hakedisList where onemDerecesi equals to UPDATED_ONEM_DERECESI
        defaultHakedisShouldNotBeFound("onemDerecesi.in=" + UPDATED_ONEM_DERECESI);
    }

    @Test
    @Transactional
    public void getAllHakedisByOnemDerecesiIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where onemDerecesi is not null
        defaultHakedisShouldBeFound("onemDerecesi.specified=true");

        // Get all the hakedisList where onemDerecesi is null
        defaultHakedisShouldNotBeFound("onemDerecesi.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisByIsDurumuIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where isDurumu equals to DEFAULT_IS_DURUMU
        defaultHakedisShouldBeFound("isDurumu.equals=" + DEFAULT_IS_DURUMU);

        // Get all the hakedisList where isDurumu equals to UPDATED_IS_DURUMU
        defaultHakedisShouldNotBeFound("isDurumu.equals=" + UPDATED_IS_DURUMU);
    }

    @Test
    @Transactional
    public void getAllHakedisByIsDurumuIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where isDurumu in DEFAULT_IS_DURUMU or UPDATED_IS_DURUMU
        defaultHakedisShouldBeFound("isDurumu.in=" + DEFAULT_IS_DURUMU + "," + UPDATED_IS_DURUMU);

        // Get all the hakedisList where isDurumu equals to UPDATED_IS_DURUMU
        defaultHakedisShouldNotBeFound("isDurumu.in=" + UPDATED_IS_DURUMU);
    }

    @Test
    @Transactional
    public void getAllHakedisByIsDurumuIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where isDurumu is not null
        defaultHakedisShouldBeFound("isDurumu.specified=true");

        // Get all the hakedisList where isDurumu is null
        defaultHakedisShouldNotBeFound("isDurumu.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisByOdemeDurumuIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where odemeDurumu equals to DEFAULT_ODEME_DURUMU
        defaultHakedisShouldBeFound("odemeDurumu.equals=" + DEFAULT_ODEME_DURUMU);

        // Get all the hakedisList where odemeDurumu equals to UPDATED_ODEME_DURUMU
        defaultHakedisShouldNotBeFound("odemeDurumu.equals=" + UPDATED_ODEME_DURUMU);
    }

    @Test
    @Transactional
    public void getAllHakedisByOdemeDurumuIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where odemeDurumu in DEFAULT_ODEME_DURUMU or UPDATED_ODEME_DURUMU
        defaultHakedisShouldBeFound("odemeDurumu.in=" + DEFAULT_ODEME_DURUMU + "," + UPDATED_ODEME_DURUMU);

        // Get all the hakedisList where odemeDurumu equals to UPDATED_ODEME_DURUMU
        defaultHakedisShouldNotBeFound("odemeDurumu.in=" + UPDATED_ODEME_DURUMU);
    }

    @Test
    @Transactional
    public void getAllHakedisByOdemeDurumuIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where odemeDurumu is not null
        defaultHakedisShouldBeFound("odemeDurumu.specified=true");

        // Get all the hakedisList where odemeDurumu is null
        defaultHakedisShouldNotBeFound("odemeDurumu.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisByOdemeNoIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where odemeNo equals to DEFAULT_ODEME_NO
        defaultHakedisShouldBeFound("odemeNo.equals=" + DEFAULT_ODEME_NO);

        // Get all the hakedisList where odemeNo equals to UPDATED_ODEME_NO
        defaultHakedisShouldNotBeFound("odemeNo.equals=" + UPDATED_ODEME_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisByOdemeNoIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where odemeNo in DEFAULT_ODEME_NO or UPDATED_ODEME_NO
        defaultHakedisShouldBeFound("odemeNo.in=" + DEFAULT_ODEME_NO + "," + UPDATED_ODEME_NO);

        // Get all the hakedisList where odemeNo equals to UPDATED_ODEME_NO
        defaultHakedisShouldNotBeFound("odemeNo.in=" + UPDATED_ODEME_NO);
    }

    @Test
    @Transactional
    public void getAllHakedisByOdemeNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where odemeNo is not null
        defaultHakedisShouldBeFound("odemeNo.specified=true");

        // Get all the hakedisList where odemeNo is null
        defaultHakedisShouldNotBeFound("odemeNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where aciklama equals to DEFAULT_ACIKLAMA
        defaultHakedisShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the hakedisList where aciklama equals to UPDATED_ACIKLAMA
        defaultHakedisShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllHakedisByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultHakedisShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the hakedisList where aciklama equals to UPDATED_ACIKLAMA
        defaultHakedisShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllHakedisByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        hakedisRepository.saveAndFlush(hakedis);

        // Get all the hakedisList where aciklama is not null
        defaultHakedisShouldBeFound("aciklama.specified=true");

        // Get all the hakedisList where aciklama is null
        defaultHakedisShouldNotBeFound("aciklama.specified=false");
    }

    @Test
    @Transactional
    public void getAllHakedisByHakedisDetayIsEqualToSomething() throws Exception {
        // Initialize the database
        HakedisDetay hakedisDetay = HakedisDetayResourceIntTest.createEntity(em);
        em.persist(hakedisDetay);
        em.flush();
        hakedis.addHakedisDetay(hakedisDetay);
        hakedisRepository.saveAndFlush(hakedis);
        Long hakedisDetayId = hakedisDetay.getId();

        // Get all the hakedisList where hakedisDetay equals to hakedisDetayId
        defaultHakedisShouldBeFound("hakedisDetayId.equals=" + hakedisDetayId);

        // Get all the hakedisList where hakedisDetay equals to hakedisDetayId + 1
        defaultHakedisShouldNotBeFound("hakedisDetayId.equals=" + (hakedisDetayId + 1));
    }


    @Test
    @Transactional
    public void getAllHakedisBySantralIsEqualToSomething() throws Exception {
        // Initialize the database
        Santral santral = SantralResourceIntTest.createEntity(em);
        em.persist(santral);
        em.flush();
        hakedis.setSantral(santral);
        hakedisRepository.saveAndFlush(hakedis);
        Long santralId = santral.getId();

        // Get all the hakedisList where santral equals to santralId
        defaultHakedisShouldBeFound("santralId.equals=" + santralId);

        // Get all the hakedisList where santral equals to santralId + 1
        defaultHakedisShouldNotBeFound("santralId.equals=" + (santralId + 1));
    }


    @Test
    @Transactional
    public void getAllHakedisByTuruIsEqualToSomething() throws Exception {
        // Initialize the database
        HakedisTuru turu = HakedisTuruResourceIntTest.createEntity(em);
        em.persist(turu);
        em.flush();
        hakedis.setTuru(turu);
        hakedisRepository.saveAndFlush(hakedis);
        Long turuId = turu.getId();

        // Get all the hakedisList where turu equals to turuId
        defaultHakedisShouldBeFound("turuId.equals=" + turuId);

        // Get all the hakedisList where turu equals to turuId + 1
        defaultHakedisShouldNotBeFound("turuId.equals=" + (turuId + 1));
    }


    @Test
    @Transactional
    public void getAllHakedisByEkipIsEqualToSomething() throws Exception {
        // Initialize the database
        Ekip ekip = EkipResourceIntTest.createEntity(em);
        em.persist(ekip);
        em.flush();
        hakedis.setEkip(ekip);
        hakedisRepository.saveAndFlush(hakedis);
        Long ekipId = ekip.getId();

        // Get all the hakedisList where ekip equals to ekipId
        defaultHakedisShouldBeFound("ekipId.equals=" + ekipId);

        // Get all the hakedisList where ekip equals to ekipId + 1
        defaultHakedisShouldNotBeFound("ekipId.equals=" + (ekipId + 1));
    }


    @Test
    @Transactional
    public void getAllHakedisByProjeIsEqualToSomething() throws Exception {
        // Initialize the database
        Proje proje = ProjeResourceIntTest.createEntity(em);
        em.persist(proje);
        em.flush();
        hakedis.setProje(proje);
        hakedisRepository.saveAndFlush(hakedis);
        Long projeId = proje.getId();

        // Get all the hakedisList where proje equals to projeId
        defaultHakedisShouldBeFound("projeId.equals=" + projeId);

        // Get all the hakedisList where proje equals to projeId + 1
        defaultHakedisShouldNotBeFound("projeId.equals=" + (projeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultHakedisShouldBeFound(String filter) throws Exception {
        restHakedisMockMvc.perform(get("/api/hakedis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hakedis.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].seriNo").value(hasItem(DEFAULT_SERI_NO.intValue())))
            .andExpect(jsonPath("$.[*].defterNo").value(hasItem(DEFAULT_DEFTER_NO)))
            .andExpect(jsonPath("$.[*].cizimNo").value(hasItem(DEFAULT_CIZIM_NO.intValue())))
            .andExpect(jsonPath("$.[*].onemDerecesi").value(hasItem(DEFAULT_ONEM_DERECESI.toString())))
            .andExpect(jsonPath("$.[*].isDurumu").value(hasItem(DEFAULT_IS_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].odemeDurumu").value(hasItem(DEFAULT_ODEME_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].odemeNo").value(hasItem(DEFAULT_ODEME_NO)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)))
            .andExpect(jsonPath("$.[*].detay").value(hasItem(DEFAULT_DETAY.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))));

        // Check, that the count call also returns 1
        restHakedisMockMvc.perform(get("/api/hakedis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultHakedisShouldNotBeFound(String filter) throws Exception {
        restHakedisMockMvc.perform(get("/api/hakedis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHakedisMockMvc.perform(get("/api/hakedis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
