package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Proje;
import com.yavuzturtelekom.domain.Hakedis;
import com.yavuzturtelekom.domain.ProjeTuru;
import com.yavuzturtelekom.domain.Mudurluk;
import com.yavuzturtelekom.repository.ProjeRepository;
import com.yavuzturtelekom.service.ProjeService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.ProjeCriteria;
import com.yavuzturtelekom.service.ProjeQueryService;

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

import com.yavuzturtelekom.domain.enumeration.IsDurumu;
/**
 * Test class for the ProjeResource REST controller.
 *
 * @see ProjeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class ProjeResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final Long DEFAULT_PROTOKOL_NO = 1L;
    private static final Long UPDATED_PROTOKOL_NO = 2L;

    private static final IsDurumu DEFAULT_DURUMU = IsDurumu.BEKLIYOR;
    private static final IsDurumu UPDATED_DURUMU = IsDurumu.BEKLIYOR_MALZEME;

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

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

    private static final LocalDate DEFAULT_BASLAMA_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BASLAMA_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BITIS_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BITIS_TARIHI = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProjeRepository projeRepository;

    @Autowired
    private ProjeService projeService;

    @Autowired
    private ProjeQueryService projeQueryService;

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

    private MockMvc restProjeMockMvc;

    private Proje proje;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjeResource projeResource = new ProjeResource(projeService, projeQueryService);
        this.restProjeMockMvc = MockMvcBuilders.standaloneSetup(projeResource)
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
    public static Proje createEntity(EntityManager em) {
        Proje proje = new Proje()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .protokolNo(DEFAULT_PROTOKOL_NO)
            .durumu(DEFAULT_DURUMU)
            .tarih(DEFAULT_TARIH)
            .detay(DEFAULT_DETAY)
            .resim(DEFAULT_RESIM)
            .resimContentType(DEFAULT_RESIM_CONTENT_TYPE)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE)
            .baslamaTarihi(DEFAULT_BASLAMA_TARIHI)
            .bitisTarihi(DEFAULT_BITIS_TARIHI);
        // Add required entity
        ProjeTuru projeTuru = ProjeTuruResourceIntTest.createEntity(em);
        em.persist(projeTuru);
        em.flush();
        proje.setTuru(projeTuru);
        return proje;
    }

    @Before
    public void initTest() {
        proje = createEntity(em);
    }

    @Test
    @Transactional
    public void createProje() throws Exception {
        int databaseSizeBeforeCreate = projeRepository.findAll().size();

        // Create the Proje
        restProjeMockMvc.perform(post("/api/projes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proje)))
            .andExpect(status().isCreated());

        // Validate the Proje in the database
        List<Proje> projeList = projeRepository.findAll();
        assertThat(projeList).hasSize(databaseSizeBeforeCreate + 1);
        Proje testProje = projeList.get(projeList.size() - 1);
        assertThat(testProje.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testProje.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testProje.getProtokolNo()).isEqualTo(DEFAULT_PROTOKOL_NO);
        assertThat(testProje.getDurumu()).isEqualTo(DEFAULT_DURUMU);
        assertThat(testProje.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testProje.getDetay()).isEqualTo(DEFAULT_DETAY);
        assertThat(testProje.getResim()).isEqualTo(DEFAULT_RESIM);
        assertThat(testProje.getResimContentType()).isEqualTo(DEFAULT_RESIM_CONTENT_TYPE);
        assertThat(testProje.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testProje.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
        assertThat(testProje.getBaslamaTarihi()).isEqualTo(DEFAULT_BASLAMA_TARIHI);
        assertThat(testProje.getBitisTarihi()).isEqualTo(DEFAULT_BITIS_TARIHI);
    }

    @Test
    @Transactional
    public void createProjeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projeRepository.findAll().size();

        // Create the Proje with an existing ID
        proje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjeMockMvc.perform(post("/api/projes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proje)))
            .andExpect(status().isBadRequest());

        // Validate the Proje in the database
        List<Proje> projeList = projeRepository.findAll();
        assertThat(projeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projeRepository.findAll().size();
        // set the field null
        proje.setAd(null);

        // Create the Proje, which fails.

        restProjeMockMvc.perform(post("/api/projes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proje)))
            .andExpect(status().isBadRequest());

        List<Proje> projeList = projeRepository.findAll();
        assertThat(projeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjes() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList
        restProjeMockMvc.perform(get("/api/projes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proje.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].protokolNo").value(hasItem(DEFAULT_PROTOKOL_NO.intValue())))
            .andExpect(jsonPath("$.[*].durumu").value(hasItem(DEFAULT_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].detay").value(hasItem(DEFAULT_DETAY.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))))
            .andExpect(jsonPath("$.[*].baslamaTarihi").value(hasItem(DEFAULT_BASLAMA_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].bitisTarihi").value(hasItem(DEFAULT_BITIS_TARIHI.toString())));
    }
    
    @Test
    @Transactional
    public void getProje() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get the proje
        restProjeMockMvc.perform(get("/api/projes/{id}", proje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proje.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.protokolNo").value(DEFAULT_PROTOKOL_NO.intValue()))
            .andExpect(jsonPath("$.durumu").value(DEFAULT_DURUMU.toString()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.detay").value(DEFAULT_DETAY.toString()))
            .andExpect(jsonPath("$.resimContentType").value(DEFAULT_RESIM_CONTENT_TYPE))
            .andExpect(jsonPath("$.resim").value(Base64Utils.encodeToString(DEFAULT_RESIM)))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)))
            .andExpect(jsonPath("$.baslamaTarihi").value(DEFAULT_BASLAMA_TARIHI.toString()))
            .andExpect(jsonPath("$.bitisTarihi").value(DEFAULT_BITIS_TARIHI.toString()));
    }

    @Test
    @Transactional
    public void getAllProjesByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where ad equals to DEFAULT_AD
        defaultProjeShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the projeList where ad equals to UPDATED_AD
        defaultProjeShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllProjesByAdIsInShouldWork() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where ad in DEFAULT_AD or UPDATED_AD
        defaultProjeShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the projeList where ad equals to UPDATED_AD
        defaultProjeShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllProjesByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where ad is not null
        defaultProjeShouldBeFound("ad.specified=true");

        // Get all the projeList where ad is null
        defaultProjeShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjesByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where aciklama equals to DEFAULT_ACIKLAMA
        defaultProjeShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the projeList where aciklama equals to UPDATED_ACIKLAMA
        defaultProjeShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllProjesByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultProjeShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the projeList where aciklama equals to UPDATED_ACIKLAMA
        defaultProjeShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllProjesByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where aciklama is not null
        defaultProjeShouldBeFound("aciklama.specified=true");

        // Get all the projeList where aciklama is null
        defaultProjeShouldNotBeFound("aciklama.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjesByProtokolNoIsEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where protokolNo equals to DEFAULT_PROTOKOL_NO
        defaultProjeShouldBeFound("protokolNo.equals=" + DEFAULT_PROTOKOL_NO);

        // Get all the projeList where protokolNo equals to UPDATED_PROTOKOL_NO
        defaultProjeShouldNotBeFound("protokolNo.equals=" + UPDATED_PROTOKOL_NO);
    }

    @Test
    @Transactional
    public void getAllProjesByProtokolNoIsInShouldWork() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where protokolNo in DEFAULT_PROTOKOL_NO or UPDATED_PROTOKOL_NO
        defaultProjeShouldBeFound("protokolNo.in=" + DEFAULT_PROTOKOL_NO + "," + UPDATED_PROTOKOL_NO);

        // Get all the projeList where protokolNo equals to UPDATED_PROTOKOL_NO
        defaultProjeShouldNotBeFound("protokolNo.in=" + UPDATED_PROTOKOL_NO);
    }

    @Test
    @Transactional
    public void getAllProjesByProtokolNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where protokolNo is not null
        defaultProjeShouldBeFound("protokolNo.specified=true");

        // Get all the projeList where protokolNo is null
        defaultProjeShouldNotBeFound("protokolNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjesByProtokolNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where protokolNo greater than or equals to DEFAULT_PROTOKOL_NO
        defaultProjeShouldBeFound("protokolNo.greaterOrEqualThan=" + DEFAULT_PROTOKOL_NO);

        // Get all the projeList where protokolNo greater than or equals to UPDATED_PROTOKOL_NO
        defaultProjeShouldNotBeFound("protokolNo.greaterOrEqualThan=" + UPDATED_PROTOKOL_NO);
    }

    @Test
    @Transactional
    public void getAllProjesByProtokolNoIsLessThanSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where protokolNo less than or equals to DEFAULT_PROTOKOL_NO
        defaultProjeShouldNotBeFound("protokolNo.lessThan=" + DEFAULT_PROTOKOL_NO);

        // Get all the projeList where protokolNo less than or equals to UPDATED_PROTOKOL_NO
        defaultProjeShouldBeFound("protokolNo.lessThan=" + UPDATED_PROTOKOL_NO);
    }


    @Test
    @Transactional
    public void getAllProjesByDurumuIsEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where durumu equals to DEFAULT_DURUMU
        defaultProjeShouldBeFound("durumu.equals=" + DEFAULT_DURUMU);

        // Get all the projeList where durumu equals to UPDATED_DURUMU
        defaultProjeShouldNotBeFound("durumu.equals=" + UPDATED_DURUMU);
    }

    @Test
    @Transactional
    public void getAllProjesByDurumuIsInShouldWork() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where durumu in DEFAULT_DURUMU or UPDATED_DURUMU
        defaultProjeShouldBeFound("durumu.in=" + DEFAULT_DURUMU + "," + UPDATED_DURUMU);

        // Get all the projeList where durumu equals to UPDATED_DURUMU
        defaultProjeShouldNotBeFound("durumu.in=" + UPDATED_DURUMU);
    }

    @Test
    @Transactional
    public void getAllProjesByDurumuIsNullOrNotNull() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where durumu is not null
        defaultProjeShouldBeFound("durumu.specified=true");

        // Get all the projeList where durumu is null
        defaultProjeShouldNotBeFound("durumu.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjesByTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where tarih equals to DEFAULT_TARIH
        defaultProjeShouldBeFound("tarih.equals=" + DEFAULT_TARIH);

        // Get all the projeList where tarih equals to UPDATED_TARIH
        defaultProjeShouldNotBeFound("tarih.equals=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllProjesByTarihIsInShouldWork() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where tarih in DEFAULT_TARIH or UPDATED_TARIH
        defaultProjeShouldBeFound("tarih.in=" + DEFAULT_TARIH + "," + UPDATED_TARIH);

        // Get all the projeList where tarih equals to UPDATED_TARIH
        defaultProjeShouldNotBeFound("tarih.in=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllProjesByTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where tarih is not null
        defaultProjeShouldBeFound("tarih.specified=true");

        // Get all the projeList where tarih is null
        defaultProjeShouldNotBeFound("tarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjesByTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where tarih greater than or equals to DEFAULT_TARIH
        defaultProjeShouldBeFound("tarih.greaterOrEqualThan=" + DEFAULT_TARIH);

        // Get all the projeList where tarih greater than or equals to UPDATED_TARIH
        defaultProjeShouldNotBeFound("tarih.greaterOrEqualThan=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllProjesByTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where tarih less than or equals to DEFAULT_TARIH
        defaultProjeShouldNotBeFound("tarih.lessThan=" + DEFAULT_TARIH);

        // Get all the projeList where tarih less than or equals to UPDATED_TARIH
        defaultProjeShouldBeFound("tarih.lessThan=" + UPDATED_TARIH);
    }


    @Test
    @Transactional
    public void getAllProjesByBaslamaTarihiIsEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where baslamaTarihi equals to DEFAULT_BASLAMA_TARIHI
        defaultProjeShouldBeFound("baslamaTarihi.equals=" + DEFAULT_BASLAMA_TARIHI);

        // Get all the projeList where baslamaTarihi equals to UPDATED_BASLAMA_TARIHI
        defaultProjeShouldNotBeFound("baslamaTarihi.equals=" + UPDATED_BASLAMA_TARIHI);
    }

    @Test
    @Transactional
    public void getAllProjesByBaslamaTarihiIsInShouldWork() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where baslamaTarihi in DEFAULT_BASLAMA_TARIHI or UPDATED_BASLAMA_TARIHI
        defaultProjeShouldBeFound("baslamaTarihi.in=" + DEFAULT_BASLAMA_TARIHI + "," + UPDATED_BASLAMA_TARIHI);

        // Get all the projeList where baslamaTarihi equals to UPDATED_BASLAMA_TARIHI
        defaultProjeShouldNotBeFound("baslamaTarihi.in=" + UPDATED_BASLAMA_TARIHI);
    }

    @Test
    @Transactional
    public void getAllProjesByBaslamaTarihiIsNullOrNotNull() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where baslamaTarihi is not null
        defaultProjeShouldBeFound("baslamaTarihi.specified=true");

        // Get all the projeList where baslamaTarihi is null
        defaultProjeShouldNotBeFound("baslamaTarihi.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjesByBaslamaTarihiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where baslamaTarihi greater than or equals to DEFAULT_BASLAMA_TARIHI
        defaultProjeShouldBeFound("baslamaTarihi.greaterOrEqualThan=" + DEFAULT_BASLAMA_TARIHI);

        // Get all the projeList where baslamaTarihi greater than or equals to UPDATED_BASLAMA_TARIHI
        defaultProjeShouldNotBeFound("baslamaTarihi.greaterOrEqualThan=" + UPDATED_BASLAMA_TARIHI);
    }

    @Test
    @Transactional
    public void getAllProjesByBaslamaTarihiIsLessThanSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where baslamaTarihi less than or equals to DEFAULT_BASLAMA_TARIHI
        defaultProjeShouldNotBeFound("baslamaTarihi.lessThan=" + DEFAULT_BASLAMA_TARIHI);

        // Get all the projeList where baslamaTarihi less than or equals to UPDATED_BASLAMA_TARIHI
        defaultProjeShouldBeFound("baslamaTarihi.lessThan=" + UPDATED_BASLAMA_TARIHI);
    }


    @Test
    @Transactional
    public void getAllProjesByBitisTarihiIsEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where bitisTarihi equals to DEFAULT_BITIS_TARIHI
        defaultProjeShouldBeFound("bitisTarihi.equals=" + DEFAULT_BITIS_TARIHI);

        // Get all the projeList where bitisTarihi equals to UPDATED_BITIS_TARIHI
        defaultProjeShouldNotBeFound("bitisTarihi.equals=" + UPDATED_BITIS_TARIHI);
    }

    @Test
    @Transactional
    public void getAllProjesByBitisTarihiIsInShouldWork() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where bitisTarihi in DEFAULT_BITIS_TARIHI or UPDATED_BITIS_TARIHI
        defaultProjeShouldBeFound("bitisTarihi.in=" + DEFAULT_BITIS_TARIHI + "," + UPDATED_BITIS_TARIHI);

        // Get all the projeList where bitisTarihi equals to UPDATED_BITIS_TARIHI
        defaultProjeShouldNotBeFound("bitisTarihi.in=" + UPDATED_BITIS_TARIHI);
    }

    @Test
    @Transactional
    public void getAllProjesByBitisTarihiIsNullOrNotNull() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where bitisTarihi is not null
        defaultProjeShouldBeFound("bitisTarihi.specified=true");

        // Get all the projeList where bitisTarihi is null
        defaultProjeShouldNotBeFound("bitisTarihi.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjesByBitisTarihiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where bitisTarihi greater than or equals to DEFAULT_BITIS_TARIHI
        defaultProjeShouldBeFound("bitisTarihi.greaterOrEqualThan=" + DEFAULT_BITIS_TARIHI);

        // Get all the projeList where bitisTarihi greater than or equals to UPDATED_BITIS_TARIHI
        defaultProjeShouldNotBeFound("bitisTarihi.greaterOrEqualThan=" + UPDATED_BITIS_TARIHI);
    }

    @Test
    @Transactional
    public void getAllProjesByBitisTarihiIsLessThanSomething() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList where bitisTarihi less than or equals to DEFAULT_BITIS_TARIHI
        defaultProjeShouldNotBeFound("bitisTarihi.lessThan=" + DEFAULT_BITIS_TARIHI);

        // Get all the projeList where bitisTarihi less than or equals to UPDATED_BITIS_TARIHI
        defaultProjeShouldBeFound("bitisTarihi.lessThan=" + UPDATED_BITIS_TARIHI);
    }


    @Test
    @Transactional
    public void getAllProjesByHakedisIsEqualToSomething() throws Exception {
        // Initialize the database
        Hakedis hakedis = HakedisResourceIntTest.createEntity(em);
        em.persist(hakedis);
        em.flush();
        proje.addHakedis(hakedis);
        projeRepository.saveAndFlush(proje);
        Long hakedisId = hakedis.getId();

        // Get all the projeList where hakedis equals to hakedisId
        defaultProjeShouldBeFound("hakedisId.equals=" + hakedisId);

        // Get all the projeList where hakedis equals to hakedisId + 1
        defaultProjeShouldNotBeFound("hakedisId.equals=" + (hakedisId + 1));
    }


    @Test
    @Transactional
    public void getAllProjesByTuruIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjeTuru turu = ProjeTuruResourceIntTest.createEntity(em);
        em.persist(turu);
        em.flush();
        proje.setTuru(turu);
        projeRepository.saveAndFlush(proje);
        Long turuId = turu.getId();

        // Get all the projeList where turu equals to turuId
        defaultProjeShouldBeFound("turuId.equals=" + turuId);

        // Get all the projeList where turu equals to turuId + 1
        defaultProjeShouldNotBeFound("turuId.equals=" + (turuId + 1));
    }


    @Test
    @Transactional
    public void getAllProjesByMudurlukIsEqualToSomething() throws Exception {
        // Initialize the database
        Mudurluk mudurluk = MudurlukResourceIntTest.createEntity(em);
        em.persist(mudurluk);
        em.flush();
        proje.setMudurluk(mudurluk);
        projeRepository.saveAndFlush(proje);
        Long mudurlukId = mudurluk.getId();

        // Get all the projeList where mudurluk equals to mudurlukId
        defaultProjeShouldBeFound("mudurlukId.equals=" + mudurlukId);

        // Get all the projeList where mudurluk equals to mudurlukId + 1
        defaultProjeShouldNotBeFound("mudurlukId.equals=" + (mudurlukId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProjeShouldBeFound(String filter) throws Exception {
        restProjeMockMvc.perform(get("/api/projes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proje.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)))
            .andExpect(jsonPath("$.[*].protokolNo").value(hasItem(DEFAULT_PROTOKOL_NO.intValue())))
            .andExpect(jsonPath("$.[*].durumu").value(hasItem(DEFAULT_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].detay").value(hasItem(DEFAULT_DETAY.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))))
            .andExpect(jsonPath("$.[*].baslamaTarihi").value(hasItem(DEFAULT_BASLAMA_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].bitisTarihi").value(hasItem(DEFAULT_BITIS_TARIHI.toString())));

        // Check, that the count call also returns 1
        restProjeMockMvc.perform(get("/api/projes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProjeShouldNotBeFound(String filter) throws Exception {
        restProjeMockMvc.perform(get("/api/projes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjeMockMvc.perform(get("/api/projes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProje() throws Exception {
        // Get the proje
        restProjeMockMvc.perform(get("/api/projes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProje() throws Exception {
        // Initialize the database
        projeService.save(proje);

        int databaseSizeBeforeUpdate = projeRepository.findAll().size();

        // Update the proje
        Proje updatedProje = projeRepository.findById(proje.getId()).get();
        // Disconnect from session so that the updates on updatedProje are not directly saved in db
        em.detach(updatedProje);
        updatedProje
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .protokolNo(UPDATED_PROTOKOL_NO)
            .durumu(UPDATED_DURUMU)
            .tarih(UPDATED_TARIH)
            .detay(UPDATED_DETAY)
            .resim(UPDATED_RESIM)
            .resimContentType(UPDATED_RESIM_CONTENT_TYPE)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE)
            .baslamaTarihi(UPDATED_BASLAMA_TARIHI)
            .bitisTarihi(UPDATED_BITIS_TARIHI);

        restProjeMockMvc.perform(put("/api/projes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProje)))
            .andExpect(status().isOk());

        // Validate the Proje in the database
        List<Proje> projeList = projeRepository.findAll();
        assertThat(projeList).hasSize(databaseSizeBeforeUpdate);
        Proje testProje = projeList.get(projeList.size() - 1);
        assertThat(testProje.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testProje.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testProje.getProtokolNo()).isEqualTo(UPDATED_PROTOKOL_NO);
        assertThat(testProje.getDurumu()).isEqualTo(UPDATED_DURUMU);
        assertThat(testProje.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testProje.getDetay()).isEqualTo(UPDATED_DETAY);
        assertThat(testProje.getResim()).isEqualTo(UPDATED_RESIM);
        assertThat(testProje.getResimContentType()).isEqualTo(UPDATED_RESIM_CONTENT_TYPE);
        assertThat(testProje.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testProje.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
        assertThat(testProje.getBaslamaTarihi()).isEqualTo(UPDATED_BASLAMA_TARIHI);
        assertThat(testProje.getBitisTarihi()).isEqualTo(UPDATED_BITIS_TARIHI);
    }

    @Test
    @Transactional
    public void updateNonExistingProje() throws Exception {
        int databaseSizeBeforeUpdate = projeRepository.findAll().size();

        // Create the Proje

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjeMockMvc.perform(put("/api/projes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proje)))
            .andExpect(status().isBadRequest());

        // Validate the Proje in the database
        List<Proje> projeList = projeRepository.findAll();
        assertThat(projeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProje() throws Exception {
        // Initialize the database
        projeService.save(proje);

        int databaseSizeBeforeDelete = projeRepository.findAll().size();

        // Delete the proje
        restProjeMockMvc.perform(delete("/api/projes/{id}", proje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Proje> projeList = projeRepository.findAll();
        assertThat(projeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proje.class);
        Proje proje1 = new Proje();
        proje1.setId(1L);
        Proje proje2 = new Proje();
        proje2.setId(proje1.getId());
        assertThat(proje1).isEqualTo(proje2);
        proje2.setId(2L);
        assertThat(proje1).isNotEqualTo(proje2);
        proje1.setId(null);
        assertThat(proje1).isNotEqualTo(proje2);
    }
}
