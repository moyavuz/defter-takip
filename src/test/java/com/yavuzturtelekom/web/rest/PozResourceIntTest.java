package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Poz;
import com.yavuzturtelekom.domain.Birim;
import com.yavuzturtelekom.repository.PozRepository;
import com.yavuzturtelekom.service.PozService;
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

    private static final String DEFAULT_IHALE_SAHASI = "AAAAAAAAAA";
    private static final String UPDATED_IHALE_SAHASI = "BBBBBBBBBB";

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
        final PozResource pozResource = new PozResource(pozService);
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
            .ihaleSahasi(DEFAULT_IHALE_SAHASI)
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
        assertThat(testPoz.getIhaleSahasi()).isEqualTo(DEFAULT_IHALE_SAHASI);
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
            .andExpect(jsonPath("$.[*].ihaleSahasi").value(hasItem(DEFAULT_IHALE_SAHASI.toString())))
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
            .andExpect(jsonPath("$.ihaleSahasi").value(DEFAULT_IHALE_SAHASI.toString()))
            .andExpect(jsonPath("$.yil").value(DEFAULT_YIL))
            .andExpect(jsonPath("$.tenzilatsizFiyat").value(DEFAULT_TENZILATSIZ_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.tenzilatliFiyat").value(DEFAULT_TENZILATLI_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.taseronFiyat").value(DEFAULT_TASERON_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.tasereFiyat").value(DEFAULT_TASERE_FIYAT.doubleValue()))
            .andExpect(jsonPath("$.malzemeMi").value(DEFAULT_MALZEME_MI.booleanValue()));
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
            .ihaleSahasi(UPDATED_IHALE_SAHASI)
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
        assertThat(testPoz.getIhaleSahasi()).isEqualTo(UPDATED_IHALE_SAHASI);
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
