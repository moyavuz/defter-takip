package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.repository.MalzemeRepository;
import com.yavuzturtelekom.service.MalzemeService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MalzemeResource REST controller.
 *
 * @see MalzemeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class MalzemeResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final String DEFAULT_KISALTMA = "AAAAAAAAAA";
    private static final String UPDATED_KISALTMA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FIYAT_MALIYET = new BigDecimal(1);
    private static final BigDecimal UPDATED_FIYAT_MALIYET = new BigDecimal(2);

    @Autowired
    private MalzemeRepository malzemeRepository;

    @Mock
    private MalzemeRepository malzemeRepositoryMock;

    @Mock
    private MalzemeService malzemeServiceMock;

    @Autowired
    private MalzemeService malzemeService;

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

    private MockMvc restMalzemeMockMvc;

    private Malzeme malzeme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MalzemeResource malzemeResource = new MalzemeResource(malzemeService);
        this.restMalzemeMockMvc = MockMvcBuilders.standaloneSetup(malzemeResource)
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
    public static Malzeme createEntity(EntityManager em) {
        Malzeme malzeme = new Malzeme()
            .ad(DEFAULT_AD)
            .aciklama(DEFAULT_ACIKLAMA)
            .kisaltma(DEFAULT_KISALTMA)
            .fiyatMaliyet(DEFAULT_FIYAT_MALIYET);
        return malzeme;
    }

    @Before
    public void initTest() {
        malzeme = createEntity(em);
    }

    @Test
    @Transactional
    public void createMalzeme() throws Exception {
        int databaseSizeBeforeCreate = malzemeRepository.findAll().size();

        // Create the Malzeme
        restMalzemeMockMvc.perform(post("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzeme)))
            .andExpect(status().isCreated());

        // Validate the Malzeme in the database
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeCreate + 1);
        Malzeme testMalzeme = malzemeList.get(malzemeList.size() - 1);
        assertThat(testMalzeme.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testMalzeme.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testMalzeme.getKisaltma()).isEqualTo(DEFAULT_KISALTMA);
        assertThat(testMalzeme.getFiyatMaliyet()).isEqualTo(DEFAULT_FIYAT_MALIYET);
    }

    @Test
    @Transactional
    public void createMalzemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = malzemeRepository.findAll().size();

        // Create the Malzeme with an existing ID
        malzeme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMalzemeMockMvc.perform(post("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzeme)))
            .andExpect(status().isBadRequest());

        // Validate the Malzeme in the database
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = malzemeRepository.findAll().size();
        // set the field null
        malzeme.setAd(null);

        // Create the Malzeme, which fails.

        restMalzemeMockMvc.perform(post("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzeme)))
            .andExpect(status().isBadRequest());

        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMalzemes() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get all the malzemeList
        restMalzemeMockMvc.perform(get("/api/malzemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(malzeme.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].kisaltma").value(hasItem(DEFAULT_KISALTMA.toString())))
            .andExpect(jsonPath("$.[*].fiyatMaliyet").value(hasItem(DEFAULT_FIYAT_MALIYET.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMalzemesWithEagerRelationshipsIsEnabled() throws Exception {
        MalzemeResource malzemeResource = new MalzemeResource(malzemeServiceMock);
        when(malzemeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMalzemeMockMvc = MockMvcBuilders.standaloneSetup(malzemeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMalzemeMockMvc.perform(get("/api/malzemes?eagerload=true"))
        .andExpect(status().isOk());

        verify(malzemeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMalzemesWithEagerRelationshipsIsNotEnabled() throws Exception {
        MalzemeResource malzemeResource = new MalzemeResource(malzemeServiceMock);
            when(malzemeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMalzemeMockMvc = MockMvcBuilders.standaloneSetup(malzemeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMalzemeMockMvc.perform(get("/api/malzemes?eagerload=true"))
        .andExpect(status().isOk());

            verify(malzemeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMalzeme() throws Exception {
        // Initialize the database
        malzemeRepository.saveAndFlush(malzeme);

        // Get the malzeme
        restMalzemeMockMvc.perform(get("/api/malzemes/{id}", malzeme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(malzeme.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.kisaltma").value(DEFAULT_KISALTMA.toString()))
            .andExpect(jsonPath("$.fiyatMaliyet").value(DEFAULT_FIYAT_MALIYET.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMalzeme() throws Exception {
        // Get the malzeme
        restMalzemeMockMvc.perform(get("/api/malzemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMalzeme() throws Exception {
        // Initialize the database
        malzemeService.save(malzeme);

        int databaseSizeBeforeUpdate = malzemeRepository.findAll().size();

        // Update the malzeme
        Malzeme updatedMalzeme = malzemeRepository.findById(malzeme.getId()).get();
        // Disconnect from session so that the updates on updatedMalzeme are not directly saved in db
        em.detach(updatedMalzeme);
        updatedMalzeme
            .ad(UPDATED_AD)
            .aciklama(UPDATED_ACIKLAMA)
            .kisaltma(UPDATED_KISALTMA)
            .fiyatMaliyet(UPDATED_FIYAT_MALIYET);

        restMalzemeMockMvc.perform(put("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMalzeme)))
            .andExpect(status().isOk());

        // Validate the Malzeme in the database
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeUpdate);
        Malzeme testMalzeme = malzemeList.get(malzemeList.size() - 1);
        assertThat(testMalzeme.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testMalzeme.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testMalzeme.getKisaltma()).isEqualTo(UPDATED_KISALTMA);
        assertThat(testMalzeme.getFiyatMaliyet()).isEqualTo(UPDATED_FIYAT_MALIYET);
    }

    @Test
    @Transactional
    public void updateNonExistingMalzeme() throws Exception {
        int databaseSizeBeforeUpdate = malzemeRepository.findAll().size();

        // Create the Malzeme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMalzemeMockMvc.perform(put("/api/malzemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzeme)))
            .andExpect(status().isBadRequest());

        // Validate the Malzeme in the database
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMalzeme() throws Exception {
        // Initialize the database
        malzemeService.save(malzeme);

        int databaseSizeBeforeDelete = malzemeRepository.findAll().size();

        // Delete the malzeme
        restMalzemeMockMvc.perform(delete("/api/malzemes/{id}", malzeme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Malzeme> malzemeList = malzemeRepository.findAll();
        assertThat(malzemeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Malzeme.class);
        Malzeme malzeme1 = new Malzeme();
        malzeme1.setId(1L);
        Malzeme malzeme2 = new Malzeme();
        malzeme2.setId(malzeme1.getId());
        assertThat(malzeme1).isEqualTo(malzeme2);
        malzeme2.setId(2L);
        assertThat(malzeme1).isNotEqualTo(malzeme2);
        malzeme1.setId(null);
        assertThat(malzeme1).isNotEqualTo(malzeme2);
    }
}
