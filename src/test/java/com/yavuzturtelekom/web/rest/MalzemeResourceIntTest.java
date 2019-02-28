package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.domain.StokTakip;
import com.yavuzturtelekom.domain.Birim;
import com.yavuzturtelekom.domain.Depo;
import com.yavuzturtelekom.domain.MalzemeGrubu;
import com.yavuzturtelekom.repository.MalzemeRepository;
import com.yavuzturtelekom.service.MalzemeService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.MalzemeCriteria;
import com.yavuzturtelekom.service.MalzemeQueryService;

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
    private MalzemeQueryService malzemeQueryService;

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
        final MalzemeResource malzemeResource = new MalzemeResource(malzemeService, malzemeQueryService);
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
    public void getAllMalzemesByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where ad equals to DEFAULT_AD
        defaultMalzemeShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the malzemeList where ad equals to UPDATED_AD
        defaultMalzemeShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllMalzemesByAdIsInShouldWork() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where ad in DEFAULT_AD or UPDATED_AD
        defaultMalzemeShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the malzemeList where ad equals to UPDATED_AD
        defaultMalzemeShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllMalzemesByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where ad is not null
        defaultMalzemeShouldBeFound("ad.specified=true");

        // Get all the malzemeList where ad is null
        defaultMalzemeShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllMalzemesByMalzemeNoIsEqualToSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where malzemeNo equals to DEFAULT_MALZEME_NO
        defaultMalzemeShouldBeFound("malzemeNo.equals=" + DEFAULT_MALZEME_NO);

        // Get all the malzemeList where malzemeNo equals to UPDATED_MALZEME_NO
        defaultMalzemeShouldNotBeFound("malzemeNo.equals=" + UPDATED_MALZEME_NO);
    }

    @Test
    @Transactional
    public void getAllMalzemesByMalzemeNoIsInShouldWork() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where malzemeNo in DEFAULT_MALZEME_NO or UPDATED_MALZEME_NO
        defaultMalzemeShouldBeFound("malzemeNo.in=" + DEFAULT_MALZEME_NO + "," + UPDATED_MALZEME_NO);

        // Get all the malzemeList where malzemeNo equals to UPDATED_MALZEME_NO
        defaultMalzemeShouldNotBeFound("malzemeNo.in=" + UPDATED_MALZEME_NO);
    }

    @Test
    @Transactional
    public void getAllMalzemesByMalzemeNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where malzemeNo is not null
        defaultMalzemeShouldBeFound("malzemeNo.specified=true");

        // Get all the malzemeList where malzemeNo is null
        defaultMalzemeShouldNotBeFound("malzemeNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllMalzemesByMalzemeNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where malzemeNo greater than or equals to DEFAULT_MALZEME_NO
        defaultMalzemeShouldBeFound("malzemeNo.greaterOrEqualThan=" + DEFAULT_MALZEME_NO);

        // Get all the malzemeList where malzemeNo greater than or equals to UPDATED_MALZEME_NO
        defaultMalzemeShouldNotBeFound("malzemeNo.greaterOrEqualThan=" + UPDATED_MALZEME_NO);
    }

    @Test
    @Transactional
    public void getAllMalzemesByMalzemeNoIsLessThanSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where malzemeNo less than or equals to DEFAULT_MALZEME_NO
        defaultMalzemeShouldNotBeFound("malzemeNo.lessThan=" + DEFAULT_MALZEME_NO);

        // Get all the malzemeList where malzemeNo less than or equals to UPDATED_MALZEME_NO
        defaultMalzemeShouldBeFound("malzemeNo.lessThan=" + UPDATED_MALZEME_NO);
    }


    @Test
    @Transactional
    public void getAllMalzemesByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where aciklama equals to DEFAULT_ACIKLAMA
        defaultMalzemeShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the malzemeList where aciklama equals to UPDATED_ACIKLAMA
        defaultMalzemeShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllMalzemesByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultMalzemeShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the malzemeList where aciklama equals to UPDATED_ACIKLAMA
        defaultMalzemeShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllMalzemesByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where aciklama is not null
        defaultMalzemeShouldBeFound("aciklama.specified=true");

        // Get all the malzemeList where aciklama is null
        defaultMalzemeShouldNotBeFound("aciklama.specified=false");
    }

    @Test
    @Transactional
    public void getAllMalzemesByKisaltmaIsEqualToSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where kisaltma equals to DEFAULT_KISALTMA
        defaultMalzemeShouldBeFound("kisaltma.equals=" + DEFAULT_KISALTMA);

        // Get all the malzemeList where kisaltma equals to UPDATED_KISALTMA
        defaultMalzemeShouldNotBeFound("kisaltma.equals=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllMalzemesByKisaltmaIsInShouldWork() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where kisaltma in DEFAULT_KISALTMA or UPDATED_KISALTMA
        defaultMalzemeShouldBeFound("kisaltma.in=" + DEFAULT_KISALTMA + "," + UPDATED_KISALTMA);

        // Get all the malzemeList where kisaltma equals to UPDATED_KISALTMA
        defaultMalzemeShouldNotBeFound("kisaltma.in=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllMalzemesByKisaltmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where kisaltma is not null
        defaultMalzemeShouldBeFound("kisaltma.specified=true");

        // Get all the malzemeList where kisaltma is null
        defaultMalzemeShouldNotBeFound("kisaltma.specified=false");
    }

    @Test
    @Transactional
    public void getAllMalzemesByTenzilatsizFiyatIsEqualToSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where tenzilatsizFiyat equals to DEFAULT_TENZILATSIZ_FIYAT
        defaultMalzemeShouldBeFound("tenzilatsizFiyat.equals=" + DEFAULT_TENZILATSIZ_FIYAT);

        // Get all the malzemeList where tenzilatsizFiyat equals to UPDATED_TENZILATSIZ_FIYAT
        defaultMalzemeShouldNotBeFound("tenzilatsizFiyat.equals=" + UPDATED_TENZILATSIZ_FIYAT);
    }

    @Test
    @Transactional
    public void getAllMalzemesByTenzilatsizFiyatIsInShouldWork() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where tenzilatsizFiyat in DEFAULT_TENZILATSIZ_FIYAT or UPDATED_TENZILATSIZ_FIYAT
        defaultMalzemeShouldBeFound("tenzilatsizFiyat.in=" + DEFAULT_TENZILATSIZ_FIYAT + "," + UPDATED_TENZILATSIZ_FIYAT);

        // Get all the malzemeList where tenzilatsizFiyat equals to UPDATED_TENZILATSIZ_FIYAT
        defaultMalzemeShouldNotBeFound("tenzilatsizFiyat.in=" + UPDATED_TENZILATSIZ_FIYAT);
    }

    @Test
    @Transactional
    public void getAllMalzemesByTenzilatsizFiyatIsNullOrNotNull() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where tenzilatsizFiyat is not null
        defaultMalzemeShouldBeFound("tenzilatsizFiyat.specified=true");

        // Get all the malzemeList where tenzilatsizFiyat is null
        defaultMalzemeShouldNotBeFound("tenzilatsizFiyat.specified=false");
    }

    @Test
    @Transactional
    public void getAllMalzemesByTenzilatliFiyatIsEqualToSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where tenzilatliFiyat equals to DEFAULT_TENZILATLI_FIYAT
        defaultMalzemeShouldBeFound("tenzilatliFiyat.equals=" + DEFAULT_TENZILATLI_FIYAT);

        // Get all the malzemeList where tenzilatliFiyat equals to UPDATED_TENZILATLI_FIYAT
        defaultMalzemeShouldNotBeFound("tenzilatliFiyat.equals=" + UPDATED_TENZILATLI_FIYAT);
    }

    @Test
    @Transactional
    public void getAllMalzemesByTenzilatliFiyatIsInShouldWork() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where tenzilatliFiyat in DEFAULT_TENZILATLI_FIYAT or UPDATED_TENZILATLI_FIYAT
        defaultMalzemeShouldBeFound("tenzilatliFiyat.in=" + DEFAULT_TENZILATLI_FIYAT + "," + UPDATED_TENZILATLI_FIYAT);

        // Get all the malzemeList where tenzilatliFiyat equals to UPDATED_TENZILATLI_FIYAT
        defaultMalzemeShouldNotBeFound("tenzilatliFiyat.in=" + UPDATED_TENZILATLI_FIYAT);
    }

    @Test
    @Transactional
    public void getAllMalzemesByTenzilatliFiyatIsNullOrNotNull() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where tenzilatliFiyat is not null
        defaultMalzemeShouldBeFound("tenzilatliFiyat.specified=true");

        // Get all the malzemeList where tenzilatliFiyat is null
        defaultMalzemeShouldNotBeFound("tenzilatliFiyat.specified=false");
    }

    @Test
    @Transactional
    public void getAllMalzemesByTaseronFiyatIsEqualToSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where taseronFiyat equals to DEFAULT_TASERON_FIYAT
        defaultMalzemeShouldBeFound("taseronFiyat.equals=" + DEFAULT_TASERON_FIYAT);

        // Get all the malzemeList where taseronFiyat equals to UPDATED_TASERON_FIYAT
        defaultMalzemeShouldNotBeFound("taseronFiyat.equals=" + UPDATED_TASERON_FIYAT);
    }

    @Test
    @Transactional
    public void getAllMalzemesByTaseronFiyatIsInShouldWork() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where taseronFiyat in DEFAULT_TASERON_FIYAT or UPDATED_TASERON_FIYAT
        defaultMalzemeShouldBeFound("taseronFiyat.in=" + DEFAULT_TASERON_FIYAT + "," + UPDATED_TASERON_FIYAT);

        // Get all the malzemeList where taseronFiyat equals to UPDATED_TASERON_FIYAT
        defaultMalzemeShouldNotBeFound("taseronFiyat.in=" + UPDATED_TASERON_FIYAT);
    }

    @Test
    @Transactional
    public void getAllMalzemesByTaseronFiyatIsNullOrNotNull() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where taseronFiyat is not null
        defaultMalzemeShouldBeFound("taseronFiyat.specified=true");

        // Get all the malzemeList where taseronFiyat is null
        defaultMalzemeShouldNotBeFound("taseronFiyat.specified=false");
    }

    @Test
    @Transactional
    public void getAllMalzemesByParaBirimiIsEqualToSomething() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where paraBirimi equals to DEFAULT_PARA_BIRIMI
        defaultMalzemeShouldBeFound("paraBirimi.equals=" + DEFAULT_PARA_BIRIMI);

        // Get all the malzemeList where paraBirimi equals to UPDATED_PARA_BIRIMI
        defaultMalzemeShouldNotBeFound("paraBirimi.equals=" + UPDATED_PARA_BIRIMI);
    }

    @Test
    @Transactional
    public void getAllMalzemesByParaBirimiIsInShouldWork() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where paraBirimi in DEFAULT_PARA_BIRIMI or UPDATED_PARA_BIRIMI
        defaultMalzemeShouldBeFound("paraBirimi.in=" + DEFAULT_PARA_BIRIMI + "," + UPDATED_PARA_BIRIMI);

        // Get all the malzemeList where paraBirimi equals to UPDATED_PARA_BIRIMI
        defaultMalzemeShouldNotBeFound("paraBirimi.in=" + UPDATED_PARA_BIRIMI);
    }

    @Test
    @Transactional
    public void getAllMalzemesByParaBirimiIsNullOrNotNull() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList where paraBirimi is not null
        defaultMalzemeShouldBeFound("paraBirimi.specified=true");

        // Get all the malzemeList where paraBirimi is null
        defaultMalzemeShouldNotBeFound("paraBirimi.specified=false");
    }

    @Test
    @Transactional
    public void getAllMalzemesByStokTakipIsEqualToSomething() throws Exception {
        // Initialize the database
        StokTakip stokTakip = StokTakipResourceIntTest.createEntity(em);
        em.persist(stokTakip);
        em.flush();
        malzeme.addStokTakip(stokTakip);
        malzemeRepository.saveAndFlush(malzeme);
        Long stokTakipId = stokTakip.getId();

        // Get all the malzemeList where stokTakip equals to stokTakipId
        defaultMalzemeShouldBeFound("stokTakipId.equals=" + stokTakipId);

        // Get all the malzemeList where stokTakip equals to stokTakipId + 1
        defaultMalzemeShouldNotBeFound("stokTakipId.equals=" + (stokTakipId + 1));
    }


    @Test
    @Transactional
    public void getAllMalzemesByBirimIsEqualToSomething() throws Exception {
        // Initialize the database
        Birim birim = BirimResourceIntTest.createEntity(em);
        em.persist(birim);
        em.flush();
        malzeme.setBirim(birim);
        malzemeRepository.saveAndFlush(malzeme);
        Long birimId = birim.getId();

        // Get all the malzemeList where birim equals to birimId
        defaultMalzemeShouldBeFound("birimId.equals=" + birimId);

        // Get all the malzemeList where birim equals to birimId + 1
        defaultMalzemeShouldNotBeFound("birimId.equals=" + (birimId + 1));
    }


    @Test
    @Transactional
    public void getAllMalzemesByDepoIsEqualToSomething() throws Exception {
        // Initialize the database
        Depo depo = DepoResourceIntTest.createEntity(em);
        em.persist(depo);
        em.flush();
        malzeme.setDepo(depo);
        malzemeRepository.saveAndFlush(malzeme);
        Long depoId = depo.getId();

        // Get all the malzemeList where depo equals to depoId
        defaultMalzemeShouldBeFound("depoId.equals=" + depoId);

        // Get all the malzemeList where depo equals to depoId + 1
        defaultMalzemeShouldNotBeFound("depoId.equals=" + (depoId + 1));
    }


    @Test
    @Transactional
    public void getAllMalzemesByGrupIsEqualToSomething() throws Exception {
        // Initialize the database
        MalzemeGrubu grup = MalzemeGrubuResourceIntTest.createEntity(em);
        em.persist(grup);
        em.flush();
        malzeme.addGrup(grup);
        malzemeRepository.saveAndFlush(malzeme);
        Long grupId = grup.getId();

        // Get all the malzemeList where grup equals to grupId
        defaultMalzemeShouldBeFound("grupId.equals=" + grupId);

        // Get all the malzemeList where grup equals to grupId + 1
        defaultMalzemeShouldNotBeFound("grupId.equals=" + (grupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMalzemeShouldBeFound(String filter) throws Exception {
        restMalzemeMockMvc.perform(get("/api/malzemes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(malzeme.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].malzemeNo").value(hasItem(DEFAULT_MALZEME_NO.intValue())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA)))
            .andExpect(jsonPath("$.[*].tenzilatsizFiyat").value(hasItem(DEFAULT_TENZILATSIZ_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].tenzilatliFiyat").value(hasItem(DEFAULT_TENZILATLI_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].taseronFiyat").value(hasItem(DEFAULT_TASERON_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].paraBirimi").value(hasItem(DEFAULT_PARA_BIRIMI.toString())));

        // Check, that the count call also returns 1
        restMalzemeMockMvc.perform(get("/api/malzemes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMalzemeShouldNotBeFound(String filter) throws Exception {
        restMalzemeMockMvc.perform(get("/api/malzemes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMalzemeMockMvc.perform(get("/api/malzemes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
