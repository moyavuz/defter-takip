package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Mudurluk;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.domain.Il;
import com.yavuzturtelekom.repository.MudurlukRepository;
import com.yavuzturtelekom.service.MudurlukService;
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
 * Test class for the MudurlukResource REST controller.
 *
 * @see MudurlukResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class MudurlukResourceIntTest {

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    @Autowired
    private MudurlukRepository mudurlukRepository;

    @Autowired
    private MudurlukService mudurlukService;

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

    private MockMvc restMudurlukMockMvc;

    private Mudurluk mudurluk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MudurlukResource mudurlukResource = new MudurlukResource(mudurlukService);
        this.restMudurlukMockMvc = MockMvcBuilders.standaloneSetup(mudurlukResource)
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
    public static Mudurluk createEntity(EntityManager em) {
        Mudurluk mudurluk = new Mudurluk()
            .ad(DEFAULT_AD)
            .adres(DEFAULT_ADRES);
        // Add required entity
        Personel personel = PersonelResourceIntTest.createEntity(em);
        em.persist(personel);
        em.flush();
        mudurluk.setMudurlukSorumlu(personel);
        // Add required entity
        Il il = IlResourceIntTest.createEntity(em);
        em.persist(il);
        em.flush();
        mudurluk.setIl(il);
        return mudurluk;
    }

    @Before
    public void initTest() {
        mudurluk = createEntity(em);
    }

    @Test
    @Transactional
    public void createMudurluk() throws Exception {
        int databaseSizeBeforeCreate = mudurlukRepository.findAll().size();

        // Create the Mudurluk
        restMudurlukMockMvc.perform(post("/api/mudurluks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mudurluk)))
            .andExpect(status().isCreated());

        // Validate the Mudurluk in the database
        List<Mudurluk> mudurlukList = mudurlukRepository.findAll();
        assertThat(mudurlukList).hasSize(databaseSizeBeforeCreate + 1);
        Mudurluk testMudurluk = mudurlukList.get(mudurlukList.size() - 1);
        assertThat(testMudurluk.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testMudurluk.getAdres()).isEqualTo(DEFAULT_ADRES);
    }

    @Test
    @Transactional
    public void createMudurlukWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mudurlukRepository.findAll().size();

        // Create the Mudurluk with an existing ID
        mudurluk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMudurlukMockMvc.perform(post("/api/mudurluks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mudurluk)))
            .andExpect(status().isBadRequest());

        // Validate the Mudurluk in the database
        List<Mudurluk> mudurlukList = mudurlukRepository.findAll();
        assertThat(mudurlukList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = mudurlukRepository.findAll().size();
        // set the field null
        mudurluk.setAd(null);

        // Create the Mudurluk, which fails.

        restMudurlukMockMvc.perform(post("/api/mudurluks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mudurluk)))
            .andExpect(status().isBadRequest());

        List<Mudurluk> mudurlukList = mudurlukRepository.findAll();
        assertThat(mudurlukList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMudurluks() throws Exception {
        // Initialize the database
        mudurlukRepository.saveAndFlush(mudurluk);

        // Get all the mudurlukList
        restMudurlukMockMvc.perform(get("/api/mudurluks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mudurluk.getId().intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES.toString())));
    }
    
    @Test
    @Transactional
    public void getMudurluk() throws Exception {
        // Initialize the database
        mudurlukRepository.saveAndFlush(mudurluk);

        // Get the mudurluk
        restMudurlukMockMvc.perform(get("/api/mudurluks/{id}", mudurluk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mudurluk.getId().intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMudurluk() throws Exception {
        // Get the mudurluk
        restMudurlukMockMvc.perform(get("/api/mudurluks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMudurluk() throws Exception {
        // Initialize the database
        mudurlukService.save(mudurluk);

        int databaseSizeBeforeUpdate = mudurlukRepository.findAll().size();

        // Update the mudurluk
        Mudurluk updatedMudurluk = mudurlukRepository.findById(mudurluk.getId()).get();
        // Disconnect from session so that the updates on updatedMudurluk are not directly saved in db
        em.detach(updatedMudurluk);
        updatedMudurluk
            .ad(UPDATED_AD)
            .adres(UPDATED_ADRES);

        restMudurlukMockMvc.perform(put("/api/mudurluks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMudurluk)))
            .andExpect(status().isOk());

        // Validate the Mudurluk in the database
        List<Mudurluk> mudurlukList = mudurlukRepository.findAll();
        assertThat(mudurlukList).hasSize(databaseSizeBeforeUpdate);
        Mudurluk testMudurluk = mudurlukList.get(mudurlukList.size() - 1);
        assertThat(testMudurluk.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testMudurluk.getAdres()).isEqualTo(UPDATED_ADRES);
    }

    @Test
    @Transactional
    public void updateNonExistingMudurluk() throws Exception {
        int databaseSizeBeforeUpdate = mudurlukRepository.findAll().size();

        // Create the Mudurluk

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMudurlukMockMvc.perform(put("/api/mudurluks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mudurluk)))
            .andExpect(status().isBadRequest());

        // Validate the Mudurluk in the database
        List<Mudurluk> mudurlukList = mudurlukRepository.findAll();
        assertThat(mudurlukList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMudurluk() throws Exception {
        // Initialize the database
        mudurlukService.save(mudurluk);

        int databaseSizeBeforeDelete = mudurlukRepository.findAll().size();

        // Delete the mudurluk
        restMudurlukMockMvc.perform(delete("/api/mudurluks/{id}", mudurluk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mudurluk> mudurlukList = mudurlukRepository.findAll();
        assertThat(mudurlukList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mudurluk.class);
        Mudurluk mudurluk1 = new Mudurluk();
        mudurluk1.setId(1L);
        Mudurluk mudurluk2 = new Mudurluk();
        mudurluk2.setId(mudurluk1.getId());
        assertThat(mudurluk1).isEqualTo(mudurluk2);
        mudurluk2.setId(2L);
        assertThat(mudurluk1).isNotEqualTo(mudurluk2);
        mudurluk1.setId(null);
        assertThat(mudurluk1).isNotEqualTo(mudurluk2);
    }
}
