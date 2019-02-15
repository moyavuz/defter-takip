package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Iscilik;
import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.domain.Proje;
import com.yavuzturtelekom.repository.IscilikRepository;
import com.yavuzturtelekom.service.IscilikService;
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
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yavuzturtelekom.domain.enumeration.IscilikDurumu;
/**
 * Test class for the IscilikResource REST controller.
 *
 * @see IscilikResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class IscilikResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DETAY = "AAAAAAAAAA";
    private static final String UPDATED_DETAY = "BBBBBBBBBB";

    private static final IscilikDurumu DEFAULT_DURUMU = IscilikDurumu.BEKLIYOR;
    private static final IscilikDurumu UPDATED_DURUMU = IscilikDurumu.BEKLIYOR_MALZEME;

    private static final byte[] DEFAULT_DOSYA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYA_CONTENT_TYPE = "image/png";

    @Autowired
    private IscilikRepository iscilikRepository;

    @Autowired
    private IscilikService iscilikService;

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

    private MockMvc restIscilikMockMvc;

    private Iscilik iscilik;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IscilikResource iscilikResource = new IscilikResource(iscilikService);
        this.restIscilikMockMvc = MockMvcBuilders.standaloneSetup(iscilikResource)
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
    public static Iscilik createEntity(EntityManager em) {
        Iscilik iscilik = new Iscilik()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .detay(DEFAULT_DETAY)
            .durumu(DEFAULT_DURUMU)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE);
        // Add required entity
        Ekip ekip = EkipResourceIntTest.createEntity(em);
        em.persist(ekip);
        em.flush();
        iscilik.setEkip(ekip);
        // Add required entity
        Proje proje = ProjeResourceIntTest.createEntity(em);
        em.persist(proje);
        em.flush();
        iscilik.setProje(proje);
        return iscilik;
    }

    @Before
    public void initTest() {
        iscilik = createEntity(em);
    }

    @Test
    @Transactional
    public void createIscilik() throws Exception {
        int databaseSizeBeforeCreate = iscilikRepository.findAll().size();

        // Create the Iscilik
        restIscilikMockMvc.perform(post("/api/isciliks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iscilik)))
            .andExpect(status().isCreated());

        // Validate the Iscilik in the database
        List<Iscilik> iscilikList = iscilikRepository.findAll();
        assertThat(iscilikList).hasSize(databaseSizeBeforeCreate + 1);
        Iscilik testIscilik = iscilikList.get(iscilikList.size() - 1);
        assertThat(testIscilik.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testIscilik.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testIscilik.getDetay()).isEqualTo(DEFAULT_DETAY);
        assertThat(testIscilik.getDurumu()).isEqualTo(DEFAULT_DURUMU);
        assertThat(testIscilik.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testIscilik.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createIscilikWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iscilikRepository.findAll().size();

        // Create the Iscilik with an existing ID
        iscilik.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIscilikMockMvc.perform(post("/api/isciliks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iscilik)))
            .andExpect(status().isBadRequest());

        // Validate the Iscilik in the database
        List<Iscilik> iscilikList = iscilikRepository.findAll();
        assertThat(iscilikList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIsciliks() throws Exception {
        // Initialize the database
        iscilikRepository.saveAndFlush(iscilik);

        // Get all the iscilikList
        restIscilikMockMvc.perform(get("/api/isciliks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iscilik.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].detay").value(hasItem(DEFAULT_DETAY.toString())))
            .andExpect(jsonPath("$.[*].durumu").value(hasItem(DEFAULT_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))));
    }
    
    @Test
    @Transactional
    public void getIscilik() throws Exception {
        // Initialize the database
        iscilikRepository.saveAndFlush(iscilik);

        // Get the iscilik
        restIscilikMockMvc.perform(get("/api/isciliks/{id}", iscilik.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iscilik.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.detay").value(DEFAULT_DETAY.toString()))
            .andExpect(jsonPath("$.durumu").value(DEFAULT_DURUMU.toString()))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)));
    }

    @Test
    @Transactional
    public void getNonExistingIscilik() throws Exception {
        // Get the iscilik
        restIscilikMockMvc.perform(get("/api/isciliks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIscilik() throws Exception {
        // Initialize the database
        iscilikService.save(iscilik);

        int databaseSizeBeforeUpdate = iscilikRepository.findAll().size();

        // Update the iscilik
        Iscilik updatedIscilik = iscilikRepository.findById(iscilik.getId()).get();
        // Disconnect from session so that the updates on updatedIscilik are not directly saved in db
        em.detach(updatedIscilik);
        updatedIscilik
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .detay(UPDATED_DETAY)
            .durumu(UPDATED_DURUMU)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE);

        restIscilikMockMvc.perform(put("/api/isciliks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIscilik)))
            .andExpect(status().isOk());

        // Validate the Iscilik in the database
        List<Iscilik> iscilikList = iscilikRepository.findAll();
        assertThat(iscilikList).hasSize(databaseSizeBeforeUpdate);
        Iscilik testIscilik = iscilikList.get(iscilikList.size() - 1);
        assertThat(testIscilik.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testIscilik.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testIscilik.getDetay()).isEqualTo(UPDATED_DETAY);
        assertThat(testIscilik.getDurumu()).isEqualTo(UPDATED_DURUMU);
        assertThat(testIscilik.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testIscilik.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingIscilik() throws Exception {
        int databaseSizeBeforeUpdate = iscilikRepository.findAll().size();

        // Create the Iscilik

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIscilikMockMvc.perform(put("/api/isciliks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iscilik)))
            .andExpect(status().isBadRequest());

        // Validate the Iscilik in the database
        List<Iscilik> iscilikList = iscilikRepository.findAll();
        assertThat(iscilikList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIscilik() throws Exception {
        // Initialize the database
        iscilikService.save(iscilik);

        int databaseSizeBeforeDelete = iscilikRepository.findAll().size();

        // Delete the iscilik
        restIscilikMockMvc.perform(delete("/api/isciliks/{id}", iscilik.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Iscilik> iscilikList = iscilikRepository.findAll();
        assertThat(iscilikList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Iscilik.class);
        Iscilik iscilik1 = new Iscilik();
        iscilik1.setId(1L);
        Iscilik iscilik2 = new Iscilik();
        iscilik2.setId(iscilik1.getId());
        assertThat(iscilik1).isEqualTo(iscilik2);
        iscilik2.setId(2L);
        assertThat(iscilik1).isNotEqualTo(iscilik2);
        iscilik1.setId(null);
        assertThat(iscilik1).isNotEqualTo(iscilik2);
    }
}
