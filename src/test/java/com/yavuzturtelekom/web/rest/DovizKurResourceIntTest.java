package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.DovizKur;
import com.yavuzturtelekom.repository.DovizKurRepository;
import com.yavuzturtelekom.service.DovizKurService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yavuzturtelekom.domain.enumeration.ParaBirimi;
/**
 * Test class for the DovizKurResource REST controller.
 *
 * @see DovizKurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class DovizKurResourceIntTest {

    private static final ParaBirimi DEFAULT_PARA_BIRIMI = ParaBirimi.TL;
    private static final ParaBirimi UPDATED_PARA_BIRIMI = ParaBirimi.USD;

    private static final Double DEFAULT_DEGER = 1D;
    private static final Double UPDATED_DEGER = 2D;

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DovizKurRepository dovizKurRepository;

    @Autowired
    private DovizKurService dovizKurService;

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

    private MockMvc restDovizKurMockMvc;

    private DovizKur dovizKur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DovizKurResource dovizKurResource = new DovizKurResource(dovizKurService);
        this.restDovizKurMockMvc = MockMvcBuilders.standaloneSetup(dovizKurResource)
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
    public static DovizKur createEntity(EntityManager em) {
        DovizKur dovizKur = new DovizKur()
            .paraBirimi(DEFAULT_PARA_BIRIMI)
            .deger(DEFAULT_DEGER)
            .tarih(DEFAULT_TARIH);
        return dovizKur;
    }

    @Before
    public void initTest() {
        dovizKur = createEntity(em);
    }

    @Test
    @Transactional
    public void createDovizKur() throws Exception {
        int databaseSizeBeforeCreate = dovizKurRepository.findAll().size();

        // Create the DovizKur
        restDovizKurMockMvc.perform(post("/api/doviz-kurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dovizKur)))
            .andExpect(status().isCreated());

        // Validate the DovizKur in the database
        List<DovizKur> dovizKurList = dovizKurRepository.findAll();
        assertThat(dovizKurList).hasSize(databaseSizeBeforeCreate + 1);
        DovizKur testDovizKur = dovizKurList.get(dovizKurList.size() - 1);
        assertThat(testDovizKur.getParaBirimi()).isEqualTo(DEFAULT_PARA_BIRIMI);
        assertThat(testDovizKur.getDeger()).isEqualTo(DEFAULT_DEGER);
        assertThat(testDovizKur.getTarih()).isEqualTo(DEFAULT_TARIH);
    }

    @Test
    @Transactional
    public void createDovizKurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dovizKurRepository.findAll().size();

        // Create the DovizKur with an existing ID
        dovizKur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDovizKurMockMvc.perform(post("/api/doviz-kurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dovizKur)))
            .andExpect(status().isBadRequest());

        // Validate the DovizKur in the database
        List<DovizKur> dovizKurList = dovizKurRepository.findAll();
        assertThat(dovizKurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDovizKurs() throws Exception {
        // Initialize the database
        dovizKurRepository.saveAndFlush(dovizKur);

        // Get all the dovizKurList
        restDovizKurMockMvc.perform(get("/api/doviz-kurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dovizKur.getId().intValue())))
            .andExpect(jsonPath("$.[*].paraBirimi").value(hasItem(DEFAULT_PARA_BIRIMI.toString())))
            .andExpect(jsonPath("$.[*].deger").value(hasItem(DEFAULT_DEGER.doubleValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())));
    }
    
    @Test
    @Transactional
    public void getDovizKur() throws Exception {
        // Initialize the database
        dovizKurRepository.saveAndFlush(dovizKur);

        // Get the dovizKur
        restDovizKurMockMvc.perform(get("/api/doviz-kurs/{id}", dovizKur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dovizKur.getId().intValue()))
            .andExpect(jsonPath("$.paraBirimi").value(DEFAULT_PARA_BIRIMI.toString()))
            .andExpect(jsonPath("$.deger").value(DEFAULT_DEGER.doubleValue()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDovizKur() throws Exception {
        // Get the dovizKur
        restDovizKurMockMvc.perform(get("/api/doviz-kurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDovizKur() throws Exception {
        // Initialize the database
        dovizKurService.save(dovizKur);

        int databaseSizeBeforeUpdate = dovizKurRepository.findAll().size();

        // Update the dovizKur
        DovizKur updatedDovizKur = dovizKurRepository.findById(dovizKur.getId()).get();
        // Disconnect from session so that the updates on updatedDovizKur are not directly saved in db
        em.detach(updatedDovizKur);
        updatedDovizKur
            .paraBirimi(UPDATED_PARA_BIRIMI)
            .deger(UPDATED_DEGER)
            .tarih(UPDATED_TARIH);

        restDovizKurMockMvc.perform(put("/api/doviz-kurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDovizKur)))
            .andExpect(status().isOk());

        // Validate the DovizKur in the database
        List<DovizKur> dovizKurList = dovizKurRepository.findAll();
        assertThat(dovizKurList).hasSize(databaseSizeBeforeUpdate);
        DovizKur testDovizKur = dovizKurList.get(dovizKurList.size() - 1);
        assertThat(testDovizKur.getParaBirimi()).isEqualTo(UPDATED_PARA_BIRIMI);
        assertThat(testDovizKur.getDeger()).isEqualTo(UPDATED_DEGER);
        assertThat(testDovizKur.getTarih()).isEqualTo(UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void updateNonExistingDovizKur() throws Exception {
        int databaseSizeBeforeUpdate = dovizKurRepository.findAll().size();

        // Create the DovizKur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDovizKurMockMvc.perform(put("/api/doviz-kurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dovizKur)))
            .andExpect(status().isBadRequest());

        // Validate the DovizKur in the database
        List<DovizKur> dovizKurList = dovizKurRepository.findAll();
        assertThat(dovizKurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDovizKur() throws Exception {
        // Initialize the database
        dovizKurService.save(dovizKur);

        int databaseSizeBeforeDelete = dovizKurRepository.findAll().size();

        // Delete the dovizKur
        restDovizKurMockMvc.perform(delete("/api/doviz-kurs/{id}", dovizKur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DovizKur> dovizKurList = dovizKurRepository.findAll();
        assertThat(dovizKurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DovizKur.class);
        DovizKur dovizKur1 = new DovizKur();
        dovizKur1.setId(1L);
        DovizKur dovizKur2 = new DovizKur();
        dovizKur2.setId(dovizKur1.getId());
        assertThat(dovizKur1).isEqualTo(dovizKur2);
        dovizKur2.setId(2L);
        assertThat(dovizKur1).isNotEqualTo(dovizKur2);
        dovizKur1.setId(null);
        assertThat(dovizKur1).isNotEqualTo(dovizKur2);
    }
}
