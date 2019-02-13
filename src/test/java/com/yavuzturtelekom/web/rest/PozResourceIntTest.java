package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Poz;
import com.yavuzturtelekom.repository.PozRepository;
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
import java.math.BigDecimal;
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

    private static final BigDecimal DEFAULT_FIYAT_TELEKOM = new BigDecimal(1);
    private static final BigDecimal UPDATED_FIYAT_TELEKOM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FIYAT_GIRISIM = new BigDecimal(1);
    private static final BigDecimal UPDATED_FIYAT_GIRISIM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FIYAT_TASERON = new BigDecimal(1);
    private static final BigDecimal UPDATED_FIYAT_TASERON = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FIYAT_TASERE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FIYAT_TASERE = new BigDecimal(2);

    private static final Double DEFAULT_KDV_ORANI = 1D;
    private static final Double UPDATED_KDV_ORANI = 2D;

    private static final Boolean DEFAULT_MALZEME_MI = false;
    private static final Boolean UPDATED_MALZEME_MI = true;

    private static final Boolean DEFAULT_AKTIF_MI = false;
    private static final Boolean UPDATED_AKTIF_MI = true;

    @Autowired
    private PozRepository pozRepository;

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
        final PozResource pozResource = new PozResource(pozRepository);
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
            .fiyatTelekom(DEFAULT_FIYAT_TELEKOM)
            .fiyatGirisim(DEFAULT_FIYAT_GIRISIM)
            .fiyatTaseron(DEFAULT_FIYAT_TASERON)
            .fiyatTasere(DEFAULT_FIYAT_TASERE)
            .kdvOrani(DEFAULT_KDV_ORANI)
            .malzemeMi(DEFAULT_MALZEME_MI)
            .aktifMi(DEFAULT_AKTIF_MI);
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
        assertThat(testPoz.getFiyatTelekom()).isEqualTo(DEFAULT_FIYAT_TELEKOM);
        assertThat(testPoz.getFiyatGirisim()).isEqualTo(DEFAULT_FIYAT_GIRISIM);
        assertThat(testPoz.getFiyatTaseron()).isEqualTo(DEFAULT_FIYAT_TASERON);
        assertThat(testPoz.getFiyatTasere()).isEqualTo(DEFAULT_FIYAT_TASERE);
        assertThat(testPoz.getKdvOrani()).isEqualTo(DEFAULT_KDV_ORANI);
        assertThat(testPoz.isMalzemeMi()).isEqualTo(DEFAULT_MALZEME_MI);
        assertThat(testPoz.isAktifMi()).isEqualTo(DEFAULT_AKTIF_MI);
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
            .andExpect(jsonPath("$.[*].fiyatTelekom").value(hasItem(DEFAULT_FIYAT_TELEKOM.intValue())))
            .andExpect(jsonPath("$.[*].fiyatGirisim").value(hasItem(DEFAULT_FIYAT_GIRISIM.intValue())))
            .andExpect(jsonPath("$.[*].fiyatTaseron").value(hasItem(DEFAULT_FIYAT_TASERON.intValue())))
            .andExpect(jsonPath("$.[*].fiyatTasere").value(hasItem(DEFAULT_FIYAT_TASERE.intValue())))
            .andExpect(jsonPath("$.[*].kdvOrani").value(hasItem(DEFAULT_KDV_ORANI.doubleValue())))
            .andExpect(jsonPath("$.[*].malzemeMi").value(hasItem(DEFAULT_MALZEME_MI.booleanValue())))
            .andExpect(jsonPath("$.[*].aktifMi").value(hasItem(DEFAULT_AKTIF_MI.booleanValue())));
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
            .andExpect(jsonPath("$.fiyatTelekom").value(DEFAULT_FIYAT_TELEKOM.intValue()))
            .andExpect(jsonPath("$.fiyatGirisim").value(DEFAULT_FIYAT_GIRISIM.intValue()))
            .andExpect(jsonPath("$.fiyatTaseron").value(DEFAULT_FIYAT_TASERON.intValue()))
            .andExpect(jsonPath("$.fiyatTasere").value(DEFAULT_FIYAT_TASERE.intValue()))
            .andExpect(jsonPath("$.kdvOrani").value(DEFAULT_KDV_ORANI.doubleValue()))
            .andExpect(jsonPath("$.malzemeMi").value(DEFAULT_MALZEME_MI.booleanValue()))
            .andExpect(jsonPath("$.aktifMi").value(DEFAULT_AKTIF_MI.booleanValue()));
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
        pozRepository.saveAndFlush(poz);

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
            .fiyatTelekom(UPDATED_FIYAT_TELEKOM)
            .fiyatGirisim(UPDATED_FIYAT_GIRISIM)
            .fiyatTaseron(UPDATED_FIYAT_TASERON)
            .fiyatTasere(UPDATED_FIYAT_TASERE)
            .kdvOrani(UPDATED_KDV_ORANI)
            .malzemeMi(UPDATED_MALZEME_MI)
            .aktifMi(UPDATED_AKTIF_MI);

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
        assertThat(testPoz.getFiyatTelekom()).isEqualTo(UPDATED_FIYAT_TELEKOM);
        assertThat(testPoz.getFiyatGirisim()).isEqualTo(UPDATED_FIYAT_GIRISIM);
        assertThat(testPoz.getFiyatTaseron()).isEqualTo(UPDATED_FIYAT_TASERON);
        assertThat(testPoz.getFiyatTasere()).isEqualTo(UPDATED_FIYAT_TASERE);
        assertThat(testPoz.getKdvOrani()).isEqualTo(UPDATED_KDV_ORANI);
        assertThat(testPoz.isMalzemeMi()).isEqualTo(UPDATED_MALZEME_MI);
        assertThat(testPoz.isAktifMi()).isEqualTo(UPDATED_AKTIF_MI);
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
        pozRepository.saveAndFlush(poz);

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
