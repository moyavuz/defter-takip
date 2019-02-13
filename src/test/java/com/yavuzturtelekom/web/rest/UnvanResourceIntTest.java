package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Unvan;
import com.yavuzturtelekom.repository.UnvanRepository;
import com.yavuzturtelekom.service.UnvanService;
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
 * Test class for the UnvanResource REST controller.
 *
 * @see UnvanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class UnvanResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AKTIF_MI = false;
    private static final Boolean UPDATED_AKTIF_MI = true;

    @Autowired
    private UnvanRepository unvanRepository;

    @Autowired
    private UnvanService unvanService;

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

    private MockMvc restUnvanMockMvc;

    private Unvan unvan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnvanResource unvanResource = new UnvanResource(unvanService);
        this.restUnvanMockMvc = MockMvcBuilders.standaloneSetup(unvanResource)
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
    public static Unvan createEntity(EntityManager em) {
        Unvan unvan = new Unvan()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .aktifMi(DEFAULT_AKTIF_MI);
        return unvan;
    }

    @Before
    public void initTest() {
        unvan = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnvan() throws Exception {
        int databaseSizeBeforeCreate = unvanRepository.findAll().size();

        // Create the Unvan
        restUnvanMockMvc.perform(post("/api/unvans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unvan)))
            .andExpect(status().isCreated());

        // Validate the Unvan in the database
        List<Unvan> unvanList = unvanRepository.findAll();
        assertThat(unvanList).hasSize(databaseSizeBeforeCreate + 1);
        Unvan testUnvan = unvanList.get(unvanList.size() - 1);
        assertThat(testUnvan.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testUnvan.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testUnvan.isAktifMi()).isEqualTo(DEFAULT_AKTIF_MI);
    }

    @Test
    @Transactional
    public void createUnvanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unvanRepository.findAll().size();

        // Create the Unvan with an existing ID
        unvan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnvanMockMvc.perform(post("/api/unvans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unvan)))
            .andExpect(status().isBadRequest());

        // Validate the Unvan in the database
        List<Unvan> unvanList = unvanRepository.findAll();
        assertThat(unvanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = unvanRepository.findAll().size();
        // set the field null
        unvan.setAd(null);

        // Create the Unvan, which fails.

        restUnvanMockMvc.perform(post("/api/unvans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unvan)))
            .andExpect(status().isBadRequest());

        List<Unvan> unvanList = unvanRepository.findAll();
        assertThat(unvanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnvans() throws Exception {
        // Initialize the database
        unvanRepository.saveAndFlush(unvan);

        // Get all the unvanList
        restUnvanMockMvc.perform(get("/api/unvans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unvan.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].aktifMi").value(hasItem(DEFAULT_AKTIF_MI.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUnvan() throws Exception {
        // Initialize the database
        unvanRepository.saveAndFlush(unvan);

        // Get the unvan
        restUnvanMockMvc.perform(get("/api/unvans/{id}", unvan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unvan.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.aktifMi").value(DEFAULT_AKTIF_MI.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUnvan() throws Exception {
        // Get the unvan
        restUnvanMockMvc.perform(get("/api/unvans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnvan() throws Exception {
        // Initialize the database
        unvanService.save(unvan);

        int databaseSizeBeforeUpdate = unvanRepository.findAll().size();

        // Update the unvan
        Unvan updatedUnvan = unvanRepository.findById(unvan.getId()).get();
        // Disconnect from session so that the updates on updatedUnvan are not directly saved in db
        em.detach(updatedUnvan);
        updatedUnvan
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .aktifMi(UPDATED_AKTIF_MI);

        restUnvanMockMvc.perform(put("/api/unvans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnvan)))
            .andExpect(status().isOk());

        // Validate the Unvan in the database
        List<Unvan> unvanList = unvanRepository.findAll();
        assertThat(unvanList).hasSize(databaseSizeBeforeUpdate);
        Unvan testUnvan = unvanList.get(unvanList.size() - 1);
        assertThat(testUnvan.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testUnvan.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testUnvan.isAktifMi()).isEqualTo(UPDATED_AKTIF_MI);
    }

    @Test
    @Transactional
    public void updateNonExistingUnvan() throws Exception {
        int databaseSizeBeforeUpdate = unvanRepository.findAll().size();

        // Create the Unvan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnvanMockMvc.perform(put("/api/unvans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unvan)))
            .andExpect(status().isBadRequest());

        // Validate the Unvan in the database
        List<Unvan> unvanList = unvanRepository.findAll();
        assertThat(unvanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnvan() throws Exception {
        // Initialize the database
        unvanService.save(unvan);

        int databaseSizeBeforeDelete = unvanRepository.findAll().size();

        // Delete the unvan
        restUnvanMockMvc.perform(delete("/api/unvans/{id}", unvan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Unvan> unvanList = unvanRepository.findAll();
        assertThat(unvanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unvan.class);
        Unvan unvan1 = new Unvan();
        unvan1.setId(1L);
        Unvan unvan2 = new Unvan();
        unvan2.setId(unvan1.getId());
        assertThat(unvan1).isEqualTo(unvan2);
        unvan2.setId(2L);
        assertThat(unvan1).isNotEqualTo(unvan2);
        unvan1.setId(null);
        assertThat(unvan1).isNotEqualTo(unvan2);
    }
}
