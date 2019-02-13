package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Iscilik;
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

/**
 * Test class for the IscilikResource REST controller.
 *
 * @see IscilikResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class IscilikResourceIntTest {

    private static final Long DEFAULT_MIKTAR = 1L;
    private static final Long UPDATED_MIKTAR = 2L;

    private static final byte[] DEFAULT_ACIKLAMA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ACIKLAMA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ACIKLAMA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ACIKLAMA_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_AKTIF_MI = false;
    private static final Boolean UPDATED_AKTIF_MI = true;

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
            .miktar(DEFAULT_MIKTAR)
            .aciklama(DEFAULT_ACIKLAMA)
            .aciklamaContentType(DEFAULT_ACIKLAMA_CONTENT_TYPE)
            .aktifMi(DEFAULT_AKTIF_MI);
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
        assertThat(testIscilik.getMiktar()).isEqualTo(DEFAULT_MIKTAR);
        assertThat(testIscilik.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testIscilik.getAciklamaContentType()).isEqualTo(DEFAULT_ACIKLAMA_CONTENT_TYPE);
        assertThat(testIscilik.isAktifMi()).isEqualTo(DEFAULT_AKTIF_MI);
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
            .andExpect(jsonPath("$.[*].miktar").value(hasItem(DEFAULT_MIKTAR.intValue())))
            .andExpect(jsonPath("$.[*].aciklamaContentType").value(hasItem(DEFAULT_ACIKLAMA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(Base64Utils.encodeToString(DEFAULT_ACIKLAMA))))
            .andExpect(jsonPath("$.[*].aktifMi").value(hasItem(DEFAULT_AKTIF_MI.booleanValue())));
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
            .andExpect(jsonPath("$.miktar").value(DEFAULT_MIKTAR.intValue()))
            .andExpect(jsonPath("$.aciklamaContentType").value(DEFAULT_ACIKLAMA_CONTENT_TYPE))
            .andExpect(jsonPath("$.aciklama").value(Base64Utils.encodeToString(DEFAULT_ACIKLAMA)))
            .andExpect(jsonPath("$.aktifMi").value(DEFAULT_AKTIF_MI.booleanValue()));
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
            .miktar(UPDATED_MIKTAR)
            .aciklama(UPDATED_ACIKLAMA)
            .aciklamaContentType(UPDATED_ACIKLAMA_CONTENT_TYPE)
            .aktifMi(UPDATED_AKTIF_MI);

        restIscilikMockMvc.perform(put("/api/isciliks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIscilik)))
            .andExpect(status().isOk());

        // Validate the Iscilik in the database
        List<Iscilik> iscilikList = iscilikRepository.findAll();
        assertThat(iscilikList).hasSize(databaseSizeBeforeUpdate);
        Iscilik testIscilik = iscilikList.get(iscilikList.size() - 1);
        assertThat(testIscilik.getMiktar()).isEqualTo(UPDATED_MIKTAR);
        assertThat(testIscilik.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testIscilik.getAciklamaContentType()).isEqualTo(UPDATED_ACIKLAMA_CONTENT_TYPE);
        assertThat(testIscilik.isAktifMi()).isEqualTo(UPDATED_AKTIF_MI);
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
