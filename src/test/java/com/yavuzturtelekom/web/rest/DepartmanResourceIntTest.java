package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Departman;
import com.yavuzturtelekom.repository.DepartmanRepository;
import com.yavuzturtelekom.service.DepartmanService;
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
 * Test class for the DepartmanResource REST controller.
 *
 * @see DepartmanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class DepartmanResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    @Autowired
    private DepartmanRepository departmanRepository;

    @Autowired
    private DepartmanService departmanService;

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

    private MockMvc restDepartmanMockMvc;

    private Departman departman;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartmanResource departmanResource = new DepartmanResource(departmanService);
        this.restDepartmanMockMvc = MockMvcBuilders.standaloneSetup(departmanResource)
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
    public static Departman createEntity(EntityManager em) {
        Departman departman = new Departman()
            .ad(DEFAULT_AD);
        return departman;
    }

    @Before
    public void initTest() {
        departman = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartman() throws Exception {
        int databaseSizeBeforeCreate = departmanRepository.findAll().size();

        // Create the Departman
        restDepartmanMockMvc.perform(post("/api/departmen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departman)))
            .andExpect(status().isCreated());

        // Validate the Departman in the database
        List<Departman> departmanList = departmanRepository.findAll();
        assertThat(departmanList).hasSize(databaseSizeBeforeCreate + 1);
        Departman testDepartman = departmanList.get(departmanList.size() - 1);
        assertThat(testDepartman.getAd()).isEqualTo(DEFAULT_AD);
    }

    @Test
    @Transactional
    public void createDepartmanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmanRepository.findAll().size();

        // Create the Departman with an existing ID
        departman.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmanMockMvc.perform(post("/api/departmen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departman)))
            .andExpect(status().isBadRequest());

        // Validate the Departman in the database
        List<Departman> departmanList = departmanRepository.findAll();
        assertThat(departmanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = departmanRepository.findAll().size();
        // set the field null
        departman.setAd(null);

        // Create the Departman, which fails.

        restDepartmanMockMvc.perform(post("/api/departmen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departman)))
            .andExpect(status().isBadRequest());

        List<Departman> departmanList = departmanRepository.findAll();
        assertThat(departmanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartmen() throws Exception {
        // Initialize the database
        departmanRepository.saveAndFlush(departman);

        // Get all the departmanList
        restDepartmanMockMvc.perform(get("/api/departmen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departman.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())));
    }
    
    @Test
    @Transactional
    public void getDepartman() throws Exception {
        // Initialize the database
        departmanRepository.saveAndFlush(departman);

        // Get the departman
        restDepartmanMockMvc.perform(get("/api/departmen/{id}", departman.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departman.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartman() throws Exception {
        // Get the departman
        restDepartmanMockMvc.perform(get("/api/departmen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartman() throws Exception {
        // Initialize the database
        departmanService.save(departman);

        int databaseSizeBeforeUpdate = departmanRepository.findAll().size();

        // Update the departman
        Departman updatedDepartman = departmanRepository.findById(departman.getId()).get();
        // Disconnect from session so that the updates on updatedDepartman are not directly saved in db
        em.detach(updatedDepartman);
        updatedDepartman
            .ad(UPDATED_AD);

        restDepartmanMockMvc.perform(put("/api/departmen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepartman)))
            .andExpect(status().isOk());

        // Validate the Departman in the database
        List<Departman> departmanList = departmanRepository.findAll();
        assertThat(departmanList).hasSize(databaseSizeBeforeUpdate);
        Departman testDepartman = departmanList.get(departmanList.size() - 1);
        assertThat(testDepartman.getAd()).isEqualTo(UPDATED_AD);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartman() throws Exception {
        int databaseSizeBeforeUpdate = departmanRepository.findAll().size();

        // Create the Departman

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmanMockMvc.perform(put("/api/departmen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departman)))
            .andExpect(status().isBadRequest());

        // Validate the Departman in the database
        List<Departman> departmanList = departmanRepository.findAll();
        assertThat(departmanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartman() throws Exception {
        // Initialize the database
        departmanService.save(departman);

        int databaseSizeBeforeDelete = departmanRepository.findAll().size();

        // Delete the departman
        restDepartmanMockMvc.perform(delete("/api/departmen/{id}", departman.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Departman> departmanList = departmanRepository.findAll();
        assertThat(departmanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Departman.class);
        Departman departman1 = new Departman();
        departman1.setId(1L);
        Departman departman2 = new Departman();
        departman2.setId(departman1.getId());
        assertThat(departman1).isEqualTo(departman2);
        departman2.setId(2L);
        assertThat(departman1).isNotEqualTo(departman2);
        departman1.setId(null);
        assertThat(departman1).isNotEqualTo(departman2);
    }
}
