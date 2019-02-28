package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.ZimmetTuru;
import com.yavuzturtelekom.repository.ZimmetTuruRepository;
import com.yavuzturtelekom.service.ZimmetTuruService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.ZimmetTuruCriteria;
import com.yavuzturtelekom.service.ZimmetTuruQueryService;

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
 * Test class for the ZimmetTuruResource REST controller.
 *
 * @see ZimmetTuruResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class ZimmetTuruResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    @Autowired
    private ZimmetTuruRepository zimmetTuruRepository;

    @Autowired
    private ZimmetTuruService zimmetTuruService;

    @Autowired
    private ZimmetTuruQueryService zimmetTuruQueryService;

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

    private MockMvc restZimmetTuruMockMvc;

    private ZimmetTuru zimmetTuru;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZimmetTuruResource zimmetTuruResource = new ZimmetTuruResource(zimmetTuruService, zimmetTuruQueryService);
        this.restZimmetTuruMockMvc = MockMvcBuilders.standaloneSetup(zimmetTuruResource)
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
    public static ZimmetTuru createEntity(EntityManager em) {
        ZimmetTuru zimmetTuru = new ZimmetTuru()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA);
        return zimmetTuru;
    }

    @Before
    public void initTest() {
        zimmetTuru = createEntity(em);
    }

    @Test
    @Transactional
    public void createZimmetTuru() throws Exception {
        int databaseSizeBeforeCreate = zimmetTuruRepository.findAll().size();

        // Create the ZimmetTuru
        restZimmetTuruMockMvc.perform(post("/api/zimmet-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zimmetTuru)))
            .andExpect(status().isCreated());

        // Validate the ZimmetTuru in the database
        List<ZimmetTuru> zimmetTuruList = zimmetTuruRepository.findAll();
        assertThat(zimmetTuruList).hasSize(databaseSizeBeforeCreate + 1);
        ZimmetTuru testZimmetTuru = zimmetTuruList.get(zimmetTuruList.size() - 1);
        assertThat(testZimmetTuru.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testZimmetTuru.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
    }

    @Test
    @Transactional
    public void createZimmetTuruWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zimmetTuruRepository.findAll().size();

        // Create the ZimmetTuru with an existing ID
        zimmetTuru.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZimmetTuruMockMvc.perform(post("/api/zimmet-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zimmetTuru)))
            .andExpect(status().isBadRequest());

        // Validate the ZimmetTuru in the database
        List<ZimmetTuru> zimmetTuruList = zimmetTuruRepository.findAll();
        assertThat(zimmetTuruList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = zimmetTuruRepository.findAll().size();
        // set the field null
        zimmetTuru.setAd(null);

        // Create the ZimmetTuru, which fails.

        restZimmetTuruMockMvc.perform(post("/api/zimmet-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zimmetTuru)))
            .andExpect(status().isBadRequest());

        List<ZimmetTuru> zimmetTuruList = zimmetTuruRepository.findAll();
        assertThat(zimmetTuruList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZimmetTurus() throws Exception {
        // Initialize the database
        zimmetTuruRepository.saveAndFlush(zimmetTuru);

        // Get all the zimmetTuruList
        restZimmetTuruMockMvc.perform(get("/api/zimmet-turus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zimmetTuru.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())));
    }
    
    @Test
    @Transactional
    public void getZimmetTuru() throws Exception {
        // Initialize the database
        zimmetTuruRepository.saveAndFlush(zimmetTuru);

        // Get the zimmetTuru
        restZimmetTuruMockMvc.perform(get("/api/zimmet-turus/{id}", zimmetTuru.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zimmetTuru.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()));
    }

    @Test
    @Transactional
    public void getAllZimmetTurusByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        zimmetTuruRepository.saveAndFlush(zimmetTuru);

        // Get all the zimmetTuruList where ad equals to DEFAULT_AD
        defaultZimmetTuruShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the zimmetTuruList where ad equals to UPDATED_AD
        defaultZimmetTuruShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllZimmetTurusByAdIsInShouldWork() throws Exception {
        // Initialize the database
        zimmetTuruRepository.saveAndFlush(zimmetTuru);

        // Get all the zimmetTuruList where ad in DEFAULT_AD or UPDATED_AD
        defaultZimmetTuruShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the zimmetTuruList where ad equals to UPDATED_AD
        defaultZimmetTuruShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllZimmetTurusByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        zimmetTuruRepository.saveAndFlush(zimmetTuru);

        // Get all the zimmetTuruList where ad is not null
        defaultZimmetTuruShouldBeFound("ad.specified=true");

        // Get all the zimmetTuruList where ad is null
        defaultZimmetTuruShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllZimmetTurusByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        zimmetTuruRepository.saveAndFlush(zimmetTuru);

        // Get all the zimmetTuruList where aciklama equals to DEFAULT_ACIKLAMA
        defaultZimmetTuruShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the zimmetTuruList where aciklama equals to UPDATED_ACIKLAMA
        defaultZimmetTuruShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllZimmetTurusByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        zimmetTuruRepository.saveAndFlush(zimmetTuru);

        // Get all the zimmetTuruList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultZimmetTuruShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the zimmetTuruList where aciklama equals to UPDATED_ACIKLAMA
        defaultZimmetTuruShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllZimmetTurusByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        zimmetTuruRepository.saveAndFlush(zimmetTuru);

        // Get all the zimmetTuruList where aciklama is not null
        defaultZimmetTuruShouldBeFound("aciklama.specified=true");

        // Get all the zimmetTuruList where aciklama is null
        defaultZimmetTuruShouldNotBeFound("aciklama.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultZimmetTuruShouldBeFound(String filter) throws Exception {
        restZimmetTuruMockMvc.perform(get("/api/zimmet-turus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zimmetTuru.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)));

        // Check, that the count call also returns 1
        restZimmetTuruMockMvc.perform(get("/api/zimmet-turus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultZimmetTuruShouldNotBeFound(String filter) throws Exception {
        restZimmetTuruMockMvc.perform(get("/api/zimmet-turus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restZimmetTuruMockMvc.perform(get("/api/zimmet-turus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingZimmetTuru() throws Exception {
        // Get the zimmetTuru
        restZimmetTuruMockMvc.perform(get("/api/zimmet-turus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZimmetTuru() throws Exception {
        // Initialize the database
        zimmetTuruService.save(zimmetTuru);

        int databaseSizeBeforeUpdate = zimmetTuruRepository.findAll().size();

        // Update the zimmetTuru
        ZimmetTuru updatedZimmetTuru = zimmetTuruRepository.findById(zimmetTuru.getId()).get();
        // Disconnect from session so that the updates on updatedZimmetTuru are not directly saved in db
        em.detach(updatedZimmetTuru);
        updatedZimmetTuru
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA);

        restZimmetTuruMockMvc.perform(put("/api/zimmet-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZimmetTuru)))
            .andExpect(status().isOk());

        // Validate the ZimmetTuru in the database
        List<ZimmetTuru> zimmetTuruList = zimmetTuruRepository.findAll();
        assertThat(zimmetTuruList).hasSize(databaseSizeBeforeUpdate);
        ZimmetTuru testZimmetTuru = zimmetTuruList.get(zimmetTuruList.size() - 1);
        assertThat(testZimmetTuru.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testZimmetTuru.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void updateNonExistingZimmetTuru() throws Exception {
        int databaseSizeBeforeUpdate = zimmetTuruRepository.findAll().size();

        // Create the ZimmetTuru

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZimmetTuruMockMvc.perform(put("/api/zimmet-turus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zimmetTuru)))
            .andExpect(status().isBadRequest());

        // Validate the ZimmetTuru in the database
        List<ZimmetTuru> zimmetTuruList = zimmetTuruRepository.findAll();
        assertThat(zimmetTuruList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZimmetTuru() throws Exception {
        // Initialize the database
        zimmetTuruService.save(zimmetTuru);

        int databaseSizeBeforeDelete = zimmetTuruRepository.findAll().size();

        // Delete the zimmetTuru
        restZimmetTuruMockMvc.perform(delete("/api/zimmet-turus/{id}", zimmetTuru.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ZimmetTuru> zimmetTuruList = zimmetTuruRepository.findAll();
        assertThat(zimmetTuruList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZimmetTuru.class);
        ZimmetTuru zimmetTuru1 = new ZimmetTuru();
        zimmetTuru1.setId(1L);
        ZimmetTuru zimmetTuru2 = new ZimmetTuru();
        zimmetTuru2.setId(zimmetTuru1.getId());
        assertThat(zimmetTuru1).isEqualTo(zimmetTuru2);
        zimmetTuru2.setId(2L);
        assertThat(zimmetTuru1).isNotEqualTo(zimmetTuru2);
        zimmetTuru1.setId(null);
        assertThat(zimmetTuru1).isNotEqualTo(zimmetTuru2);
    }
}
