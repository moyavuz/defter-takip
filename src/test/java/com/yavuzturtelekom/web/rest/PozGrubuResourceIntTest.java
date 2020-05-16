package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.PozGrubu;
import com.yavuzturtelekom.domain.Poz;
import com.yavuzturtelekom.repository.PozGrubuRepository;
import com.yavuzturtelekom.service.PozGrubuService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.PozGrubuCriteria;
import com.yavuzturtelekom.service.PozGrubuQueryService;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PozGrubuResource REST controller.
 *
 * @see PozGrubuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class PozGrubuResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_KISALTMA = "AAAAAAAAAA";
    private static final String UPDATED_KISALTMA = "BBBBBBBBBB";

    @Autowired
    private PozGrubuRepository pozGrubuRepository;

    @Mock
    private PozGrubuRepository pozGrubuRepositoryMock;

    @Mock
    private PozGrubuService pozGrubuServiceMock;

    @Autowired
    private PozGrubuService pozGrubuService;

    @Autowired
    private PozGrubuQueryService pozGrubuQueryService;

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

    private MockMvc restPozGrubuMockMvc;

    private PozGrubu pozGrubu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PozGrubuResource pozGrubuResource = new PozGrubuResource(pozGrubuService, pozGrubuQueryService);
        this.restPozGrubuMockMvc = MockMvcBuilders.standaloneSetup(pozGrubuResource)
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
    public static PozGrubu createEntity(EntityManager em) {
        PozGrubu pozGrubu = new PozGrubu()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .kisaltma(DEFAULT_KISALTMA);
        return pozGrubu;
    }

    @Before
    public void initTest() {
        pozGrubu = createEntity(em);
    }

    @Test
    @Transactional
    public void createPozGrubu() throws Exception {
        int databaseSizeBeforeCreate = pozGrubuRepository.findAll().size();

        // Create the PozGrubu
        restPozGrubuMockMvc.perform(post("/api/poz-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pozGrubu)))
            .andExpect(status().isCreated());

        // Validate the PozGrubu in the database
        List<PozGrubu> pozGrubuList = pozGrubuRepository.findAll();
        assertThat(pozGrubuList).hasSize(databaseSizeBeforeCreate + 1);
        PozGrubu testPozGrubu = pozGrubuList.get(pozGrubuList.size() - 1);
        assertThat(testPozGrubu.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testPozGrubu.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testPozGrubu.getKisaltma()).isEqualTo(DEFAULT_KISALTMA);
    }

    @Test
    @Transactional
    public void createPozGrubuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pozGrubuRepository.findAll().size();

        // Create the PozGrubu with an existing ID
        pozGrubu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPozGrubuMockMvc.perform(post("/api/poz-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pozGrubu)))
            .andExpect(status().isBadRequest());

        // Validate the PozGrubu in the database
        List<PozGrubu> pozGrubuList = pozGrubuRepository.findAll();
        assertThat(pozGrubuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pozGrubuRepository.findAll().size();
        // set the field null
        pozGrubu.setAd(null);

        // Create the PozGrubu, which fails.

        restPozGrubuMockMvc.perform(post("/api/poz-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pozGrubu)))
            .andExpect(status().isBadRequest());

        List<PozGrubu> pozGrubuList = pozGrubuRepository.findAll();
        assertThat(pozGrubuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPozGrubus() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList
        restPozGrubuMockMvc.perform(get("/api/poz-grubus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pozGrubu.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPozGrubusWithEagerRelationshipsIsEnabled() throws Exception {
        PozGrubuResource pozGrubuResource = new PozGrubuResource(pozGrubuServiceMock, pozGrubuQueryService);
        when(pozGrubuServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPozGrubuMockMvc = MockMvcBuilders.standaloneSetup(pozGrubuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPozGrubuMockMvc.perform(get("/api/poz-grubus?eagerload=true"))
        .andExpect(status().isOk());

        verify(pozGrubuServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPozGrubusWithEagerRelationshipsIsNotEnabled() throws Exception {
        PozGrubuResource pozGrubuResource = new PozGrubuResource(pozGrubuServiceMock, pozGrubuQueryService);
            when(pozGrubuServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPozGrubuMockMvc = MockMvcBuilders.standaloneSetup(pozGrubuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPozGrubuMockMvc.perform(get("/api/poz-grubus?eagerload=true"))
        .andExpect(status().isOk());

            verify(pozGrubuServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPozGrubu() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get the pozGrubu
        restPozGrubuMockMvc.perform(get("/api/poz-grubus/{id}", pozGrubu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pozGrubu.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.kisaltma").value(DEFAULT_KISALTMA.toString()));
    }

    @Test
    @Transactional
    public void getAllPozGrubusByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList where ad equals to DEFAULT_AD
        defaultPozGrubuShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the pozGrubuList where ad equals to UPDATED_AD
        defaultPozGrubuShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllPozGrubusByAdIsInShouldWork() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList where ad in DEFAULT_AD or UPDATED_AD
        defaultPozGrubuShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the pozGrubuList where ad equals to UPDATED_AD
        defaultPozGrubuShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllPozGrubusByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList where ad is not null
        defaultPozGrubuShouldBeFound("ad.specified=true");

        // Get all the pozGrubuList where ad is null
        defaultPozGrubuShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozGrubusByAciklamaIsEqualToSomething() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList where aciklama equals to DEFAULT_ACIKLAMA
        defaultPozGrubuShouldBeFound("aciklama.equals=" + DEFAULT_ACIKLAMA);

        // Get all the pozGrubuList where aciklama equals to UPDATED_ACIKLAMA
        defaultPozGrubuShouldNotBeFound("aciklama.equals=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllPozGrubusByAciklamaIsInShouldWork() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList where aciklama in DEFAULT_ACIKLAMA or UPDATED_ACIKLAMA
        defaultPozGrubuShouldBeFound("aciklama.in=" + DEFAULT_ACIKLAMA + "," + UPDATED_ACIKLAMA);

        // Get all the pozGrubuList where aciklama equals to UPDATED_ACIKLAMA
        defaultPozGrubuShouldNotBeFound("aciklama.in=" + UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void getAllPozGrubusByAciklamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList where aciklama is not null
        defaultPozGrubuShouldBeFound("aciklama.specified=true");

        // Get all the pozGrubuList where aciklama is null
        defaultPozGrubuShouldNotBeFound("aciklama.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozGrubusByKisaltmaIsEqualToSomething() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList where kisaltma equals to DEFAULT_KISALTMA
        defaultPozGrubuShouldBeFound("kisaltma.equals=" + DEFAULT_KISALTMA);

        // Get all the pozGrubuList where kisaltma equals to UPDATED_KISALTMA
        defaultPozGrubuShouldNotBeFound("kisaltma.equals=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllPozGrubusByKisaltmaIsInShouldWork() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList where kisaltma in DEFAULT_KISALTMA or UPDATED_KISALTMA
        defaultPozGrubuShouldBeFound("kisaltma.in=" + DEFAULT_KISALTMA + "," + UPDATED_KISALTMA);

        // Get all the pozGrubuList where kisaltma equals to UPDATED_KISALTMA
        defaultPozGrubuShouldNotBeFound("kisaltma.in=" + UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void getAllPozGrubusByKisaltmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pozGrubuRepository.saveAndFlush(pozGrubu);

        // Get all the pozGrubuList where kisaltma is not null
        defaultPozGrubuShouldBeFound("kisaltma.specified=true");

        // Get all the pozGrubuList where kisaltma is null
        defaultPozGrubuShouldNotBeFound("kisaltma.specified=false");
    }

    @Test
    @Transactional
    public void getAllPozGrubusByPozListesiIsEqualToSomething() throws Exception {
        // Initialize the database
        Poz pozListesi = PozResourceIntTest.createEntity(em);
        em.persist(pozListesi);
        em.flush();
        pozGrubu.addPozListesi(pozListesi);
        pozGrubuRepository.saveAndFlush(pozGrubu);
        Long pozListesiId = pozListesi.getId();

        // Get all the pozGrubuList where pozListesi equals to pozListesiId
        defaultPozGrubuShouldBeFound("pozListesiId.equals=" + pozListesiId);

        // Get all the pozGrubuList where pozListesi equals to pozListesiId + 1
        defaultPozGrubuShouldNotBeFound("pozListesiId.equals=" + (pozListesiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPozGrubuShouldBeFound(String filter) throws Exception {
        restPozGrubuMockMvc.perform(get("/api/poz-grubus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pozGrubu.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA)))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA)));

        // Check, that the count call also returns 1
        restPozGrubuMockMvc.perform(get("/api/poz-grubus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPozGrubuShouldNotBeFound(String filter) throws Exception {
        restPozGrubuMockMvc.perform(get("/api/poz-grubus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPozGrubuMockMvc.perform(get("/api/poz-grubus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPozGrubu() throws Exception {
        // Get the pozGrubu
        restPozGrubuMockMvc.perform(get("/api/poz-grubus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePozGrubu() throws Exception {
        // Initialize the database
        pozGrubuService.save(pozGrubu);

        int databaseSizeBeforeUpdate = pozGrubuRepository.findAll().size();

        // Update the pozGrubu
        PozGrubu updatedPozGrubu = pozGrubuRepository.findById(pozGrubu.getId()).get();
        // Disconnect from session so that the updates on updatedPozGrubu are not directly saved in db
        em.detach(updatedPozGrubu);
        updatedPozGrubu
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .kisaltma(UPDATED_KISALTMA);

        restPozGrubuMockMvc.perform(put("/api/poz-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPozGrubu)))
            .andExpect(status().isOk());

        // Validate the PozGrubu in the database
        List<PozGrubu> pozGrubuList = pozGrubuRepository.findAll();
        assertThat(pozGrubuList).hasSize(databaseSizeBeforeUpdate);
        PozGrubu testPozGrubu = pozGrubuList.get(pozGrubuList.size() - 1);
        assertThat(testPozGrubu.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testPozGrubu.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testPozGrubu.getKisaltma()).isEqualTo(UPDATED_KISALTMA);
    }

    @Test
    @Transactional
    public void updateNonExistingPozGrubu() throws Exception {
        int databaseSizeBeforeUpdate = pozGrubuRepository.findAll().size();

        // Create the PozGrubu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPozGrubuMockMvc.perform(put("/api/poz-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pozGrubu)))
            .andExpect(status().isBadRequest());

        // Validate the PozGrubu in the database
        List<PozGrubu> pozGrubuList = pozGrubuRepository.findAll();
        assertThat(pozGrubuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePozGrubu() throws Exception {
        // Initialize the database
        pozGrubuService.save(pozGrubu);

        int databaseSizeBeforeDelete = pozGrubuRepository.findAll().size();

        // Delete the pozGrubu
        restPozGrubuMockMvc.perform(delete("/api/poz-grubus/{id}", pozGrubu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PozGrubu> pozGrubuList = pozGrubuRepository.findAll();
        assertThat(pozGrubuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PozGrubu.class);
        PozGrubu pozGrubu1 = new PozGrubu();
        pozGrubu1.setId(1L);
        PozGrubu pozGrubu2 = new PozGrubu();
        pozGrubu2.setId(pozGrubu1.getId());
        assertThat(pozGrubu1).isEqualTo(pozGrubu2);
        pozGrubu2.setId(2L);
        assertThat(pozGrubu1).isNotEqualTo(pozGrubu2);
        pozGrubu1.setId(null);
        assertThat(pozGrubu1).isNotEqualTo(pozGrubu2);
    }
}
