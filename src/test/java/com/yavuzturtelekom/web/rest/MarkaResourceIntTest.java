package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Marka;
import com.yavuzturtelekom.repository.MarkaRepository;
import com.yavuzturtelekom.service.MarkaService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.MarkaCriteria;
import com.yavuzturtelekom.service.MarkaQueryService;

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
 * Test class for the MarkaResource REST controller.
 *
 * @see MarkaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class MarkaResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    @Autowired
    private MarkaRepository markaRepository;

    @Autowired
    private MarkaService markaService;

    @Autowired
    private MarkaQueryService markaQueryService;

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

    private MockMvc restMarkaMockMvc;

    private Marka marka;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarkaResource markaResource = new MarkaResource(markaService, markaQueryService);
        this.restMarkaMockMvc = MockMvcBuilders.standaloneSetup(markaResource)
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
    public static Marka createEntity(EntityManager em) {
        Marka marka = new Marka()
            .ad(DEFAULT_AD);
        return marka;
    }

    @Before
    public void initTest() {
        marka = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarka() throws Exception {
        int databaseSizeBeforeCreate = markaRepository.findAll().size();

        // Create the Marka
        restMarkaMockMvc.perform(post("/api/markas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marka)))
            .andExpect(status().isCreated());

        // Validate the Marka in the database
        List<Marka> markaList = markaRepository.findAll();
        assertThat(markaList).hasSize(databaseSizeBeforeCreate + 1);
        Marka testMarka = markaList.get(markaList.size() - 1);
        assertThat(testMarka.getAd()).isEqualTo(DEFAULT_AD);
    }

    @Test
    @Transactional
    public void createMarkaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = markaRepository.findAll().size();

        // Create the Marka with an existing ID
        marka.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarkaMockMvc.perform(post("/api/markas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marka)))
            .andExpect(status().isBadRequest());

        // Validate the Marka in the database
        List<Marka> markaList = markaRepository.findAll();
        assertThat(markaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = markaRepository.findAll().size();
        // set the field null
        marka.setAd(null);

        // Create the Marka, which fails.

        restMarkaMockMvc.perform(post("/api/markas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marka)))
            .andExpect(status().isBadRequest());

        List<Marka> markaList = markaRepository.findAll();
        assertThat(markaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarkas() throws Exception {
        // Initialize the database
        markaRepository.saveAndFlush(marka);

        // Get all the markaList
        restMarkaMockMvc.perform(get("/api/markas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marka.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())));
    }
    
    @Test
    @Transactional
    public void getMarka() throws Exception {
        // Initialize the database
        markaRepository.saveAndFlush(marka);

        // Get the marka
        restMarkaMockMvc.perform(get("/api/markas/{id}", marka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marka.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()));
    }

    @Test
    @Transactional
    public void getAllMarkasByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        markaRepository.saveAndFlush(marka);

        // Get all the markaList where ad equals to DEFAULT_AD
        defaultMarkaShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the markaList where ad equals to UPDATED_AD
        defaultMarkaShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllMarkasByAdIsInShouldWork() throws Exception {
        // Initialize the database
        markaRepository.saveAndFlush(marka);

        // Get all the markaList where ad in DEFAULT_AD or UPDATED_AD
        defaultMarkaShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the markaList where ad equals to UPDATED_AD
        defaultMarkaShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllMarkasByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        markaRepository.saveAndFlush(marka);

        // Get all the markaList where ad is not null
        defaultMarkaShouldBeFound("ad.specified=true");

        // Get all the markaList where ad is null
        defaultMarkaShouldNotBeFound("ad.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMarkaShouldBeFound(String filter) throws Exception {
        restMarkaMockMvc.perform(get("/api/markas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marka.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)));

        // Check, that the count call also returns 1
        restMarkaMockMvc.perform(get("/api/markas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMarkaShouldNotBeFound(String filter) throws Exception {
        restMarkaMockMvc.perform(get("/api/markas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMarkaMockMvc.perform(get("/api/markas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMarka() throws Exception {
        // Get the marka
        restMarkaMockMvc.perform(get("/api/markas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarka() throws Exception {
        // Initialize the database
        markaService.save(marka);

        int databaseSizeBeforeUpdate = markaRepository.findAll().size();

        // Update the marka
        Marka updatedMarka = markaRepository.findById(marka.getId()).get();
        // Disconnect from session so that the updates on updatedMarka are not directly saved in db
        em.detach(updatedMarka);
        updatedMarka
            .ad(UPDATED_AD);

        restMarkaMockMvc.perform(put("/api/markas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarka)))
            .andExpect(status().isOk());

        // Validate the Marka in the database
        List<Marka> markaList = markaRepository.findAll();
        assertThat(markaList).hasSize(databaseSizeBeforeUpdate);
        Marka testMarka = markaList.get(markaList.size() - 1);
        assertThat(testMarka.getAd()).isEqualTo(UPDATED_AD);
    }

    @Test
    @Transactional
    public void updateNonExistingMarka() throws Exception {
        int databaseSizeBeforeUpdate = markaRepository.findAll().size();

        // Create the Marka

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarkaMockMvc.perform(put("/api/markas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marka)))
            .andExpect(status().isBadRequest());

        // Validate the Marka in the database
        List<Marka> markaList = markaRepository.findAll();
        assertThat(markaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMarka() throws Exception {
        // Initialize the database
        markaService.save(marka);

        int databaseSizeBeforeDelete = markaRepository.findAll().size();

        // Delete the marka
        restMarkaMockMvc.perform(delete("/api/markas/{id}", marka.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Marka> markaList = markaRepository.findAll();
        assertThat(markaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Marka.class);
        Marka marka1 = new Marka();
        marka1.setId(1L);
        Marka marka2 = new Marka();
        marka2.setId(marka1.getId());
        assertThat(marka1).isEqualTo(marka2);
        marka2.setId(2L);
        assertThat(marka1).isNotEqualTo(marka2);
        marka1.setId(null);
        assertThat(marka1).isNotEqualTo(marka2);
    }
}
