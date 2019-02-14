package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.ProjeTuru;
import com.yavuzturtelekom.repository.ProjeTuruRepository;
import com.yavuzturtelekom.service.ProjeTuruService;
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
 * Test class for the ProjeTuruResource REST controller.
 *
 * @see ProjeTuruResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class ProjeTuruResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_KISALTMA = "AAAAAAAAAA";
    private static final String UPDATED_KISALTMA = "BBBBBBBBBB";

    @Autowired
    private ProjeTuruRepository projeTuruRepository;

    @Autowired
    private ProjeTuruService projeTuruService;

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

    private MockMvc restProjeTuruMockMvc;

    private ProjeTuru projeTuru;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjeTuruResource projeTuruResource = new ProjeTuruResource(projeTuruService);
        this.restProjeTuruMockMvc = MockMvcBuilders.standaloneSetup(projeTuruResource)
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
    public static ProjeTuru createEntity(EntityManager em) {
        ProjeTuru projeTuru = new ProjeTuru()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .kisaltma(DEFAULT_KISALTMA);
        return projeTuru;
    }

    @Before
    public void initTest() {
        projeTuru = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjeTuru() throws Exception {
        int databaseSizeBeforeCreate = projeTuruRepository.findAll().size();

        // Create the ProjeTuru
        restProjeTuruMockMvc.perform(post("/api/proje-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeTuru)))
            .andExpect(status().isCreated());

        // Validate the ProjeTuru in the database
        List<ProjeTuru> projeTuruList = projeTuruRepository.findAll();
        assertThat(projeTuruList).hasSize(databaseSizeBeforeCreate + 1);
        ProjeTuru testProjeTuru = projeTuruList.get(projeTuruList.size() - 1);
        assertThat(testProjeTuru.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testProjeTuru.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testProjeTuru.getKisaltma()).isEqualTo(DEFAULT_KISALTMA);
    }

    @Test
    @Transactional
    public void createProjeTuruWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projeTuruRepository.findAll().size();

        // Create the ProjeTuru with an existing ID
        projeTuru.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjeTuruMockMvc.perform(post("/api/proje-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeTuru)))
            .andExpect(status().isBadRequest());

        // Validate the ProjeTuru in the database
        List<ProjeTuru> projeTuruList = projeTuruRepository.findAll();
        assertThat(projeTuruList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projeTuruRepository.findAll().size();
        // set the field null
        projeTuru.setAd(null);

        // Create the ProjeTuru, which fails.

        restProjeTuruMockMvc.perform(post("/api/proje-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeTuru)))
            .andExpect(status().isBadRequest());

        List<ProjeTuru> projeTuruList = projeTuruRepository.findAll();
        assertThat(projeTuruList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjeTurus() throws Exception {
        // Initialize the database
        projeTuruRepository.saveAndFlush(projeTuru);

        // Get all the projeTuruList
        restProjeTuruMockMvc.perform(get("/api/proje-turus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projeTuru.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA.toString())));
    }
    
    @Test
    @Transactional
    public void getProjeTuru() throws Exception {
        // Initialize the database
        projeTuruRepository.saveAndFlush(projeTuru);

        // Get the projeTuru
        restProjeTuruMockMvc.perform(get("/api/proje-turus/{id}", projeTuru.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projeTuru.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.kisaltma").value(DEFAULT_KISALTMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjeTuru() throws Exception {
        // Get the projeTuru
        restProjeTuruMockMvc.perform(get("/api/proje-turus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjeTuru() throws Exception {
        // Initialize the database
        projeTuruService.save(projeTuru);

        int databaseSizeBeforeUpdate = projeTuruRepository.findAll().size();

        // Update the projeTuru
        ProjeTuru updatedProjeTuru = projeTuruRepository.findById(projeTuru.getId()).get();
        // Disconnect from session so that the updates on updatedProjeTuru are not directly saved in db
        em.detach(updatedProjeTuru);
        updatedProjeTuru
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .kisaltma(UPDATED_KISALTMA);

        restProjeTuruMockMvc.perform(put("/api/proje-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjeTuru)))
            .andExpect(status().isOk());

        // Validate the ProjeTuru in the database
        List<ProjeTuru> projeTuruList = projeTuruRepository.findAll();
        assertThat(projeTuruList).hasSize(databaseSizeBeforeUpdate);
        ProjeTuru testProjeTuru = projeTuruList.get(projeTuruList.size() - 1);
        assertThat(testProjeTuru.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testProjeTuru.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testProjeTuru.getKisaltma()).isEqualTo(UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void updateNonExistingProjeTuru() throws Exception {
        int databaseSizeBeforeUpdate = projeTuruRepository.findAll().size();

        // Create the ProjeTuru

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjeTuruMockMvc.perform(put("/api/proje-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projeTuru)))
            .andExpect(status().isBadRequest());

        // Validate the ProjeTuru in the database
        List<ProjeTuru> projeTuruList = projeTuruRepository.findAll();
        assertThat(projeTuruList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjeTuru() throws Exception {
        // Initialize the database
        projeTuruService.save(projeTuru);

        int databaseSizeBeforeDelete = projeTuruRepository.findAll().size();

        // Delete the projeTuru
        restProjeTuruMockMvc.perform(delete("/api/proje-turus/{id}", projeTuru.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjeTuru> projeTuruList = projeTuruRepository.findAll();
        assertThat(projeTuruList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjeTuru.class);
        ProjeTuru projeTuru1 = new ProjeTuru();
        projeTuru1.setId(1L);
        ProjeTuru projeTuru2 = new ProjeTuru();
        projeTuru2.setId(projeTuru1.getId());
        assertThat(projeTuru1).isEqualTo(projeTuru2);
        projeTuru2.setId(2L);
        assertThat(projeTuru1).isNotEqualTo(projeTuru2);
        projeTuru1.setId(null);
        assertThat(projeTuru1).isNotEqualTo(projeTuru2);
    }
}
