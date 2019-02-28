package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Santral;
import com.yavuzturtelekom.domain.Mudurluk;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.repository.SantralRepository;
import com.yavuzturtelekom.service.SantralService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.SantralCriteria;
import com.yavuzturtelekom.service.SantralQueryService;

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
 * Test class for the SantralResource REST controller.
 *
 * @see SantralResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class SantralResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    @Autowired
    private SantralRepository santralRepository;

    @Autowired
    private SantralService santralService;

    @Autowired
    private SantralQueryService santralQueryService;

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

    private MockMvc restSantralMockMvc;

    private Santral santral;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SantralResource santralResource = new SantralResource(santralService, santralQueryService);
        this.restSantralMockMvc = MockMvcBuilders.standaloneSetup(santralResource)
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
    public static Santral createEntity(EntityManager em) {
        Santral santral = new Santral()
            .ad(DEFAULT_AD);
        // Add required entity
        Personel personel = PersonelResourceIntTest.createEntity(em);
        em.persist(personel);
        em.flush();
        santral.setSantralSorumlu(personel);
        return santral;
    }

    @Before
    public void initTest() {
        santral = createEntity(em);
    }

    @Test
    @Transactional
    public void createSantral() throws Exception {
        int databaseSizeBeforeCreate = santralRepository.findAll().size();

        // Create the Santral
        restSantralMockMvc.perform(post("/api/santrals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(santral)))
            .andExpect(status().isCreated());

        // Validate the Santral in the database
        List<Santral> santralList = santralRepository.findAll();
        assertThat(santralList).hasSize(databaseSizeBeforeCreate + 1);
        Santral testSantral = santralList.get(santralList.size() - 1);
        assertThat(testSantral.getAd()).isEqualTo(DEFAULT_AD);
    }

    @Test
    @Transactional
    public void createSantralWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = santralRepository.findAll().size();

        // Create the Santral with an existing ID
        santral.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSantralMockMvc.perform(post("/api/santrals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(santral)))
            .andExpect(status().isBadRequest());

        // Validate the Santral in the database
        List<Santral> santralList = santralRepository.findAll();
        assertThat(santralList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = santralRepository.findAll().size();
        // set the field null
        santral.setAd(null);

        // Create the Santral, which fails.

        restSantralMockMvc.perform(post("/api/santrals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(santral)))
            .andExpect(status().isBadRequest());

        List<Santral> santralList = santralRepository.findAll();
        assertThat(santralList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSantrals() throws Exception {
        // Initialize the database
        santralRepository.saveAndFlush(santral);

        // Get all the santralList
        restSantralMockMvc.perform(get("/api/santrals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(santral.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())));
    }
    
    @Test
    @Transactional
    public void getSantral() throws Exception {
        // Initialize the database
        santralRepository.saveAndFlush(santral);

        // Get the santral
        restSantralMockMvc.perform(get("/api/santrals/{id}", santral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(santral.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()));
    }

    @Test
    @Transactional
    public void getAllSantralsByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        santralRepository.saveAndFlush(santral);

        // Get all the santralList where ad equals to DEFAULT_AD
        defaultSantralShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the santralList where ad equals to UPDATED_AD
        defaultSantralShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllSantralsByAdIsInShouldWork() throws Exception {
        // Initialize the database
        santralRepository.saveAndFlush(santral);

        // Get all the santralList where ad in DEFAULT_AD or UPDATED_AD
        defaultSantralShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the santralList where ad equals to UPDATED_AD
        defaultSantralShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllSantralsByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        santralRepository.saveAndFlush(santral);

        // Get all the santralList where ad is not null
        defaultSantralShouldBeFound("ad.specified=true");

        // Get all the santralList where ad is null
        defaultSantralShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllSantralsByMudurlukIsEqualToSomething() throws Exception {
        // Initialize the database
        Mudurluk mudurluk = MudurlukResourceIntTest.createEntity(em);
        em.persist(mudurluk);
        em.flush();
        santral.setMudurluk(mudurluk);
        santralRepository.saveAndFlush(santral);
        Long mudurlukId = mudurluk.getId();

        // Get all the santralList where mudurluk equals to mudurlukId
        defaultSantralShouldBeFound("mudurlukId.equals=" + mudurlukId);

        // Get all the santralList where mudurluk equals to mudurlukId + 1
        defaultSantralShouldNotBeFound("mudurlukId.equals=" + (mudurlukId + 1));
    }


    @Test
    @Transactional
    public void getAllSantralsBySantralSorumluIsEqualToSomething() throws Exception {
        // Initialize the database
        Personel santralSorumlu = PersonelResourceIntTest.createEntity(em);
        em.persist(santralSorumlu);
        em.flush();
        santral.setSantralSorumlu(santralSorumlu);
        santralRepository.saveAndFlush(santral);
        Long santralSorumluId = santralSorumlu.getId();

        // Get all the santralList where santralSorumlu equals to santralSorumluId
        defaultSantralShouldBeFound("santralSorumluId.equals=" + santralSorumluId);

        // Get all the santralList where santralSorumlu equals to santralSorumluId + 1
        defaultSantralShouldNotBeFound("santralSorumluId.equals=" + (santralSorumluId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSantralShouldBeFound(String filter) throws Exception {
        restSantralMockMvc.perform(get("/api/santrals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(santral.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)));

        // Check, that the count call also returns 1
        restSantralMockMvc.perform(get("/api/santrals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSantralShouldNotBeFound(String filter) throws Exception {
        restSantralMockMvc.perform(get("/api/santrals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSantralMockMvc.perform(get("/api/santrals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSantral() throws Exception {
        // Get the santral
        restSantralMockMvc.perform(get("/api/santrals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSantral() throws Exception {
        // Initialize the database
        santralService.save(santral);

        int databaseSizeBeforeUpdate = santralRepository.findAll().size();

        // Update the santral
        Santral updatedSantral = santralRepository.findById(santral.getId()).get();
        // Disconnect from session so that the updates on updatedSantral are not directly saved in db
        em.detach(updatedSantral);
        updatedSantral
            .ad(UPDATED_AD);

        restSantralMockMvc.perform(put("/api/santrals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSantral)))
            .andExpect(status().isOk());

        // Validate the Santral in the database
        List<Santral> santralList = santralRepository.findAll();
        assertThat(santralList).hasSize(databaseSizeBeforeUpdate);
        Santral testSantral = santralList.get(santralList.size() - 1);
        assertThat(testSantral.getAd()).isEqualTo(UPDATED_AD);
    }

    @Test
    @Transactional
    public void updateNonExistingSantral() throws Exception {
        int databaseSizeBeforeUpdate = santralRepository.findAll().size();

        // Create the Santral

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSantralMockMvc.perform(put("/api/santrals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(santral)))
            .andExpect(status().isBadRequest());

        // Validate the Santral in the database
        List<Santral> santralList = santralRepository.findAll();
        assertThat(santralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSantral() throws Exception {
        // Initialize the database
        santralService.save(santral);

        int databaseSizeBeforeDelete = santralRepository.findAll().size();

        // Delete the santral
        restSantralMockMvc.perform(delete("/api/santrals/{id}", santral.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Santral> santralList = santralRepository.findAll();
        assertThat(santralList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Santral.class);
        Santral santral1 = new Santral();
        santral1.setId(1L);
        Santral santral2 = new Santral();
        santral2.setId(santral1.getId());
        assertThat(santral1).isEqualTo(santral2);
        santral2.setId(2L);
        assertThat(santral1).isNotEqualTo(santral2);
        santral1.setId(null);
        assertThat(santral1).isNotEqualTo(santral2);
    }
}
