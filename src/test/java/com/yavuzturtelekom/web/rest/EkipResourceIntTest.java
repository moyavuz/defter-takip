package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.domain.Hakedis;
import com.yavuzturtelekom.domain.StokTakip;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.domain.Mudurluk;
import com.yavuzturtelekom.repository.EkipRepository;
import com.yavuzturtelekom.service.EkipService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.EkipCriteria;
import com.yavuzturtelekom.service.EkipQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yavuzturtelekom.domain.enumeration.PersonelTuru;
/**
 * Test class for the EkipResource REST controller.
 *
 * @see EkipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class EkipResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_EPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_EPOSTA = "BBBBBBBBBB";

    private static final PersonelTuru DEFAULT_TURU = PersonelTuru.MAASLI;
    private static final PersonelTuru UPDATED_TURU = PersonelTuru.GOTURU;

    @Autowired
    private EkipRepository ekipRepository;

    @Mock
    private EkipRepository ekipRepositoryMock;

    @Mock
    private EkipService ekipServiceMock;

    @Autowired
    private EkipService ekipService;

    @Autowired
    private EkipQueryService ekipQueryService;

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

    private MockMvc restEkipMockMvc;

    private Ekip ekip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EkipResource ekipResource = new EkipResource(ekipService, ekipQueryService);
        this.restEkipMockMvc = MockMvcBuilders.standaloneSetup(ekipResource)
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
    public static Ekip createEntity(EntityManager em) {
        Ekip ekip = new Ekip()
            .ad(DEFAULT_AD)
            .telefon(DEFAULT_TELEFON)
            .eposta(DEFAULT_EPOSTA)
            .turu(DEFAULT_TURU);
        // Add required entity
        Personel personel = PersonelResourceIntTest.createEntity(em);
        em.persist(personel);
        em.flush();
        ekip.setEkipSorumlu(personel);
        return ekip;
    }

    @Before
    public void initTest() {
        ekip = createEntity(em);
    }

    @Test
    @Transactional
    public void createEkip() throws Exception {
        int databaseSizeBeforeCreate = ekipRepository.findAll().size();

        // Create the Ekip
        restEkipMockMvc.perform(post("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ekip)))
            .andExpect(status().isCreated());

        // Validate the Ekip in the database
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeCreate + 1);
        Ekip testEkip = ekipList.get(ekipList.size() - 1);
        assertThat(testEkip.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testEkip.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testEkip.getEposta()).isEqualTo(DEFAULT_EPOSTA);
        assertThat(testEkip.getTuru()).isEqualTo(DEFAULT_TURU);
    }

    @Test
    @Transactional
    public void createEkipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ekipRepository.findAll().size();

        // Create the Ekip with an existing ID
        ekip.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEkipMockMvc.perform(post("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ekip)))
            .andExpect(status().isBadRequest());

        // Validate the Ekip in the database
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ekipRepository.findAll().size();
        // set the field null
        ekip.setAd(null);

        // Create the Ekip, which fails.

        restEkipMockMvc.perform(post("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ekip)))
            .andExpect(status().isBadRequest());

        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEkips() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList
        restEkipMockMvc.perform(get("/api/ekips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ekip.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA.toString())))
            .andExpect(jsonPath("$.[*].turu").value(hasItem(DEFAULT_TURU.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEkipsWithEagerRelationshipsIsEnabled() throws Exception {
        EkipResource ekipResource = new EkipResource(ekipServiceMock, ekipQueryService);
        when(ekipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEkipMockMvc = MockMvcBuilders.standaloneSetup(ekipResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEkipMockMvc.perform(get("/api/ekips?eagerload=true"))
        .andExpect(status().isOk());

        verify(ekipServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEkipsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EkipResource ekipResource = new EkipResource(ekipServiceMock, ekipQueryService);
            when(ekipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEkipMockMvc = MockMvcBuilders.standaloneSetup(ekipResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEkipMockMvc.perform(get("/api/ekips?eagerload=true"))
        .andExpect(status().isOk());

            verify(ekipServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEkip() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get the ekip
        restEkipMockMvc.perform(get("/api/ekips/{id}", ekip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ekip.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.eposta").value(DEFAULT_EPOSTA.toString()))
            .andExpect(jsonPath("$.turu").value(DEFAULT_TURU.toString()));
    }

    @Test
    @Transactional
    public void getAllEkipsByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where ad equals to DEFAULT_AD
        defaultEkipShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the ekipList where ad equals to UPDATED_AD
        defaultEkipShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllEkipsByAdIsInShouldWork() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where ad in DEFAULT_AD or UPDATED_AD
        defaultEkipShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the ekipList where ad equals to UPDATED_AD
        defaultEkipShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllEkipsByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where ad is not null
        defaultEkipShouldBeFound("ad.specified=true");

        // Get all the ekipList where ad is null
        defaultEkipShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllEkipsByTelefonIsEqualToSomething() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where telefon equals to DEFAULT_TELEFON
        defaultEkipShouldBeFound("telefon.equals=" + DEFAULT_TELEFON);

        // Get all the ekipList where telefon equals to UPDATED_TELEFON
        defaultEkipShouldNotBeFound("telefon.equals=" + UPDATED_TELEFON);
    }

    @Test
    @Transactional
    public void getAllEkipsByTelefonIsInShouldWork() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where telefon in DEFAULT_TELEFON or UPDATED_TELEFON
        defaultEkipShouldBeFound("telefon.in=" + DEFAULT_TELEFON + "," + UPDATED_TELEFON);

        // Get all the ekipList where telefon equals to UPDATED_TELEFON
        defaultEkipShouldNotBeFound("telefon.in=" + UPDATED_TELEFON);
    }

    @Test
    @Transactional
    public void getAllEkipsByTelefonIsNullOrNotNull() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where telefon is not null
        defaultEkipShouldBeFound("telefon.specified=true");

        // Get all the ekipList where telefon is null
        defaultEkipShouldNotBeFound("telefon.specified=false");
    }

    @Test
    @Transactional
    public void getAllEkipsByEpostaIsEqualToSomething() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where eposta equals to DEFAULT_EPOSTA
        defaultEkipShouldBeFound("eposta.equals=" + DEFAULT_EPOSTA);

        // Get all the ekipList where eposta equals to UPDATED_EPOSTA
        defaultEkipShouldNotBeFound("eposta.equals=" + UPDATED_EPOSTA);
    }

    @Test
    @Transactional
    public void getAllEkipsByEpostaIsInShouldWork() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where eposta in DEFAULT_EPOSTA or UPDATED_EPOSTA
        defaultEkipShouldBeFound("eposta.in=" + DEFAULT_EPOSTA + "," + UPDATED_EPOSTA);

        // Get all the ekipList where eposta equals to UPDATED_EPOSTA
        defaultEkipShouldNotBeFound("eposta.in=" + UPDATED_EPOSTA);
    }

    @Test
    @Transactional
    public void getAllEkipsByEpostaIsNullOrNotNull() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where eposta is not null
        defaultEkipShouldBeFound("eposta.specified=true");

        // Get all the ekipList where eposta is null
        defaultEkipShouldNotBeFound("eposta.specified=false");
    }

    @Test
    @Transactional
    public void getAllEkipsByTuruIsEqualToSomething() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where turu equals to DEFAULT_TURU
        defaultEkipShouldBeFound("turu.equals=" + DEFAULT_TURU);

        // Get all the ekipList where turu equals to UPDATED_TURU
        defaultEkipShouldNotBeFound("turu.equals=" + UPDATED_TURU);
    }

    @Test
    @Transactional
    public void getAllEkipsByTuruIsInShouldWork() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where turu in DEFAULT_TURU or UPDATED_TURU
        defaultEkipShouldBeFound("turu.in=" + DEFAULT_TURU + "," + UPDATED_TURU);

        // Get all the ekipList where turu equals to UPDATED_TURU
        defaultEkipShouldNotBeFound("turu.in=" + UPDATED_TURU);
    }

    @Test
    @Transactional
    public void getAllEkipsByTuruIsNullOrNotNull() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList where turu is not null
        defaultEkipShouldBeFound("turu.specified=true");

        // Get all the ekipList where turu is null
        defaultEkipShouldNotBeFound("turu.specified=false");
    }

    @Test
    @Transactional
    public void getAllEkipsByHakedisIsEqualToSomething() throws Exception {
        // Initialize the database
        Hakedis hakedis = HakedisResourceIntTest.createEntity(em);
        em.persist(hakedis);
        em.flush();
        ekip.addHakedis(hakedis);
        ekipRepository.saveAndFlush(ekip);
        Long hakedisId = hakedis.getId();

        // Get all the ekipList where hakedis equals to hakedisId
        defaultEkipShouldBeFound("hakedisId.equals=" + hakedisId);

        // Get all the ekipList where hakedis equals to hakedisId + 1
        defaultEkipShouldNotBeFound("hakedisId.equals=" + (hakedisId + 1));
    }


    @Test
    @Transactional
    public void getAllEkipsByStokTakipIsEqualToSomething() throws Exception {
        // Initialize the database
        StokTakip stokTakip = StokTakipResourceIntTest.createEntity(em);
        em.persist(stokTakip);
        em.flush();
        ekip.addStokTakip(stokTakip);
        ekipRepository.saveAndFlush(ekip);
        Long stokTakipId = stokTakip.getId();

        // Get all the ekipList where stokTakip equals to stokTakipId
        defaultEkipShouldBeFound("stokTakipId.equals=" + stokTakipId);

        // Get all the ekipList where stokTakip equals to stokTakipId + 1
        defaultEkipShouldNotBeFound("stokTakipId.equals=" + (stokTakipId + 1));
    }


    @Test
    @Transactional
    public void getAllEkipsByEkipSorumluIsEqualToSomething() throws Exception {
        // Initialize the database
        Personel ekipSorumlu = PersonelResourceIntTest.createEntity(em);
        em.persist(ekipSorumlu);
        em.flush();
        ekip.setEkipSorumlu(ekipSorumlu);
        ekipRepository.saveAndFlush(ekip);
        Long ekipSorumluId = ekipSorumlu.getId();

        // Get all the ekipList where ekipSorumlu equals to ekipSorumluId
        defaultEkipShouldBeFound("ekipSorumluId.equals=" + ekipSorumluId);

        // Get all the ekipList where ekipSorumlu equals to ekipSorumluId + 1
        defaultEkipShouldNotBeFound("ekipSorumluId.equals=" + (ekipSorumluId + 1));
    }


    @Test
    @Transactional
    public void getAllEkipsByMudurlukIsEqualToSomething() throws Exception {
        // Initialize the database
        Mudurluk mudurluk = MudurlukResourceIntTest.createEntity(em);
        em.persist(mudurluk);
        em.flush();
        ekip.setMudurluk(mudurluk);
        ekipRepository.saveAndFlush(ekip);
        Long mudurlukId = mudurluk.getId();

        // Get all the ekipList where mudurluk equals to mudurlukId
        defaultEkipShouldBeFound("mudurlukId.equals=" + mudurlukId);

        // Get all the ekipList where mudurluk equals to mudurlukId + 1
        defaultEkipShouldNotBeFound("mudurlukId.equals=" + (mudurlukId + 1));
    }


    @Test
    @Transactional
    public void getAllEkipsByEkipPersonelIsEqualToSomething() throws Exception {
        // Initialize the database
        Personel ekipPersonel = PersonelResourceIntTest.createEntity(em);
        em.persist(ekipPersonel);
        em.flush();
        ekip.addEkipPersonel(ekipPersonel);
        ekipRepository.saveAndFlush(ekip);
        Long ekipPersonelId = ekipPersonel.getId();

        // Get all the ekipList where ekipPersonel equals to ekipPersonelId
        defaultEkipShouldBeFound("ekipPersonelId.equals=" + ekipPersonelId);

        // Get all the ekipList where ekipPersonel equals to ekipPersonelId + 1
        defaultEkipShouldNotBeFound("ekipPersonelId.equals=" + (ekipPersonelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEkipShouldBeFound(String filter) throws Exception {
        restEkipMockMvc.perform(get("/api/ekips?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ekip.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON)))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA)))
            .andExpect(jsonPath("$.[*].turu").value(hasItem(DEFAULT_TURU.toString())));

        // Check, that the count call also returns 1
        restEkipMockMvc.perform(get("/api/ekips/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEkipShouldNotBeFound(String filter) throws Exception {
        restEkipMockMvc.perform(get("/api/ekips?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEkipMockMvc.perform(get("/api/ekips/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEkip() throws Exception {
        // Get the ekip
        restEkipMockMvc.perform(get("/api/ekips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEkip() throws Exception {
        // Initialize the database
        ekipService.save(ekip);

        int databaseSizeBeforeUpdate = ekipRepository.findAll().size();

        // Update the ekip
        Ekip updatedEkip = ekipRepository.findById(ekip.getId()).get();
        // Disconnect from session so that the updates on updatedEkip are not directly saved in db
        em.detach(updatedEkip);
        updatedEkip
            .ad(UPDATED_AD)
            .telefon(UPDATED_TELEFON)
            .eposta(UPDATED_EPOSTA)
            .turu(UPDATED_TURU);

        restEkipMockMvc.perform(put("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEkip)))
            .andExpect(status().isOk());

        // Validate the Ekip in the database
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeUpdate);
        Ekip testEkip = ekipList.get(ekipList.size() - 1);
        assertThat(testEkip.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testEkip.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testEkip.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testEkip.getTuru()).isEqualTo(UPDATED_TURU);
    }

    @Test
    @Transactional
    public void updateNonExistingEkip() throws Exception {
        int databaseSizeBeforeUpdate = ekipRepository.findAll().size();

        // Create the Ekip

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEkipMockMvc.perform(put("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ekip)))
            .andExpect(status().isBadRequest());

        // Validate the Ekip in the database
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEkip() throws Exception {
        // Initialize the database
        ekipService.save(ekip);

        int databaseSizeBeforeDelete = ekipRepository.findAll().size();

        // Delete the ekip
        restEkipMockMvc.perform(delete("/api/ekips/{id}", ekip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ekip.class);
        Ekip ekip1 = new Ekip();
        ekip1.setId(1L);
        Ekip ekip2 = new Ekip();
        ekip2.setId(ekip1.getId());
        assertThat(ekip1).isEqualTo(ekip2);
        ekip2.setId(2L);
        assertThat(ekip1).isNotEqualTo(ekip2);
        ekip1.setId(null);
        assertThat(ekip1).isNotEqualTo(ekip2);
    }
}
