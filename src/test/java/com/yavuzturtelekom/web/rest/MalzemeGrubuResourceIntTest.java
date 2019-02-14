package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.MalzemeGrubu;
import com.yavuzturtelekom.repository.MalzemeGrubuRepository;
import com.yavuzturtelekom.service.MalzemeGrubuService;
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
 * Test class for the MalzemeGrubuResource REST controller.
 *
 * @see MalzemeGrubuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class MalzemeGrubuResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    @Autowired
    private MalzemeGrubuRepository malzemeGrubuRepository;

    @Mock
    private MalzemeGrubuRepository malzemeGrubuRepositoryMock;

    @Mock
    private MalzemeGrubuService malzemeGrubuServiceMock;

    @Autowired
    private MalzemeGrubuService malzemeGrubuService;

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

    private MockMvc restMalzemeGrubuMockMvc;

    private MalzemeGrubu malzemeGrubu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MalzemeGrubuResource malzemeGrubuResource = new MalzemeGrubuResource(malzemeGrubuService);
        this.restMalzemeGrubuMockMvc = MockMvcBuilders.standaloneSetup(malzemeGrubuResource)
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
    public static MalzemeGrubu createEntity(EntityManager em) {
        MalzemeGrubu malzemeGrubu = new MalzemeGrubu()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA);
        return malzemeGrubu;
    }

    @Before
    public void initTest() {
        malzemeGrubu = createEntity(em);
    }

    @Test
    @Transactional
    public void createMalzemeGrubu() throws Exception {
        int databaseSizeBeforeCreate = malzemeGrubuRepository.findAll().size();

        // Create the MalzemeGrubu
        restMalzemeGrubuMockMvc.perform(post("/api/malzeme-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzemeGrubu)))
            .andExpect(status().isCreated());

        // Validate the MalzemeGrubu in the database
        List<MalzemeGrubu> malzemeGrubuList = malzemeGrubuRepository.findAll();
        assertThat(malzemeGrubuList).hasSize(databaseSizeBeforeCreate + 1);
        MalzemeGrubu testMalzemeGrubu = malzemeGrubuList.get(malzemeGrubuList.size() - 1);
        assertThat(testMalzemeGrubu.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testMalzemeGrubu.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
    }

    @Test
    @Transactional
    public void createMalzemeGrubuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = malzemeGrubuRepository.findAll().size();

        // Create the MalzemeGrubu with an existing ID
        malzemeGrubu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMalzemeGrubuMockMvc.perform(post("/api/malzeme-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzemeGrubu)))
            .andExpect(status().isBadRequest());

        // Validate the MalzemeGrubu in the database
        List<MalzemeGrubu> malzemeGrubuList = malzemeGrubuRepository.findAll();
        assertThat(malzemeGrubuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = malzemeGrubuRepository.findAll().size();
        // set the field null
        malzemeGrubu.setAd(null);

        // Create the MalzemeGrubu, which fails.

        restMalzemeGrubuMockMvc.perform(post("/api/malzeme-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzemeGrubu)))
            .andExpect(status().isBadRequest());

        List<MalzemeGrubu> malzemeGrubuList = malzemeGrubuRepository.findAll();
        assertThat(malzemeGrubuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMalzemeGrubus() throws Exception {
        // Initialize the database
        malzemeGrubuRepository.saveAndFlush(malzemeGrubu);

        // Get all the malzemeGrubuList
        restMalzemeGrubuMockMvc.perform(get("/api/malzeme-grubus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(malzemeGrubu.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMalzemeGrubusWithEagerRelationshipsIsEnabled() throws Exception {
        MalzemeGrubuResource malzemeGrubuResource = new MalzemeGrubuResource(malzemeGrubuServiceMock);
        when(malzemeGrubuServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMalzemeGrubuMockMvc = MockMvcBuilders.standaloneSetup(malzemeGrubuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMalzemeGrubuMockMvc.perform(get("/api/malzeme-grubus?eagerload=true"))
        .andExpect(status().isOk());

        verify(malzemeGrubuServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMalzemeGrubusWithEagerRelationshipsIsNotEnabled() throws Exception {
        MalzemeGrubuResource malzemeGrubuResource = new MalzemeGrubuResource(malzemeGrubuServiceMock);
            when(malzemeGrubuServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMalzemeGrubuMockMvc = MockMvcBuilders.standaloneSetup(malzemeGrubuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMalzemeGrubuMockMvc.perform(get("/api/malzeme-grubus?eagerload=true"))
        .andExpect(status().isOk());

            verify(malzemeGrubuServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMalzemeGrubu() throws Exception {
        // Initialize the database
        malzemeGrubuRepository.saveAndFlush(malzemeGrubu);

        // Get the malzemeGrubu
        restMalzemeGrubuMockMvc.perform(get("/api/malzeme-grubus/{id}", malzemeGrubu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(malzemeGrubu.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMalzemeGrubu() throws Exception {
        // Get the malzemeGrubu
        restMalzemeGrubuMockMvc.perform(get("/api/malzeme-grubus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMalzemeGrubu() throws Exception {
        // Initialize the database
        malzemeGrubuService.save(malzemeGrubu);

        int databaseSizeBeforeUpdate = malzemeGrubuRepository.findAll().size();

        // Update the malzemeGrubu
        MalzemeGrubu updatedMalzemeGrubu = malzemeGrubuRepository.findById(malzemeGrubu.getId()).get();
        // Disconnect from session so that the updates on updatedMalzemeGrubu are not directly saved in db
        em.detach(updatedMalzemeGrubu);
        updatedMalzemeGrubu
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA);

        restMalzemeGrubuMockMvc.perform(put("/api/malzeme-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMalzemeGrubu)))
            .andExpect(status().isOk());

        // Validate the MalzemeGrubu in the database
        List<MalzemeGrubu> malzemeGrubuList = malzemeGrubuRepository.findAll();
        assertThat(malzemeGrubuList).hasSize(databaseSizeBeforeUpdate);
        MalzemeGrubu testMalzemeGrubu = malzemeGrubuList.get(malzemeGrubuList.size() - 1);
        assertThat(testMalzemeGrubu.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testMalzemeGrubu.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void updateNonExistingMalzemeGrubu() throws Exception {
        int databaseSizeBeforeUpdate = malzemeGrubuRepository.findAll().size();

        // Create the MalzemeGrubu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMalzemeGrubuMockMvc.perform(put("/api/malzeme-grubus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzemeGrubu)))
            .andExpect(status().isBadRequest());

        // Validate the MalzemeGrubu in the database
        List<MalzemeGrubu> malzemeGrubuList = malzemeGrubuRepository.findAll();
        assertThat(malzemeGrubuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMalzemeGrubu() throws Exception {
        // Initialize the database
        malzemeGrubuService.save(malzemeGrubu);

        int databaseSizeBeforeDelete = malzemeGrubuRepository.findAll().size();

        // Delete the malzemeGrubu
        restMalzemeGrubuMockMvc.perform(delete("/api/malzeme-grubus/{id}", malzemeGrubu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MalzemeGrubu> malzemeGrubuList = malzemeGrubuRepository.findAll();
        assertThat(malzemeGrubuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MalzemeGrubu.class);
        MalzemeGrubu malzemeGrubu1 = new MalzemeGrubu();
        malzemeGrubu1.setId(1L);
        MalzemeGrubu malzemeGrubu2 = new MalzemeGrubu();
        malzemeGrubu2.setId(malzemeGrubu1.getId());
        assertThat(malzemeGrubu1).isEqualTo(malzemeGrubu2);
        malzemeGrubu2.setId(2L);
        assertThat(malzemeGrubu1).isNotEqualTo(malzemeGrubu2);
        malzemeGrubu1.setId(null);
        assertThat(malzemeGrubu1).isNotEqualTo(malzemeGrubu2);
    }
}
