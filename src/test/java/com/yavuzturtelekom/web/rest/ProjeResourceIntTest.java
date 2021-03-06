package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Proje;
import com.yavuzturtelekom.domain.ProjeTuru;
import com.yavuzturtelekom.repository.ProjeRepository;
import com.yavuzturtelekom.service.ProjeService;
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
        final ProjeResource projeResource = new ProjeResource(projeService);
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
