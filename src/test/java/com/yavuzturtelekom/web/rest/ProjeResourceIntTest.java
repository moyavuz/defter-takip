package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Proje;
import com.yavuzturtelekom.repository.ProjeRepository;
import com.yavuzturtelekom.service.ProjeService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yavuzturtelekom.domain.enumeration.GorevDurumu;
import com.yavuzturtelekom.domain.enumeration.OnemDurumu;
/**
 * Test class for the ProjeResource REST controller.
 *
 * @see ProjeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class ProjeResourceIntTest {

    private static final String DEFAULT_IS_TANIMI = "AAAAAAAAAA";
    private static final String UPDATED_IS_TANIMI = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOSYA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYA_CONTENT_TYPE = "image/png";

    private static final GorevDurumu DEFAULT_DURUMU = GorevDurumu.BEKLIYOR;
    private static final GorevDurumu UPDATED_DURUMU = GorevDurumu.HAZIR;

    private static final OnemDurumu DEFAULT_ONEM_DURUMU = OnemDurumu.ACIL;
    private static final OnemDurumu UPDATED_ONEM_DURUMU = OnemDurumu.IVEDI;

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BASLAMA_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BASLAMA_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BITIS_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BITIS_TARIHI = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProjeRepository projeRepository;

    @Mock
    private ProjeRepository projeRepositoryMock;

    @Mock
    private ProjeService projeServiceMock;

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
            .isTanimi(DEFAULT_IS_TANIMI)
            .aciklama(DEFAULT_ACIKLAMA)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE)
            .durumu(DEFAULT_DURUMU)
            .onemDurumu(DEFAULT_ONEM_DURUMU)
            .tarih(DEFAULT_TARIH)
            .baslamaTarihi(DEFAULT_BASLAMA_TARIHI)
            .bitisTarihi(DEFAULT_BITIS_TARIHI);
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
        assertThat(testProje.getIsTanimi()).isEqualTo(DEFAULT_IS_TANIMI);
        assertThat(testProje.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testProje.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testProje.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
        assertThat(testProje.getDurumu()).isEqualTo(DEFAULT_DURUMU);
        assertThat(testProje.getOnemDurumu()).isEqualTo(DEFAULT_ONEM_DURUMU);
        assertThat(testProje.getTarih()).isEqualTo(DEFAULT_TARIH);
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
    public void getAllProjes() throws Exception {
        // Initialize the database
        projeRepository.saveAndFlush(proje);

        // Get all the projeList
        restProjeMockMvc.perform(get("/api/projes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proje.getId().intValue())))
            .andExpect(jsonPath("$.[*].isTanimi").value(hasItem(DEFAULT_IS_TANIMI.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))))
            .andExpect(jsonPath("$.[*].durumu").value(hasItem(DEFAULT_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].onemDurumu").value(hasItem(DEFAULT_ONEM_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].baslamaTarihi").value(hasItem(DEFAULT_BASLAMA_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].bitisTarihi").value(hasItem(DEFAULT_BITIS_TARIHI.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProjesWithEagerRelationshipsIsEnabled() throws Exception {
        ProjeResource projeResource = new ProjeResource(projeServiceMock);
        when(projeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProjeMockMvc = MockMvcBuilders.standaloneSetup(projeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProjeMockMvc.perform(get("/api/projes?eagerload=true"))
        .andExpect(status().isOk());

        verify(projeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProjesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProjeResource projeResource = new ProjeResource(projeServiceMock);
            when(projeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProjeMockMvc = MockMvcBuilders.standaloneSetup(projeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProjeMockMvc.perform(get("/api/projes?eagerload=true"))
        .andExpect(status().isOk());

            verify(projeServiceMock, times(1)).findAllWithEagerRelationships(any());
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
            .andExpect(jsonPath("$.isTanimi").value(DEFAULT_IS_TANIMI.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)))
            .andExpect(jsonPath("$.durumu").value(DEFAULT_DURUMU.toString()))
            .andExpect(jsonPath("$.onemDurumu").value(DEFAULT_ONEM_DURUMU.toString()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
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
            .isTanimi(UPDATED_IS_TANIMI)
            .aciklama(UPDATED_ACIKLAMA)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE)
            .durumu(UPDATED_DURUMU)
            .onemDurumu(UPDATED_ONEM_DURUMU)
            .tarih(UPDATED_TARIH)
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
        assertThat(testProje.getIsTanimi()).isEqualTo(UPDATED_IS_TANIMI);
        assertThat(testProje.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testProje.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testProje.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
        assertThat(testProje.getDurumu()).isEqualTo(UPDATED_DURUMU);
        assertThat(testProje.getOnemDurumu()).isEqualTo(UPDATED_ONEM_DURUMU);
        assertThat(testProje.getTarih()).isEqualTo(UPDATED_TARIH);
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
