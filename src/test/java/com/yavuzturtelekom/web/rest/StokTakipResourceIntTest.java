package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.StokTakip;
import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.domain.Depo;
import com.yavuzturtelekom.repository.StokTakipRepository;
import com.yavuzturtelekom.service.StokTakipService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.StokTakipCriteria;
import com.yavuzturtelekom.service.StokTakipQueryService;

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

import com.yavuzturtelekom.domain.enumeration.StokHareketTuru;
/**
 * Test class for the StokTakipResource REST controller.
 *
 * @see StokTakipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class StokTakipResourceIntTest {

    private static final Long DEFAULT_MIKTAR = 1L;
    private static final Long UPDATED_MIKTAR = 2L;

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final StokHareketTuru DEFAULT_HAREKET_TURU = StokHareketTuru.GIRIS;
    private static final StokHareketTuru UPDATED_HAREKET_TURU = StokHareketTuru.CIKIS;

    @Autowired
    private StokTakipRepository stokTakipRepository;

    @Autowired
    private StokTakipService stokTakipService;

    @Autowired
    private StokTakipQueryService stokTakipQueryService;

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

    private MockMvc restStokTakipMockMvc;

    private StokTakip stokTakip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StokTakipResource stokTakipResource = new StokTakipResource(stokTakipService, stokTakipQueryService);
        this.restStokTakipMockMvc = MockMvcBuilders.standaloneSetup(stokTakipResource)
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
    public static StokTakip createEntity(EntityManager em) {
        StokTakip stokTakip = new StokTakip()
            .miktar(DEFAULT_MIKTAR)
            .aciklama(DEFAULT_ACIKLAMA)
            .tarih(DEFAULT_TARIH)
            .hareketTuru(DEFAULT_HAREKET_TURU);
        // Add required entity
        Ekip ekip = EkipResourceIntTest.createEntity(em);
        em.persist(ekip);
        em.flush();
        stokTakip.setEkip(ekip);
        // Add required entity
        Malzeme malzeme = MalzemeResourceIntTest.createEntity(em);
        em.persist(malzeme);
        em.flush();
        stokTakip.setMalzeme(malzeme);
        return stokTakip;
    }

    @Before
    public void initTest() {
        stokTakip = createEntity(em);
    }

    @Test
    @Transactional
    public void createStokTakip() throws Exception {
        int databaseSizeBeforeCreate = stokTakipRepository.findAll().size();

        // Create the StokTakip
        restStokTakipMockMvc.perform(post("/api/stok-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stokTakip)))
            .andExpect(status().isCreated());

        // Validate the StokTakip in the database
        List<StokTakip> stokTakipList = stokTakipRepository.findAll();
        assertThat(stokTakipList).hasSize(databaseSizeBeforeCreate + 1);
        StokTakip testStokTakip = stokTakipList.get(stokTakipList.size() - 1);
        assertThat(testStokTakip.getMiktar()).isEqualTo(DEFAULT_MIKTAR);
        assertThat(testStokTakip.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testStokTakip.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testStokTakip.getHareketTuru()).isEqualTo(DEFAULT_HAREKET_TURU);
    }

    @Test
    @Transactional
    public void createStokTakipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stokTakipRepository.findAll().size();

        // Create the StokTakip with an existing ID
        stokTakip.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStokTakipMockMvc.perform(post("/api/stok-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stokTakip)))
            .andExpect(status().isBadRequest());

        // Validate the StokTakip in the database
        List<StokTakip> stokTakipList = stokTakipRepository.findAll();
        assertThat(stokTakipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMiktarIsRequired() throws Exception {
        int databaseSizeBeforeTest = stokTakipRepository.findAll().size();
        // set the field null
        stokTakip.setMiktar(null);

        // Create the StokTakip, which fails.

        restStokTakipMockMvc.perform(post("/api/stok-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stokTakip)))
            .andExpect(status().isBadRequest());

        List<StokTakip> stokTakipList = stokTakipRepository.findAll();
        assertThat(stokTakipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStokTakips() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList
        restStokTakipMockMvc.perform(get("/api/stok-takips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stokTakip.getId().intValue())))
            .andExpect(jsonPath("$.[*].miktar").value(hasItem(DEFAULT_MIKTAR.intValue())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].hareketTuru").value(hasItem(DEFAULT_HAREKET_TURU.toString())));
    }
    
    @Test
    @Transactional
    public void getStokTakip() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get the stokTakip
        restStokTakipMockMvc.perform(get("/api/stok-takips/{id}", stokTakip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stokTakip.getId().intValue()))
            .andExpect(jsonPath("$.miktar").value(DEFAULT_MIKTAR.intValue()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.hareketTuru").value(DEFAULT_HAREKET_TURU.toString()));
    }

    @Test
    @Transactional
    public void getAllStokTakipsByMiktarIsEqualToSomething() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where miktar equals to DEFAULT_MIKTAR
        defaultStokTakipShouldBeFound("miktar.equals=" + DEFAULT_MIKTAR);

        // Get all the stokTakipList where miktar equals to UPDATED_MIKTAR
        defaultStokTakipShouldNotBeFound("miktar.equals=" + UPDATED_MIKTAR);
    }

    @Test
    @Transactional
    public void getAllStokTakipsByMiktarIsInShouldWork() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where miktar in DEFAULT_MIKTAR or UPDATED_MIKTAR
        defaultStokTakipShouldBeFound("miktar.in=" + DEFAULT_MIKTAR + "," + UPDATED_MIKTAR);

        // Get all the stokTakipList where miktar equals to UPDATED_MIKTAR
        defaultStokTakipShouldNotBeFound("miktar.in=" + UPDATED_MIKTAR);
    }

    @Test
    @Transactional
    public void getAllStokTakipsByMiktarIsNullOrNotNull() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where miktar is not null
        defaultStokTakipShouldBeFound("miktar.specified=true");

        // Get all the stokTakipList where miktar is null
        defaultStokTakipShouldNotBeFound("miktar.specified=false");
    }

    @Test
    @Transactional
    public void getAllStokTakipsByMiktarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where miktar greater than or equals to DEFAULT_MIKTAR
        defaultStokTakipShouldBeFound("miktar.greaterOrEqualThan=" + DEFAULT_MIKTAR);

        // Get all the stokTakipList where miktar greater than or equals to UPDATED_MIKTAR
        defaultStokTakipShouldNotBeFound("miktar.greaterOrEqualThan=" + UPDATED_MIKTAR);
    }

    @Test
    @Transactional
    public void getAllStokTakipsByMiktarIsLessThanSomething() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where miktar less than or equals to DEFAULT_MIKTAR
        defaultStokTakipShouldNotBeFound("miktar.lessThan=" + DEFAULT_MIKTAR);

        // Get all the stokTakipList where miktar less than or equals to UPDATED_MIKTAR
        defaultStokTakipShouldBeFound("miktar.lessThan=" + UPDATED_MIKTAR);
    }


    @Test
    @Transactional
    public void getAllStokTakipsByTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where tarih equals to DEFAULT_TARIH
        defaultStokTakipShouldBeFound("tarih.equals=" + DEFAULT_TARIH);

        // Get all the stokTakipList where tarih equals to UPDATED_TARIH
        defaultStokTakipShouldNotBeFound("tarih.equals=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllStokTakipsByTarihIsInShouldWork() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where tarih in DEFAULT_TARIH or UPDATED_TARIH
        defaultStokTakipShouldBeFound("tarih.in=" + DEFAULT_TARIH + "," + UPDATED_TARIH);

        // Get all the stokTakipList where tarih equals to UPDATED_TARIH
        defaultStokTakipShouldNotBeFound("tarih.in=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllStokTakipsByTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where tarih is not null
        defaultStokTakipShouldBeFound("tarih.specified=true");

        // Get all the stokTakipList where tarih is null
        defaultStokTakipShouldNotBeFound("tarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllStokTakipsByTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where tarih greater than or equals to DEFAULT_TARIH
        defaultStokTakipShouldBeFound("tarih.greaterOrEqualThan=" + DEFAULT_TARIH);

        // Get all the stokTakipList where tarih greater than or equals to UPDATED_TARIH
        defaultStokTakipShouldNotBeFound("tarih.greaterOrEqualThan=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllStokTakipsByTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where tarih less than or equals to DEFAULT_TARIH
        defaultStokTakipShouldNotBeFound("tarih.lessThan=" + DEFAULT_TARIH);

        // Get all the stokTakipList where tarih less than or equals to UPDATED_TARIH
        defaultStokTakipShouldBeFound("tarih.lessThan=" + UPDATED_TARIH);
    }


    @Test
    @Transactional
    public void getAllStokTakipsByHareketTuruIsEqualToSomething() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where hareketTuru equals to DEFAULT_HAREKET_TURU
        defaultStokTakipShouldBeFound("hareketTuru.equals=" + DEFAULT_HAREKET_TURU);

        // Get all the stokTakipList where hareketTuru equals to UPDATED_HAREKET_TURU
        defaultStokTakipShouldNotBeFound("hareketTuru.equals=" + UPDATED_HAREKET_TURU);
    }

    @Test
    @Transactional
    public void getAllStokTakipsByHareketTuruIsInShouldWork() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where hareketTuru in DEFAULT_HAREKET_TURU or UPDATED_HAREKET_TURU
        defaultStokTakipShouldBeFound("hareketTuru.in=" + DEFAULT_HAREKET_TURU + "," + UPDATED_HAREKET_TURU);

        // Get all the stokTakipList where hareketTuru equals to UPDATED_HAREKET_TURU
        defaultStokTakipShouldNotBeFound("hareketTuru.in=" + UPDATED_HAREKET_TURU);
    }

    @Test
    @Transactional
    public void getAllStokTakipsByHareketTuruIsNullOrNotNull() throws Exception {
        // Initialize the database
        stokTakipRepository.saveAndFlush(stokTakip);

        // Get all the stokTakipList where hareketTuru is not null
        defaultStokTakipShouldBeFound("hareketTuru.specified=true");

        // Get all the stokTakipList where hareketTuru is null
        defaultStokTakipShouldNotBeFound("hareketTuru.specified=false");
    }

    @Test
    @Transactional
    public void getAllStokTakipsByEkipIsEqualToSomething() throws Exception {
        // Initialize the database
        Ekip ekip = EkipResourceIntTest.createEntity(em);
        em.persist(ekip);
        em.flush();
        stokTakip.setEkip(ekip);
        stokTakipRepository.saveAndFlush(stokTakip);
        Long ekipId = ekip.getId();

        // Get all the stokTakipList where ekip equals to ekipId
        defaultStokTakipShouldBeFound("ekipId.equals=" + ekipId);

        // Get all the stokTakipList where ekip equals to ekipId + 1
        defaultStokTakipShouldNotBeFound("ekipId.equals=" + (ekipId + 1));
    }


    @Test
    @Transactional
    public void getAllStokTakipsByMalzemeIsEqualToSomething() throws Exception {
        // Initialize the database
        Malzeme malzeme = MalzemeResourceIntTest.createEntity(em);
        em.persist(malzeme);
        em.flush();
        stokTakip.setMalzeme(malzeme);
        stokTakipRepository.saveAndFlush(stokTakip);
        Long malzemeId = malzeme.getId();

        // Get all the stokTakipList where malzeme equals to malzemeId
        defaultStokTakipShouldBeFound("malzemeId.equals=" + malzemeId);

        // Get all the stokTakipList where malzeme equals to malzemeId + 1
        defaultStokTakipShouldNotBeFound("malzemeId.equals=" + (malzemeId + 1));
    }


    @Test
    @Transactional
    public void getAllStokTakipsByDepoIsEqualToSomething() throws Exception {
        // Initialize the database
        Depo depo = DepoResourceIntTest.createEntity(em);
        em.persist(depo);
        em.flush();
        stokTakip.setDepo(depo);
        stokTakipRepository.saveAndFlush(stokTakip);
        Long depoId = depo.getId();

        // Get all the stokTakipList where depo equals to depoId
        defaultStokTakipShouldBeFound("depoId.equals=" + depoId);

        // Get all the stokTakipList where depo equals to depoId + 1
        defaultStokTakipShouldNotBeFound("depoId.equals=" + (depoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStokTakipShouldBeFound(String filter) throws Exception {
        restStokTakipMockMvc.perform(get("/api/stok-takips?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stokTakip.getId().intValue())))
            .andExpect(jsonPath("$.[*].miktar").value(hasItem(DEFAULT_MIKTAR.intValue())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].hareketTuru").value(hasItem(DEFAULT_HAREKET_TURU.toString())));

        // Check, that the count call also returns 1
        restStokTakipMockMvc.perform(get("/api/stok-takips/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStokTakipShouldNotBeFound(String filter) throws Exception {
        restStokTakipMockMvc.perform(get("/api/stok-takips?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStokTakipMockMvc.perform(get("/api/stok-takips/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStokTakip() throws Exception {
        // Get the stokTakip
        restStokTakipMockMvc.perform(get("/api/stok-takips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStokTakip() throws Exception {
        // Initialize the database
        stokTakipService.save(stokTakip);

        int databaseSizeBeforeUpdate = stokTakipRepository.findAll().size();

        // Update the stokTakip
        StokTakip updatedStokTakip = stokTakipRepository.findById(stokTakip.getId()).get();
        // Disconnect from session so that the updates on updatedStokTakip are not directly saved in db
        em.detach(updatedStokTakip);
        updatedStokTakip
            .miktar(UPDATED_MIKTAR)
            .aciklama(UPDATED_ACIKLAMA)
            .tarih(UPDATED_TARIH)
            .hareketTuru(UPDATED_HAREKET_TURU);

        restStokTakipMockMvc.perform(put("/api/stok-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStokTakip)))
            .andExpect(status().isOk());

        // Validate the StokTakip in the database
        List<StokTakip> stokTakipList = stokTakipRepository.findAll();
        assertThat(stokTakipList).hasSize(databaseSizeBeforeUpdate);
        StokTakip testStokTakip = stokTakipList.get(stokTakipList.size() - 1);
        assertThat(testStokTakip.getMiktar()).isEqualTo(UPDATED_MIKTAR);
        assertThat(testStokTakip.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testStokTakip.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testStokTakip.getHareketTuru()).isEqualTo(UPDATED_HAREKET_TURU);
    }

    @Test
    @Transactional
    public void updateNonExistingStokTakip() throws Exception {
        int databaseSizeBeforeUpdate = stokTakipRepository.findAll().size();

        // Create the StokTakip

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStokTakipMockMvc.perform(put("/api/stok-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stokTakip)))
            .andExpect(status().isBadRequest());

        // Validate the StokTakip in the database
        List<StokTakip> stokTakipList = stokTakipRepository.findAll();
        assertThat(stokTakipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStokTakip() throws Exception {
        // Initialize the database
        stokTakipService.save(stokTakip);

        int databaseSizeBeforeDelete = stokTakipRepository.findAll().size();

        // Delete the stokTakip
        restStokTakipMockMvc.perform(delete("/api/stok-takips/{id}", stokTakip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StokTakip> stokTakipList = stokTakipRepository.findAll();
        assertThat(stokTakipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StokTakip.class);
        StokTakip stokTakip1 = new StokTakip();
        stokTakip1.setId(1L);
        StokTakip stokTakip2 = new StokTakip();
        stokTakip2.setId(stokTakip1.getId());
        assertThat(stokTakip1).isEqualTo(stokTakip2);
        stokTakip2.setId(2L);
        assertThat(stokTakip1).isNotEqualTo(stokTakip2);
        stokTakip1.setId(null);
        assertThat(stokTakip1).isNotEqualTo(stokTakip2);
    }
}
