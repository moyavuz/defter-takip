package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.MalzemeTakip;
import com.yavuzturtelekom.repository.MalzemeTakipRepository;
import com.yavuzturtelekom.service.MalzemeTakipService;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MalzemeTakipResource REST controller.
 *
 * @see MalzemeTakipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class MalzemeTakipResourceIntTest {

    private static final Long DEFAULT_MIKTAR = 1L;
    private static final Long UPDATED_MIKTAR = 2L;

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MalzemeTakipRepository malzemeTakipRepository;

    @Autowired
    private MalzemeTakipService malzemeTakipService;

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

    private MockMvc restMalzemeTakipMockMvc;

    private MalzemeTakip malzemeTakip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MalzemeTakipResource malzemeTakipResource = new MalzemeTakipResource(malzemeTakipService);
        this.restMalzemeTakipMockMvc = MockMvcBuilders.standaloneSetup(malzemeTakipResource)
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
    public static MalzemeTakip createEntity(EntityManager em) {
        MalzemeTakip malzemeTakip = new MalzemeTakip()
            .miktar(DEFAULT_MIKTAR)
            .aciklama(DEFAULT_ACIKLAMA)
            .tarih(DEFAULT_TARIH);
        return malzemeTakip;
    }

    @Before
    public void initTest() {
        malzemeTakip = createEntity(em);
    }

    @Test
    @Transactional
    public void createMalzemeTakip() throws Exception {
        int databaseSizeBeforeCreate = malzemeTakipRepository.findAll().size();

        // Create the MalzemeTakip
        restMalzemeTakipMockMvc.perform(post("/api/malzeme-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzemeTakip)))
            .andExpect(status().isCreated());

        // Validate the MalzemeTakip in the database
        List<MalzemeTakip> malzemeTakipList = malzemeTakipRepository.findAll();
        assertThat(malzemeTakipList).hasSize(databaseSizeBeforeCreate + 1);
        MalzemeTakip testMalzemeTakip = malzemeTakipList.get(malzemeTakipList.size() - 1);
        assertThat(testMalzemeTakip.getMiktar()).isEqualTo(DEFAULT_MIKTAR);
        assertThat(testMalzemeTakip.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
        assertThat(testMalzemeTakip.getTarih()).isEqualTo(DEFAULT_TARIH);
    }

    @Test
    @Transactional
    public void createMalzemeTakipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = malzemeTakipRepository.findAll().size();

        // Create the MalzemeTakip with an existing ID
        malzemeTakip.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMalzemeTakipMockMvc.perform(post("/api/malzeme-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzemeTakip)))
            .andExpect(status().isBadRequest());

        // Validate the MalzemeTakip in the database
        List<MalzemeTakip> malzemeTakipList = malzemeTakipRepository.findAll();
        assertThat(malzemeTakipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMiktarIsRequired() throws Exception {
        int databaseSizeBeforeTest = malzemeTakipRepository.findAll().size();
        // set the field null
        malzemeTakip.setMiktar(null);

        // Create the MalzemeTakip, which fails.

        restMalzemeTakipMockMvc.perform(post("/api/malzeme-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzemeTakip)))
            .andExpect(status().isBadRequest());

        List<MalzemeTakip> malzemeTakipList = malzemeTakipRepository.findAll();
        assertThat(malzemeTakipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMalzemeTakips() throws Exception {
        // Initialize the database
        malzemeTakipRepository.saveAndFlush(malzemeTakip);

        // Get all the malzemeTakipList
        restMalzemeTakipMockMvc.perform(get("/api/malzeme-takips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(malzemeTakip.getId().intValue())))
            .andExpect(jsonPath("$.[*].miktar").value(hasItem(DEFAULT_MIKTAR.intValue())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())));
    }
    
    @Test
    @Transactional
    public void getMalzemeTakip() throws Exception {
        // Initialize the database
        malzemeTakipRepository.saveAndFlush(malzemeTakip);

        // Get the malzemeTakip
        restMalzemeTakipMockMvc.perform(get("/api/malzeme-takips/{id}", malzemeTakip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(malzemeTakip.getId().intValue()))
            .andExpect(jsonPath("$.miktar").value(DEFAULT_MIKTAR.intValue()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMalzemeTakip() throws Exception {
        // Get the malzemeTakip
        restMalzemeTakipMockMvc.perform(get("/api/malzeme-takips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMalzemeTakip() throws Exception {
        // Initialize the database
        malzemeTakipService.save(malzemeTakip);

        int databaseSizeBeforeUpdate = malzemeTakipRepository.findAll().size();

        // Update the malzemeTakip
        MalzemeTakip updatedMalzemeTakip = malzemeTakipRepository.findById(malzemeTakip.getId()).get();
        // Disconnect from session so that the updates on updatedMalzemeTakip are not directly saved in db
        em.detach(updatedMalzemeTakip);
        updatedMalzemeTakip
            .miktar(UPDATED_MIKTAR)
            .aciklama(UPDATED_ACIKLAMA)
            .tarih(UPDATED_TARIH);

        restMalzemeTakipMockMvc.perform(put("/api/malzeme-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMalzemeTakip)))
            .andExpect(status().isOk());

        // Validate the MalzemeTakip in the database
        List<MalzemeTakip> malzemeTakipList = malzemeTakipRepository.findAll();
        assertThat(malzemeTakipList).hasSize(databaseSizeBeforeUpdate);
        MalzemeTakip testMalzemeTakip = malzemeTakipList.get(malzemeTakipList.size() - 1);
        assertThat(testMalzemeTakip.getMiktar()).isEqualTo(UPDATED_MIKTAR);
        assertThat(testMalzemeTakip.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
        assertThat(testMalzemeTakip.getTarih()).isEqualTo(UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void updateNonExistingMalzemeTakip() throws Exception {
        int databaseSizeBeforeUpdate = malzemeTakipRepository.findAll().size();

        // Create the MalzemeTakip

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMalzemeTakipMockMvc.perform(put("/api/malzeme-takips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(malzemeTakip)))
            .andExpect(status().isBadRequest());

        // Validate the MalzemeTakip in the database
        List<MalzemeTakip> malzemeTakipList = malzemeTakipRepository.findAll();
        assertThat(malzemeTakipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMalzemeTakip() throws Exception {
        // Initialize the database
        malzemeTakipService.save(malzemeTakip);

        int databaseSizeBeforeDelete = malzemeTakipRepository.findAll().size();

        // Delete the malzemeTakip
        restMalzemeTakipMockMvc.perform(delete("/api/malzeme-takips/{id}", malzemeTakip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MalzemeTakip> malzemeTakipList = malzemeTakipRepository.findAll();
        assertThat(malzemeTakipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MalzemeTakip.class);
        MalzemeTakip malzemeTakip1 = new MalzemeTakip();
        malzemeTakip1.setId(1L);
        MalzemeTakip malzemeTakip2 = new MalzemeTakip();
        malzemeTakip2.setId(malzemeTakip1.getId());
        assertThat(malzemeTakip1).isEqualTo(malzemeTakip2);
        malzemeTakip2.setId(2L);
        assertThat(malzemeTakip1).isNotEqualTo(malzemeTakip2);
        malzemeTakip1.setId(null);
        assertThat(malzemeTakip1).isNotEqualTo(malzemeTakip2);
    }
}
