package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.HakedisDetay;
import com.yavuzturtelekom.domain.Hakedis;
import com.yavuzturtelekom.domain.Poz;
import com.yavuzturtelekom.repository.HakedisDetayRepository;
import com.yavuzturtelekom.service.HakedisDetayService;
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
import java.util.List;


import static com.yavuzturtelekom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HakedisDetayResource REST controller.
 *
 * @see HakedisDetayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class HakedisDetayResourceIntTest {

    private static final Double DEFAULT_MIKTAR = 1D;
    private static final Double UPDATED_MIKTAR = 2D;

    @Autowired
    private HakedisDetayRepository hakedisDetayRepository;

    @Autowired
    private HakedisDetayService hakedisDetayService;

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

    private MockMvc restHakedisDetayMockMvc;

    private HakedisDetay hakedisDetay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HakedisDetayResource hakedisDetayResource = new HakedisDetayResource(hakedisDetayService);
        this.restHakedisDetayMockMvc = MockMvcBuilders.standaloneSetup(hakedisDetayResource)
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
    public static HakedisDetay createEntity(EntityManager em) {
        HakedisDetay hakedisDetay = new HakedisDetay()
            .miktar(DEFAULT_MIKTAR);
        // Add required entity
        Hakedis hakedis = HakedisResourceIntTest.createEntity(em);
        em.persist(hakedis);
        em.flush();
        hakedisDetay.setHakedis(hakedis);
        // Add required entity
        Poz poz = PozResourceIntTest.createEntity(em);
        em.persist(poz);
        em.flush();
        hakedisDetay.setPoz(poz);
        return hakedisDetay;
    }

    @Before
    public void initTest() {
        hakedisDetay = createEntity(em);
    }

    @Test
    @Transactional
    public void createHakedisDetay() throws Exception {
        int databaseSizeBeforeCreate = hakedisDetayRepository.findAll().size();

        // Create the HakedisDetay
        restHakedisDetayMockMvc.perform(post("/api/hakedis-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedisDetay)))
            .andExpect(status().isCreated());

        // Validate the HakedisDetay in the database
        List<HakedisDetay> hakedisDetayList = hakedisDetayRepository.findAll();
        assertThat(hakedisDetayList).hasSize(databaseSizeBeforeCreate + 1);
        HakedisDetay testHakedisDetay = hakedisDetayList.get(hakedisDetayList.size() - 1);
        assertThat(testHakedisDetay.getMiktar()).isEqualTo(DEFAULT_MIKTAR);
    }

    @Test
    @Transactional
    public void createHakedisDetayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hakedisDetayRepository.findAll().size();

        // Create the HakedisDetay with an existing ID
        hakedisDetay.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHakedisDetayMockMvc.perform(post("/api/hakedis-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedisDetay)))
            .andExpect(status().isBadRequest());

        // Validate the HakedisDetay in the database
        List<HakedisDetay> hakedisDetayList = hakedisDetayRepository.findAll();
        assertThat(hakedisDetayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMiktarIsRequired() throws Exception {
        int databaseSizeBeforeTest = hakedisDetayRepository.findAll().size();
        // set the field null
        hakedisDetay.setMiktar(null);

        // Create the HakedisDetay, which fails.

        restHakedisDetayMockMvc.perform(post("/api/hakedis-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedisDetay)))
            .andExpect(status().isBadRequest());

        List<HakedisDetay> hakedisDetayList = hakedisDetayRepository.findAll();
        assertThat(hakedisDetayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHakedisDetays() throws Exception {
        // Initialize the database
        hakedisDetayRepository.saveAndFlush(hakedisDetay);

        // Get all the hakedisDetayList
        restHakedisDetayMockMvc.perform(get("/api/hakedis-detays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hakedisDetay.getId().intValue())))
            .andExpect(jsonPath("$.[*].miktar").value(hasItem(DEFAULT_MIKTAR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getHakedisDetay() throws Exception {
        // Initialize the database
        hakedisDetayRepository.saveAndFlush(hakedisDetay);

        // Get the hakedisDetay
        restHakedisDetayMockMvc.perform(get("/api/hakedis-detays/{id}", hakedisDetay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hakedisDetay.getId().intValue()))
            .andExpect(jsonPath("$.miktar").value(DEFAULT_MIKTAR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHakedisDetay() throws Exception {
        // Get the hakedisDetay
        restHakedisDetayMockMvc.perform(get("/api/hakedis-detays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHakedisDetay() throws Exception {
        // Initialize the database
        hakedisDetayService.save(hakedisDetay);

        int databaseSizeBeforeUpdate = hakedisDetayRepository.findAll().size();

        // Update the hakedisDetay
        HakedisDetay updatedHakedisDetay = hakedisDetayRepository.findById(hakedisDetay.getId()).get();
        // Disconnect from session so that the updates on updatedHakedisDetay are not directly saved in db
        em.detach(updatedHakedisDetay);
        updatedHakedisDetay
            .miktar(UPDATED_MIKTAR);

        restHakedisDetayMockMvc.perform(put("/api/hakedis-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHakedisDetay)))
            .andExpect(status().isOk());

        // Validate the HakedisDetay in the database
        List<HakedisDetay> hakedisDetayList = hakedisDetayRepository.findAll();
        assertThat(hakedisDetayList).hasSize(databaseSizeBeforeUpdate);
        HakedisDetay testHakedisDetay = hakedisDetayList.get(hakedisDetayList.size() - 1);
        assertThat(testHakedisDetay.getMiktar()).isEqualTo(UPDATED_MIKTAR);
    }

    @Test
    @Transactional
    public void updateNonExistingHakedisDetay() throws Exception {
        int databaseSizeBeforeUpdate = hakedisDetayRepository.findAll().size();

        // Create the HakedisDetay

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHakedisDetayMockMvc.perform(put("/api/hakedis-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hakedisDetay)))
            .andExpect(status().isBadRequest());

        // Validate the HakedisDetay in the database
        List<HakedisDetay> hakedisDetayList = hakedisDetayRepository.findAll();
        assertThat(hakedisDetayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHakedisDetay() throws Exception {
        // Initialize the database
        hakedisDetayService.save(hakedisDetay);

        int databaseSizeBeforeDelete = hakedisDetayRepository.findAll().size();

        // Delete the hakedisDetay
        restHakedisDetayMockMvc.perform(delete("/api/hakedis-detays/{id}", hakedisDetay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HakedisDetay> hakedisDetayList = hakedisDetayRepository.findAll();
        assertThat(hakedisDetayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HakedisDetay.class);
        HakedisDetay hakedisDetay1 = new HakedisDetay();
        hakedisDetay1.setId(1L);
        HakedisDetay hakedisDetay2 = new HakedisDetay();
        hakedisDetay2.setId(hakedisDetay1.getId());
        assertThat(hakedisDetay1).isEqualTo(hakedisDetay2);
        hakedisDetay2.setId(2L);
        assertThat(hakedisDetay1).isNotEqualTo(hakedisDetay2);
        hakedisDetay1.setId(null);
        assertThat(hakedisDetay1).isNotEqualTo(hakedisDetay2);
    }
}
