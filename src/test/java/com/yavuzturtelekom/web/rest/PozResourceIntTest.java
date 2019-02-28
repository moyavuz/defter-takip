package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Poz;
import com.yavuzturtelekom.domain.HakedisDetay;
import com.yavuzturtelekom.domain.Birim;
import com.yavuzturtelekom.domain.PozGrubu;
import com.yavuzturtelekom.repository.PozRepository;
import com.yavuzturtelekom.service.PozService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.PozCriteria;
import com.yavuzturtelekom.service.PozQueryService;

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

/**
 * Test class for the PozResource REST controller.
 *
 * @see PozResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class PozResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_KISALTMA = "AAAAAAAAAA";
    private static final String UPDATED_KISALTMA = "BBBBBBBBBB";

    private static final Integer DEFAULT_YIL = 1;
    private static final Integer UPDATED_YIL = 2;

    private static final Double DEFAULT_TENZILATSIZ_FIYAT = 1D;
    private static final Double UPDATED_TENZILATSIZ_FIYAT = 2D;

    private static final Double DEFAULT_TENZILATLI_FIYAT = 1D;
    private static final Double UPDATED_TENZILATLI_FIYAT = 2D;

    private static final Double DEFAULT_TASERON_FIYAT = 1D;
    private static final Double UPDATED_TASERON_FIYAT = 2D;

    private static final Double DEFAULT_TASERE_FIYAT = 1D;
    private static final Double UPDATED_TASERE_FIYAT = 2D;

    private static final Boolean DEFAULT_MALZEME_MI = false;
    private static final Boolean UPDATED_MALZEME_MI = true;

    @Autowired
    private PozRepository pozRepository;

    @Autowired
    private PozService pozService;

    @Autowired
    private PozQueryService pozQueryService;

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

    private MockMvc restPozMockMvc;

    private Poz poz;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PozResource pozResource = new PozResource(pozService, pozQueryService);
        this.restPozMockMvc = MockMvcBuilders.standaloneSetup(pozResource)
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
    public static Poz createEntity(EntityManager em) {
        Poz poz = new Poz()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .kisaltma(DEFAULT_KISALTMA)
            .yil(DEFAULT_YIL)
            .tenzilatsizFiyat(DEFAULT_TENZILATSIZ_FIYAT)
            .tenzilatliFiyat(DEFAULT_TENZILATLI_FIYAT)
            .taseronFiyat(DEFAULT_TASERON_FIYAT)
            .tasereFiyat(DEFAULT_TASERE_FIYAT)
            .malzemeMi(DEFAULT_MALZEME_MI);
        // Add required entity
        Birim birim = BirimResourceIntTest.createEntity(em);
        em.persist(birim);
        em.flush();
        poz.setBirim(birim);
        return poz;
    }

    @Before
    public void initTest() {
        poz = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoz() throws Exception {
        int databaseSizeBeforeCreate = pozRepository.findAll().size();

        // Create the Poz
        restPozMockMvc.perform(post("/api/pozs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poz)))
            .andExpect(status().isCreated());

        // Validate the Poz in the database
        List<Poz> pozList = pozRepository.findAll();
        assertThat(pozList).hasSize(databaseSizeBeforeCreate + 1);
        Poz testPoz = pozList.get(pozList.size() - 1);
        assertThat(testPoz.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testPoz.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testPoz.getKisaltma()).isEqualTo(DEFAULT_KISALTMA);
        assertThat(testPoz.getYil()).isEqualTo(DEFAULT_YIL);
        assertThat(testPoz.getTenzilatsizFiyat()).isEqualTo(DEFAULT_TENZILATSIZ_FIYAT);
        assertThat(testPoz.getTenzilatliFiyat()).isEqualTo(DEFAULT_TENZILATLI_FIYAT);
        assertThat(testPoz.getTaseronFiyat()).isEqualTo(DEFAULT_TASERON_FIYAT);
        assertThat(testPoz.getTasereFiyat()).isEqualTo(DEFAULT_TASERE_FIYAT);
        assertThat(testPoz.isMalzemeMi()).isEqualTo(DEFAULT_MALZEME_MI);
    }

    @Test
    @Transactional
    public void createPozWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pozRepository.findAll().size();

        // Create the Poz with an existing ID
        poz.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPozMockMvc.perform(post("/api/pozs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poz)))
            .andExpect(status().isBadRequest());

        // Validate the Poz in the database
        List<Poz> pozList = pozRepository.findAll();
        assertThat(pozList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pozRepository.findAll().size();
        // set the field null
        poz.setAd(null);

        // Create the Poz, which fails.

        restPozMockMvc.perform(post("/api/pozs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poz)))
            .andExpect(status().isBadRequest());

        List<Poz> pozList = pozRepository.findAll();
        assertThat(pozList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPozs() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList
        restPozMockMvc.perform(get("/api/pozs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poz.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA.toString())))
            .andExpect(jsonPath("$.[*].yil").value(hasItem(DEFAULT_YIL)))
            .andExpect(jsonPath("$.[*].tenzilatsizFiyat").value(hasItem(DEFAULT_TENZILATSIZ_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].tenzilatliFiyat").value(hasItem(DEFAULT_TENZILATLI_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].taseronFiyat").value(hasItem(DEFAULT_TASERON_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].tasereFiyat").value(hasItem(DEFAULT_TASERE_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].malzemeMi").value(hasItem(DEFAULT_MALZEME_MI.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPoz() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get the poz
        restPozMockMvc.perform(get("/api/pozs/{id}", poz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poz.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.kisaltma").value(DEFAULT_KISALTMA.toString()))
            .andExpect(jsonPath("$.yil").value(DEFAULT_YIL))
            .andExpect(jsonPath("$.tenzilatsizFiyat").value(DEFAULT_TENZILATSIZ_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.tenzilatliFiyat").value(DEFAULT_TENZILATLI_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.taseronFiyat").value(DEFAULT_TASERON_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.tasereFiyat").value(DEFAULT_TASERE_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.malzemeMi").value(DEFAULT_MALZEME_MI.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllPozsByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where ad equals to DEFAULT_AD
        defaultPozShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the pozList where ad equals to UPDATED_AD
        defaultPozShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllPozsByAdIsInShouldWork() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where ad in DEFAULT_AD or UPDATED_AD
        defaultPozShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the pozList where ad equals to UPDATED_AD
        defaultPozShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllPozsByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where ad is not null
        defaultPozShouldBeFound("ad.specified=true");

        // Get all the pozList where ad is null
        defaultPozShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozsByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where aciklama equals to DEFAULT_ACIKLAMA
        defaultPozShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the pozList where aciklama equals to UPDATED_ACIKLAMA
        defaultPozShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllPozsByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultPozShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the pozList where aciklama equals to UPDATED_ACIKLAMA
        defaultPozShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllPozsByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where aciklama is not null
        defaultPozShouldBeFound("aciklama.specified=true");

        // Get all the pozList where aciklama is null
        defaultPozShouldNotBeFound("aciklama.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozsByKisaltmaIsEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where kisaltma equals to DEFAULT_KISALTMA
        defaultPozShouldBeFound("kisaltma.equals=" + DEFAULT_KISALTMA);

        // Get all the pozList where kisaltma equals to UPDATED_KISALTMA
        defaultPozShouldNotBeFound("kisaltma.equals=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllPozsByKisaltmaIsInShouldWork() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where kisaltma in DEFAULT_KISALTMA or UPDATED_KISALTMA
        defaultPozShouldBeFound("kisaltma.in=" + DEFAULT_KISALTMA + "," + UPDATED_KISALTMA);

        // Get all the pozList where kisaltma equals to UPDATED_KISALTMA
        defaultPozShouldNotBeFound("kisaltma.in=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllPozsByKisaltmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where kisaltma is not null
        defaultPozShouldBeFound("kisaltma.specified=true");

        // Get all the pozList where kisaltma is null
        defaultPozShouldNotBeFound("kisaltma.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozsByYilIsEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where yil equals to DEFAULT_YIL
        defaultPozShouldBeFound("yil.equals=" + DEFAULT_YIL);

        // Get all the pozList where yil equals to UPDATED_YIL
        defaultPozShouldNotBeFound("yil.equals=" + UPDATED_YIL);
    }

    @Test
    @Transactional
    public void getAllPozsByYilIsInShouldWork() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where yil in DEFAULT_YIL or UPDATED_YIL
        defaultPozShouldBeFound("yil.in=" + DEFAULT_YIL + "," + UPDATED_YIL);

        // Get all the pozList where yil equals to UPDATED_YIL
        defaultPozShouldNotBeFound("yil.in=" + UPDATED_YIL);
    }

    @Test
    @Transactional
    public void getAllPozsByYilIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where yil is not null
        defaultPozShouldBeFound("yil.specified=true");

        // Get all the pozList where yil is null
        defaultPozShouldNotBeFound("yil.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozsByYilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where yil greater than or equals to DEFAULT_YIL
        defaultPozShouldBeFound("yil.greaterOrEqualThan=" + DEFAULT_YIL);

        // Get all the pozList where yil greater than or equals to UPDATED_YIL
        defaultPozShouldNotBeFound("yil.greaterOrEqualThan=" + UPDATED_YIL);
    }

    @Test
    @Transactional
    public void getAllPozsByYilIsLessThanSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where yil less than or equals to DEFAULT_YIL
        defaultPozShouldNotBeFound("yil.lessThan=" + DEFAULT_YIL);

        // Get all the pozList where yil less than or equals to UPDATED_YIL
        defaultPozShouldBeFound("yil.lessThan=" + UPDATED_YIL);
    }


    @Test
    @Transactional
    public void getAllPozsByTenzilatsizFiyatIsEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where tenzilatsizFiyat equals to DEFAULT_TENZILATSIZ_FIYAT
        defaultPozShouldBeFound("tenzilatsizFiyat.equals=" + DEFAULT_TENZILATSIZ_FIYAT);

        // Get all the pozList where tenzilatsizFiyat equals to UPDATED_TENZILATSIZ_FIYAT
        defaultPozShouldNotBeFound("tenzilatsizFiyat.equals=" + UPDATED_TENZILATSIZ_FIYAT);
    }

    @Test
    @Transactional
    public void getAllPozsByTenzilatsizFiyatIsInShouldWork() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where tenzilatsizFiyat in DEFAULT_TENZILATSIZ_FIYAT or UPDATED_TENZILATSIZ_FIYAT
        defaultPozShouldBeFound("tenzilatsizFiyat.in=" + DEFAULT_TENZILATSIZ_FIYAT + "," + UPDATED_TENZILATSIZ_FIYAT);

        // Get all the pozList where tenzilatsizFiyat equals to UPDATED_TENZILATSIZ_FIYAT
        defaultPozShouldNotBeFound("tenzilatsizFiyat.in=" + UPDATED_TENZILATSIZ_FIYAT);
    }

    @Test
    @Transactional
    public void getAllPozsByTenzilatsizFiyatIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where tenzilatsizFiyat is not null
        defaultPozShouldBeFound("tenzilatsizFiyat.specified=true");

        // Get all the pozList where tenzilatsizFiyat is null
        defaultPozShouldNotBeFound("tenzilatsizFiyat.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozsByTenzilatliFiyatIsEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where tenzilatliFiyat equals to DEFAULT_TENZILATLI_FIYAT
        defaultPozShouldBeFound("tenzilatliFiyat.equals=" + DEFAULT_TENZILATLI_FIYAT);

        // Get all the pozList where tenzilatliFiyat equals to UPDATED_TENZILATLI_FIYAT
        defaultPozShouldNotBeFound("tenzilatliFiyat.equals=" + UPDATED_TENZILATLI_FIYAT);
    }

    @Test
    @Transactional
    public void getAllPozsByTenzilatliFiyatIsInShouldWork() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where tenzilatliFiyat in DEFAULT_TENZILATLI_FIYAT or UPDATED_TENZILATLI_FIYAT
        defaultPozShouldBeFound("tenzilatliFiyat.in=" + DEFAULT_TENZILATLI_FIYAT + "," + UPDATED_TENZILATLI_FIYAT);

        // Get all the pozList where tenzilatliFiyat equals to UPDATED_TENZILATLI_FIYAT
        defaultPozShouldNotBeFound("tenzilatliFiyat.in=" + UPDATED_TENZILATLI_FIYAT);
    }

    @Test
    @Transactional
    public void getAllPozsByTenzilatliFiyatIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where tenzilatliFiyat is not null
        defaultPozShouldBeFound("tenzilatliFiyat.specified=true");

        // Get all the pozList where tenzilatliFiyat is null
        defaultPozShouldNotBeFound("tenzilatliFiyat.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozsByTaseronFiyatIsEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where taseronFiyat equals to DEFAULT_TASERON_FIYAT
        defaultPozShouldBeFound("taseronFiyat.equals=" + DEFAULT_TASERON_FIYAT);

        // Get all the pozList where taseronFiyat equals to UPDATED_TASERON_FIYAT
        defaultPozShouldNotBeFound("taseronFiyat.equals=" + UPDATED_TASERON_FIYAT);
    }

    @Test
    @Transactional
    public void getAllPozsByTaseronFiyatIsInShouldWork() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where taseronFiyat in DEFAULT_TASERON_FIYAT or UPDATED_TASERON_FIYAT
        defaultPozShouldBeFound("taseronFiyat.in=" + DEFAULT_TASERON_FIYAT + "," + UPDATED_TASERON_FIYAT);

        // Get all the pozList where taseronFiyat equals to UPDATED_TASERON_FIYAT
        defaultPozShouldNotBeFound("taseronFiyat.in=" + UPDATED_TASERON_FIYAT);
    }

    @Test
    @Transactional
    public void getAllPozsByTaseronFiyatIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where taseronFiyat is not null
        defaultPozShouldBeFound("taseronFiyat.specified=true");

        // Get all the pozList where taseronFiyat is null
        defaultPozShouldNotBeFound("taseronFiyat.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozsByTasereFiyatIsEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where tasereFiyat equals to DEFAULT_TASERE_FIYAT
        defaultPozShouldBeFound("tasereFiyat.equals=" + DEFAULT_TASERE_FIYAT);

        // Get all the pozList where tasereFiyat equals to UPDATED_TASERE_FIYAT
        defaultPozShouldNotBeFound("tasereFiyat.equals=" + UPDATED_TASERE_FIYAT);
    }

    @Test
    @Transactional
    public void getAllPozsByTasereFiyatIsInShouldWork() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where tasereFiyat in DEFAULT_TASERE_FIYAT or UPDATED_TASERE_FIYAT
        defaultPozShouldBeFound("tasereFiyat.in=" + DEFAULT_TASERE_FIYAT + "," + UPDATED_TASERE_FIYAT);

        // Get all the pozList where tasereFiyat equals to UPDATED_TASERE_FIYAT
        defaultPozShouldNotBeFound("tasereFiyat.in=" + UPDATED_TASERE_FIYAT);
    }

    @Test
    @Transactional
    public void getAllPozsByTasereFiyatIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where tasereFiyat is not null
        defaultPozShouldBeFound("tasereFiyat.specified=true");

        // Get all the pozList where tasereFiyat is null
        defaultPozShouldNotBeFound("tasereFiyat.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozsByMalzemeMiIsEqualToSomething() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where malzemeMi equals to DEFAULT_MALZEME_MI
        defaultPozShouldBeFound("malzemeMi.equals=" + DEFAULT_MALZEME_MI);

        // Get all the pozList where malzemeMi equals to UPDATED_MALZEME_MI
        defaultPozShouldNotBeFound("malzemeMi.equals=" + UPDATED_MALZEME_MI);
    }

    @Test
    @Transactional
    public void getAllPozsByMalzemeMiIsInShouldWork() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where malzemeMi in DEFAULT_MALZEME_MI or UPDATED_MALZEME_MI
        defaultPozShouldBeFound("malzemeMi.in=" + DEFAULT_MALZEME_MI + "," + UPDATED_MALZEME_MI);

        // Get all the pozList where malzemeMi equals to UPDATED_MALZEME_MI
        defaultPozShouldNotBeFound("malzemeMi.in=" + UPDATED_MALZEME_MI);
    }

    @Test
    @Transactional
    public void getAllPozsByMalzemeMiIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozRepository.saveAndFlush(poz);

        // Get all the pozList where malzemeMi is not null
        defaultPozShouldBeFound("malzemeMi.specified=true");

        // Get all the pozList where malzemeMi is null
        defaultPozShouldNotBeFound("malzemeMi.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozsByHakedisDetayIsEqualToSomething() throws Exception {
        // Initialize the database
        HakedisDetay hakedisDetay = HakedisDetayResourceIntTest.createEntity(em);
        em.persist(hakedisDetay);
        em.flush();
        poz.addHakedisDetay(hakedisDetay);
        pozRepository.saveAndFlush(poz);
        Long hakedisDetayId = hakedisDetay.getId();

        // Get all the pozList where hakedisDetay equals to hakedisDetayId
        defaultPozShouldBeFound("hakedisDetayId.equals=" + hakedisDetayId);

        // Get all the pozList where hakedisDetay equals to hakedisDetayId + 1
        defaultPozShouldNotBeFound("hakedisDetayId.equals=" + (hakedisDetayId + 1));
    }


    @Test
    @Transactional
    public void getAllPozsByBirimIsEqualToSomething() throws Exception {
        // Initialize the database
        Birim birim = BirimResourceIntTest.createEntity(em);
        em.persist(birim);
        em.flush();
        poz.setBirim(birim);
        pozRepository.saveAndFlush(poz);
        Long birimId = birim.getId();

        // Get all the pozList where birim equals to birimId
        defaultPozShouldBeFound("birimId.equals=" + birimId);

        // Get all the pozList where birim equals to birimId + 1
        defaultPozShouldNotBeFound("birimId.equals=" + (birimId + 1));
    }


    @Test
    @Transactional
    public void getAllPozsByGrupIsEqualToSomething() throws Exception {
        // Initialize the database
        PozGrubu grup = PozGrubuResourceIntTest.createEntity(em);
        em.persist(grup);
        em.flush();
        poz.addGrup(grup);
        pozRepository.saveAndFlush(poz);
        Long grupId = grup.getId();

        // Get all the pozList where grup equals to grupId
        defaultPozShouldBeFound("grupId.equals=" + grupId);

        // Get all the pozList where grup equals to grupId + 1
        defaultPozShouldNotBeFound("grupId.equals=" + (grupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPozShouldBeFound(String filter) throws Exception {
        restPozMockMvc.perform(get("/api/pozs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poz.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA)))
            .andExpect(jsonPath("$.[*].yil").value(hasItem(DEFAULT_YIL)))
            .andExpect(jsonPath("$.[*].tenzilatsizFiyat").value(hasItem(DEFAULT_TENZILATSIZ_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].tenzilatliFiyat").value(hasItem(DEFAULT_TENZILATLI_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].taseronFiyat").value(hasItem(DEFAULT_TASERON_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].tasereFiyat").value(hasItem(DEFAULT_TASERE_FIYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].malzemeMi").value(hasItem(DEFAULT_MALZEME_MI.booleanValue())));

        // Check, that the count call also returns 1
        restPozMockMvc.perform(get("/api/pozs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPozShouldNotBeFound(String filter) throws Exception {
        restPozMockMvc.perform(get("/api/pozs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPozMockMvc.perform(get("/api/pozs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPoz() throws Exception {
        // Get the poz
        restPozMockMvc.perform(get("/api/pozs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoz() throws Exception {
        // Initialize the database
        pozService.save(poz);

        int databaseSizeBeforeUpdate = pozRepository.findAll().size();

        // Update the poz
        Poz updatedPoz = pozRepository.findById(poz.getId()).get();
        // Disconnect from session so that the updates on updatedPoz are not directly saved in db
        em.detach(updatedPoz);
        updatedPoz
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .kisaltma(UPDATED_KISALTMA)
            .yil(UPDATED_YIL)
            .tenzilatsizFiyat(UPDATED_TENZILATSIZ_FIYAT)
            .tenzilatliFiyat(UPDATED_TENZILATLI_FIYAT)
            .taseronFiyat(UPDATED_TASERON_FIYAT)
            .tasereFiyat(UPDATED_TASERE_FIYAT)
            .malzemeMi(UPDATED_MALZEME_MI);

        restPozMockMvc.perform(put("/api/pozs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoz)))
            .andExpect(status().isOk());

        // Validate the Poz in the database
        List<Poz> pozList = pozRepository.findAll();
        assertThat(pozList).hasSize(databaseSizeBeforeUpdate);
        Poz testPoz = pozList.get(pozList.size() - 1);
        assertThat(testPoz.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testPoz.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testPoz.getKisaltma()).isEqualTo(UPDATED_KISALTMA);
        assertThat(testPoz.getYil()).isEqualTo(UPDATED_YIL);
        assertThat(testPoz.getTenzilatsizFiyat()).isEqualTo(UPDATED_TENZILATSIZ_FIYAT);
        assertThat(testPoz.getTenzilatliFiyat()).isEqualTo(UPDATED_TENZILATLI_FIYAT);
        assertThat(testPoz.getTaseronFiyat()).isEqualTo(UPDATED_TASERON_FIYAT);
        assertThat(testPoz.getTasereFiyat()).isEqualTo(UPDATED_TASERE_FIYAT);
        assertThat(testPoz.isMalzemeMi()).isEqualTo(UPDATED_MALZEME_MI);
    }

    @Test
    @Transactional
    public void updateNonExistingPoz() throws Exception {
        int databaseSizeBeforeUpdate = pozRepository.findAll().size();

        // Create the Poz

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPozMockMvc.perform(put("/api/pozs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poz)))
            .andExpect(status().isBadRequest());

        // Validate the Poz in the database
        List<Poz> pozList = pozRepository.findAll();
        assertThat(pozList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoz() throws Exception {
        // Initialize the database
        pozService.save(poz);

        int databaseSizeBeforeDelete = pozRepository.findAll().size();

        // Delete the poz
        restPozMockMvc.perform(delete("/api/pozs/{id}", poz.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Poz> pozList = pozRepository.findAll();
        assertThat(pozList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poz.class);
        Poz poz1 = new Poz();
        poz1.setId(1L);
        Poz poz2 = new Poz();
        poz2.setId(poz1.getId());
        assertThat(poz1).isEqualTo(poz2);
        poz2.setId(2L);
        assertThat(poz1).isNotEqualTo(poz2);
        poz1.setId(null);
        assertThat(poz1).isNotEqualTo(poz2);
    }
}
