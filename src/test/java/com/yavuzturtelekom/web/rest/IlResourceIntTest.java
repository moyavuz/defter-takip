package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Il;
import com.yavuzturtelekom.repository.IlRepository;
import com.yavuzturtelekom.service.IlService;
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
 * Test class for the IlResource REST controller.
 *
 * @see IlResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class IlResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    @Autowired
    private IlRepository ilRepository;

    @Autowired
    private IlService ilService;

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

    private MockMvc restIlMockMvc;

    private Il il;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IlResource ilResource = new IlResource(ilService);
        this.restIlMockMvc = MockMvcBuilders.standaloneSetup(ilResource)
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
    public static Il createEntity(EntityManager em) {
        Il il = new Il()
            .ad(DEFAULT_AD);
        return il;
    }

    @Before
    public void initTest() {
        il = createEntity(em);
    }

    @Test
    @Transactional
    public void createIl() throws Exception {
        int databaseSizeBeforeCreate = ilRepository.findAll().size();

        // Create the Il
        restIlMockMvc.perform(post("/api/ils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(il)))
            .andExpect(status().isCreated());

        // Validate the Il in the database
        List<Il> ilList = ilRepository.findAll();
        assertThat(ilList).hasSize(databaseSizeBeforeCreate + 1);
        Il testIl = ilList.get(ilList.size() - 1);
        assertThat(testIl.getAd()).isEqualTo(DEFAULT_AD);
    }

    @Test
    @Transactional
    public void createIlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ilRepository.findAll().size();

        // Create the Il with an existing ID
        il.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIlMockMvc.perform(post("/api/ils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(il)))
            .andExpect(status().isBadRequest());

        // Validate the Il in the database
        List<Il> ilList = ilRepository.findAll();
        assertThat(ilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ilRepository.findAll().size();
        // set the field null
        il.setAd(null);

        // Create the Il, which fails.

        restIlMockMvc.perform(post("/api/ils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(il)))
            .andExpect(status().isBadRequest());

        List<Il> ilList = ilRepository.findAll();
        assertThat(ilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIls() throws Exception {
        // Initialize the database
        ilRepository.saveAndFlush(il);

        // Get all the ilList
        restIlMockMvc.perform(get("/api/ils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(il.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())));
    }
    
    @Test
    @Transactional
    public void getIl() throws Exception {
        // Initialize the database
        ilRepository.saveAndFlush(il);

        // Get the il
        restIlMockMvc.perform(get("/api/ils/{id}", il.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(il.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIl() throws Exception {
        // Get the il
        restIlMockMvc.perform(get("/api/ils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIl() throws Exception {
        // Initialize the database
        ilService.save(il);

        int databaseSizeBeforeUpdate = ilRepository.findAll().size();

        // Update the il
        Il updatedIl = ilRepository.findById(il.getId()).get();
        // Disconnect from session so that the updates on updatedIl are not directly saved in db
        em.detach(updatedIl);
        updatedIl
            .ad(UPDATED_AD);

        restIlMockMvc.perform(put("/api/ils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIl)))
            .andExpect(status().isOk());

        // Validate the Il in the database
        List<Il> ilList = ilRepository.findAll();
        assertThat(ilList).hasSize(databaseSizeBeforeUpdate);
        Il testIl = ilList.get(ilList.size() - 1);
        assertThat(testIl.getAd()).isEqualTo(UPDATED_AD);
    }

    @Test
    @Transactional
    public void updateNonExistingIl() throws Exception {
        int databaseSizeBeforeUpdate = ilRepository.findAll().size();

        // Create the Il

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIlMockMvc.perform(put("/api/ils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(il)))
            .andExpect(status().isBadRequest());

        // Validate the Il in the database
        List<Il> ilList = ilRepository.findAll();
        assertThat(ilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIl() throws Exception {
        // Initialize the database
        ilService.save(il);

        int databaseSizeBeforeDelete = ilRepository.findAll().size();

        // Delete the il
        restIlMockMvc.perform(delete("/api/ils/{id}", il.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Il> ilList = ilRepository.findAll();
        assertThat(ilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Il.class);
        Il il1 = new Il();
        il1.setId(1L);
        Il il2 = new Il();
        il2.setId(il1.getId());
        assertThat(il1).isEqualTo(il2);
        il2.setId(2L);
        assertThat(il1).isNotEqualTo(il2);
        il1.setId(null);
        assertThat(il1).isNotEqualTo(il2);
    }
}
