package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.IscilikDetay;
import com.yavuzturtelekom.repository.IscilikDetayRepository;
import com.yavuzturtelekom.service.IscilikDetayService;
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
 * Test class for the IscilikDetayResource REST controller.
 *
 * @see IscilikDetayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class IscilikDetayResourceIntTest {

    private static final Long DEFAULT_MIKTAR = 1L;
    private static final Long UPDATED_MIKTAR = 2L;

    @Autowired
    private IscilikDetayRepository iscilikDetayRepository;

    @Autowired
    private IscilikDetayService iscilikDetayService;

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

    private MockMvc restIscilikDetayMockMvc;

    private IscilikDetay iscilikDetay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IscilikDetayResource iscilikDetayResource = new IscilikDetayResource(iscilikDetayService);
        this.restIscilikDetayMockMvc = MockMvcBuilders.standaloneSetup(iscilikDetayResource)
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
    public static IscilikDetay createEntity(EntityManager em) {
        IscilikDetay iscilikDetay = new IscilikDetay()
            .miktar(DEFAULT_MIKTAR);
        return iscilikDetay;
    }

    @Before
    public void initTest() {
        iscilikDetay = createEntity(em);
    }

    @Test
    @Transactional
    public void createIscilikDetay() throws Exception {
        int databaseSizeBeforeCreate = iscilikDetayRepository.findAll().size();

        // Create the IscilikDetay
        restIscilikDetayMockMvc.perform(post("/api/iscilik-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iscilikDetay)))
            .andExpect(status().isCreated());

        // Validate the IscilikDetay in the database
        List<IscilikDetay> iscilikDetayList = iscilikDetayRepository.findAll();
        assertThat(iscilikDetayList).hasSize(databaseSizeBeforeCreate + 1);
        IscilikDetay testIscilikDetay = iscilikDetayList.get(iscilikDetayList.size() - 1);
        assertThat(testIscilikDetay.getMiktar()).isEqualTo(DEFAULT_MIKTAR);
    }

    @Test
    @Transactional
    public void createIscilikDetayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iscilikDetayRepository.findAll().size();

        // Create the IscilikDetay with an existing ID
        iscilikDetay.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIscilikDetayMockMvc.perform(post("/api/iscilik-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iscilikDetay)))
            .andExpect(status().isBadRequest());

        // Validate the IscilikDetay in the database
        List<IscilikDetay> iscilikDetayList = iscilikDetayRepository.findAll();
        assertThat(iscilikDetayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMiktarIsRequired() throws Exception {
        int databaseSizeBeforeTest = iscilikDetayRepository.findAll().size();
        // set the field null
        iscilikDetay.setMiktar(null);

        // Create the IscilikDetay, which fails.

        restIscilikDetayMockMvc.perform(post("/api/iscilik-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iscilikDetay)))
            .andExpect(status().isBadRequest());

        List<IscilikDetay> iscilikDetayList = iscilikDetayRepository.findAll();
        assertThat(iscilikDetayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIscilikDetays() throws Exception {
        // Initialize the database
        iscilikDetayRepository.saveAndFlush(iscilikDetay);

        // Get all the iscilikDetayList
        restIscilikDetayMockMvc.perform(get("/api/iscilik-detays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iscilikDetay.getId().intValue())))
            .andExpect(jsonPath("$.[*].miktar").value(hasItem(DEFAULT_MIKTAR.intValue())));
    }
    
    @Test
    @Transactional
    public void getIscilikDetay() throws Exception {
        // Initialize the database
        iscilikDetayRepository.saveAndFlush(iscilikDetay);

        // Get the iscilikDetay
        restIscilikDetayMockMvc.perform(get("/api/iscilik-detays/{id}", iscilikDetay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iscilikDetay.getId().intValue()))
            .andExpect(jsonPath("$.miktar").value(DEFAULT_MIKTAR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIscilikDetay() throws Exception {
        // Get the iscilikDetay
        restIscilikDetayMockMvc.perform(get("/api/iscilik-detays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIscilikDetay() throws Exception {
        // Initialize the database
        iscilikDetayService.save(iscilikDetay);

        int databaseSizeBeforeUpdate = iscilikDetayRepository.findAll().size();

        // Update the iscilikDetay
        IscilikDetay updatedIscilikDetay = iscilikDetayRepository.findById(iscilikDetay.getId()).get();
        // Disconnect from session so that the updates on updatedIscilikDetay are not directly saved in db
        em.detach(updatedIscilikDetay);
        updatedIscilikDetay
            .miktar(UPDATED_MIKTAR);

        restIscilikDetayMockMvc.perform(put("/api/iscilik-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIscilikDetay)))
            .andExpect(status().isOk());

        // Validate the IscilikDetay in the database
        List<IscilikDetay> iscilikDetayList = iscilikDetayRepository.findAll();
        assertThat(iscilikDetayList).hasSize(databaseSizeBeforeUpdate);
        IscilikDetay testIscilikDetay = iscilikDetayList.get(iscilikDetayList.size() - 1);
        assertThat(testIscilikDetay.getMiktar()).isEqualTo(UPDATED_MIKTAR);
    }

    @Test
    @Transactional
    public void updateNonExistingIscilikDetay() throws Exception {
        int databaseSizeBeforeUpdate = iscilikDetayRepository.findAll().size();

        // Create the IscilikDetay

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIscilikDetayMockMvc.perform(put("/api/iscilik-detays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iscilikDetay)))
            .andExpect(status().isBadRequest());

        // Validate the IscilikDetay in the database
        List<IscilikDetay> iscilikDetayList = iscilikDetayRepository.findAll();
        assertThat(iscilikDetayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIscilikDetay() throws Exception {
        // Initialize the database
        iscilikDetayService.save(iscilikDetay);

        int databaseSizeBeforeDelete = iscilikDetayRepository.findAll().size();

        // Delete the iscilikDetay
        restIscilikDetayMockMvc.perform(delete("/api/iscilik-detays/{id}", iscilikDetay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IscilikDetay> iscilikDetayList = iscilikDetayRepository.findAll();
        assertThat(iscilikDetayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IscilikDetay.class);
        IscilikDetay iscilikDetay1 = new IscilikDetay();
        iscilikDetay1.setId(1L);
        IscilikDetay iscilikDetay2 = new IscilikDetay();
        iscilikDetay2.setId(iscilikDetay1.getId());
        assertThat(iscilikDetay1).isEqualTo(iscilikDetay2);
        iscilikDetay2.setId(2L);
        assertThat(iscilikDetay1).isNotEqualTo(iscilikDetay2);
        iscilikDetay1.setId(null);
        assertThat(iscilikDetay1).isNotEqualTo(iscilikDetay2);
    }
}
