package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.repository.EkipRepository;
import com.yavuzturtelekom.service.EkipService;
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

import com.yavuzturtelekom.domain.enumeration.CalismaTuru;
/**
 * Test class for the EkipResource REST controller.
 *
 * @see EkipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class EkipResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_EPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_EPOSTA = "BBBBBBBBBB";

    private static final CalismaTuru DEFAULT_CALISMA_TURU = CalismaTuru.MAASLI;
    private static final CalismaTuru UPDATED_CALISMA_TURU = CalismaTuru.GOTURU;

    @Autowired
    private EkipRepository ekipRepository;

    @Mock
    private EkipRepository ekipRepositoryMock;

    @Mock
    private EkipService ekipServiceMock;

    @Autowired
    private EkipService ekipService;

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

    private MockMvc restEkipMockMvc;

    private Ekip ekip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EkipResource ekipResource = new EkipResource(ekipService);
        this.restEkipMockMvc = MockMvcBuilders.standaloneSetup(ekipResource)
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
    public static Ekip createEntity(EntityManager em) {
        Ekip ekip = new Ekip()
            .ad(DEFAULT_AD)
            .telefon(DEFAULT_TELEFON)
            .eposta(DEFAULT_EPOSTA)
            .calismaTuru(DEFAULT_CALISMA_TURU);
        return ekip;
    }

    @Before
    public void initTest() {
        ekip = createEntity(em);
    }

    @Test
    @Transactional
    public void createEkip() throws Exception {
        int databaseSizeBeforeCreate = ekipRepository.findAll().size();

        // Create the Ekip
        restEkipMockMvc.perform(post("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ekip)))
            .andExpect(status().isCreated());

        // Validate the Ekip in the database
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeCreate + 1);
        Ekip testEkip = ekipList.get(ekipList.size() - 1);
        assertThat(testEkip.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testEkip.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testEkip.getEposta()).isEqualTo(DEFAULT_EPOSTA);
        assertThat(testEkip.getCalismaTuru()).isEqualTo(DEFAULT_CALISMA_TURU);
    }

    @Test
    @Transactional
    public void createEkipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ekipRepository.findAll().size();

        // Create the Ekip with an existing ID
        ekip.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEkipMockMvc.perform(post("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ekip)))
            .andExpect(status().isBadRequest());

        // Validate the Ekip in the database
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ekipRepository.findAll().size();
        // set the field null
        ekip.setAd(null);

        // Create the Ekip, which fails.

        restEkipMockMvc.perform(post("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ekip)))
            .andExpect(status().isBadRequest());

        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEkips() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get all the ekipList
        restEkipMockMvc.perform(get("/api/ekips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ekip.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA.toString())))
            .andExpect(jsonPath("$.[*].calismaTuru").value(hasItem(DEFAULT_CALISMA_TURU.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEkipsWithEagerRelationshipsIsEnabled() throws Exception {
        EkipResource ekipResource = new EkipResource(ekipServiceMock);
        when(ekipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEkipMockMvc = MockMvcBuilders.standaloneSetup(ekipResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEkipMockMvc.perform(get("/api/ekips?eagerload=true"))
        .andExpect(status().isOk());

        verify(ekipServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEkipsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EkipResource ekipResource = new EkipResource(ekipServiceMock);
            when(ekipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEkipMockMvc = MockMvcBuilders.standaloneSetup(ekipResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEkipMockMvc.perform(get("/api/ekips?eagerload=true"))
        .andExpect(status().isOk());

            verify(ekipServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEkip() throws Exception {
        // Initialize the database
        ekipRepository.saveAndFlush(ekip);

        // Get the ekip
        restEkipMockMvc.perform(get("/api/ekips/{id}", ekip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ekip.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.eposta").value(DEFAULT_EPOSTA.toString()))
            .andExpect(jsonPath("$.calismaTuru").value(DEFAULT_CALISMA_TURU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEkip() throws Exception {
        // Get the ekip
        restEkipMockMvc.perform(get("/api/ekips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEkip() throws Exception {
        // Initialize the database
        ekipService.save(ekip);

        int databaseSizeBeforeUpdate = ekipRepository.findAll().size();

        // Update the ekip
        Ekip updatedEkip = ekipRepository.findById(ekip.getId()).get();
        // Disconnect from session so that the updates on updatedEkip are not directly saved in db
        em.detach(updatedEkip);
        updatedEkip
            .ad(UPDATED_AD)
            .telefon(UPDATED_TELEFON)
            .eposta(UPDATED_EPOSTA)
            .calismaTuru(UPDATED_CALISMA_TURU);

        restEkipMockMvc.perform(put("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEkip)))
            .andExpect(status().isOk());

        // Validate the Ekip in the database
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeUpdate);
        Ekip testEkip = ekipList.get(ekipList.size() - 1);
        assertThat(testEkip.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testEkip.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testEkip.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testEkip.getCalismaTuru()).isEqualTo(UPDATED_CALISMA_TURU);
    }

    @Test
    @Transactional
    public void updateNonExistingEkip() throws Exception {
        int databaseSizeBeforeUpdate = ekipRepository.findAll().size();

        // Create the Ekip

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEkipMockMvc.perform(put("/api/ekips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ekip)))
            .andExpect(status().isBadRequest());

        // Validate the Ekip in the database
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEkip() throws Exception {
        // Initialize the database
        ekipService.save(ekip);

        int databaseSizeBeforeDelete = ekipRepository.findAll().size();

        // Delete the ekip
        restEkipMockMvc.perform(delete("/api/ekips/{id}", ekip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ekip> ekipList = ekipRepository.findAll();
        assertThat(ekipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ekip.class);
        Ekip ekip1 = new Ekip();
        ekip1.setId(1L);
        Ekip ekip2 = new Ekip();
        ekip2.setId(ekip1.getId());
        assertThat(ekip1).isEqualTo(ekip2);
        ekip2.setId(2L);
        assertThat(ekip1).isNotEqualTo(ekip2);
        ekip1.setId(null);
        assertThat(ekip1).isNotEqualTo(ekip2);
    }
}
