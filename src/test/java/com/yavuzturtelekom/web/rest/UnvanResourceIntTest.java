package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Unvan;
import com.yavuzturtelekom.repository.UnvanRepository;
import com.yavuzturtelekom.service.UnvanService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.UnvanCriteria;
import com.yavuzturtelekom.service.UnvanQueryService;

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

    @Autowired
    private UnvanRepository unvanRepository;

    @Autowired
    private UnvanService unvanService;

    @Autowired
    private UnvanQueryService unvanQueryService;

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
        final UnvanResource unvanResource = new UnvanResource(unvanService, unvanQueryService);
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
            .aciklama(DEFAULT_ACIKLAMA);
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
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())));
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
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()));
    }

    @Test
    @Transactional
    public void getAllUnvansByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        unvanRepository.saveAndFlush(unvan);

        // Get all the unvanList where ad equals to DEFAULT_AD
        defaultUnvanShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the unvanList where ad equals to UPDATED_AD
        defaultUnvanShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllUnvansByAdIsInShouldWork() throws Exception {
        // Initialize the database
        unvanRepository.saveAndFlush(unvan);

        // Get all the unvanList where ad in DEFAULT_AD or UPDATED_AD
        defaultUnvanShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the unvanList where ad equals to UPDATED_AD
        defaultUnvanShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllUnvansByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        unvanRepository.saveAndFlush(unvan);

        // Get all the unvanList where ad is not null
        defaultUnvanShouldBeFound("ad.specified=true");

        // Get all the unvanList where ad is null
        defaultUnvanShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllUnvansByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        unvanRepository.saveAndFlush(unvan);

        // Get all the unvanList where aciklama equals to DEFAULT_ACIKLAMA
        defaultUnvanShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the unvanList where aciklama equals to UPDATED_ACIKLAMA
        defaultUnvanShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllUnvansByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        unvanRepository.saveAndFlush(unvan);

        // Get all the unvanList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultUnvanShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the unvanList where aciklama equals to UPDATED_ACIKLAMA
        defaultUnvanShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllUnvansByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        unvanRepository.saveAndFlush(unvan);

        // Get all the unvanList where aciklama is not null
        defaultUnvanShouldBeFound("aciklama.specified=true");

        // Get all the unvanList where aciklama is null
        defaultUnvanShouldNotBeFound("aciklama.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUnvanShouldBeFound(String filter) throws Exception {
        restUnvanMockMvc.perform(get("/api/unvans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unvan.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)));

        // Check, that the count call also returns 1
        restUnvanMockMvc.perform(get("/api/unvans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUnvanShouldNotBeFound(String filter) throws Exception {
        restUnvanMockMvc.perform(get("/api/unvans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUnvanMockMvc.perform(get("/api/unvans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
            .aciklama(UPDATED_ACIKLAMA);

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
