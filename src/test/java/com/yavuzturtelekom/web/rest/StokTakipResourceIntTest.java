package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.StokTakip;
import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.repository.StokTakipRepository;
import com.yavuzturtelekom.service.StokTakipService;
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

    private static final StokHareketTuru DEFAULT_TURU = StokHareketTuru.GIRIS;
    private static final StokHareketTuru UPDATED_TURU = StokHareketTuru.CIKIS;

    @Autowired
    private StokTakipRepository stokTakipRepository;

    @Autowired
    private StokTakipService stokTakipService;

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
        final StokTakipResource stokTakipResource = new StokTakipResource(stokTakipService);
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
            .turu(DEFAULT_TURU);
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
        assertThat(testStokTakip.getTuru()).isEqualTo(DEFAULT_TURU);
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
            .andExpect(jsonPath("$.[*].turu").value(hasItem(DEFAULT_TURU.toString())));
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
            .andExpect(jsonPath("$.turu").value(DEFAULT_TURU.toString()));
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
            .turu(UPDATED_TURU);

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
        assertThat(testStokTakip.getTuru()).isEqualTo(UPDATED_TURU);
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
