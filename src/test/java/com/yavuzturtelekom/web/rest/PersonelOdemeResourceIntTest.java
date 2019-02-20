package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.PersonelOdeme;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.repository.PersonelOdemeRepository;
import com.yavuzturtelekom.service.PersonelOdemeService;
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

import com.yavuzturtelekom.domain.enumeration.OdemeTuru;
import com.yavuzturtelekom.domain.enumeration.OdemeYontemi;
/**
 * Test class for the PersonelOdemeResource REST controller.
 *
 * @see PersonelOdemeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class PersonelOdemeResourceIntTest {

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MIKTAR = 1D;
    private static final Double UPDATED_MIKTAR = 2D;

    private static final OdemeTuru DEFAULT_TURU = OdemeTuru.MAAS;
    private static final OdemeTuru UPDATED_TURU = OdemeTuru.AVANS;

    private static final OdemeYontemi DEFAULT_YONTEM = OdemeYontemi.NAKIT;
    private static final OdemeYontemi UPDATED_YONTEM = OdemeYontemi.BANKA;

    @Autowired
    private PersonelOdemeRepository personelOdemeRepository;

    @Autowired
    private PersonelOdemeService personelOdemeService;

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

    private MockMvc restPersonelOdemeMockMvc;

    private PersonelOdeme personelOdeme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonelOdemeResource personelOdemeResource = new PersonelOdemeResource(personelOdemeService);
        this.restPersonelOdemeMockMvc = MockMvcBuilders.standaloneSetup(personelOdemeResource)
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
    public static PersonelOdeme createEntity(EntityManager em) {
        PersonelOdeme personelOdeme = new PersonelOdeme()
            .tarih(DEFAULT_TARIH)
            .miktar(DEFAULT_MIKTAR)
            .turu(DEFAULT_TURU)
            .yontem(DEFAULT_YONTEM);
        // Add required entity
        Personel personel = PersonelResourceIntTest.createEntity(em);
        em.persist(personel);
        em.flush();
        personelOdeme.setPersonel(personel);
        return personelOdeme;
    }

    @Before
    public void initTest() {
        personelOdeme = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonelOdeme() throws Exception {
        int databaseSizeBeforeCreate = personelOdemeRepository.findAll().size();

        // Create the PersonelOdeme
        restPersonelOdemeMockMvc.perform(post("/api/personel-odemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelOdeme)))
            .andExpect(status().isCreated());

        // Validate the PersonelOdeme in the database
        List<PersonelOdeme> personelOdemeList = personelOdemeRepository.findAll();
        assertThat(personelOdemeList).hasSize(databaseSizeBeforeCreate + 1);
        PersonelOdeme testPersonelOdeme = personelOdemeList.get(personelOdemeList.size() - 1);
        assertThat(testPersonelOdeme.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testPersonelOdeme.getMiktar()).isEqualTo(DEFAULT_MIKTAR);
        assertThat(testPersonelOdeme.getTuru()).isEqualTo(DEFAULT_TURU);
        assertThat(testPersonelOdeme.getYontem()).isEqualTo(DEFAULT_YONTEM);
    }

    @Test
    @Transactional
    public void createPersonelOdemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personelOdemeRepository.findAll().size();

        // Create the PersonelOdeme with an existing ID
        personelOdeme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonelOdemeMockMvc.perform(post("/api/personel-odemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelOdeme)))
            .andExpect(status().isBadRequest());

        // Validate the PersonelOdeme in the database
        List<PersonelOdeme> personelOdemeList = personelOdemeRepository.findAll();
        assertThat(personelOdemeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonelOdemes() throws Exception {
        // Initialize the database
        personelOdemeRepository.saveAndFlush(personelOdeme);

        // Get all the personelOdemeList
        restPersonelOdemeMockMvc.perform(get("/api/personel-odemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personelOdeme.getId().intValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].miktar").value(hasItem(DEFAULT_MIKTAR.doubleValue())))
            .andExpect(jsonPath("$.[*].turu").value(hasItem(DEFAULT_TURU.toString())))
            .andExpect(jsonPath("$.[*].yontem").value(hasItem(DEFAULT_YONTEM.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonelOdeme() throws Exception {
        // Initialize the database
        personelOdemeRepository.saveAndFlush(personelOdeme);

        // Get the personelOdeme
        restPersonelOdemeMockMvc.perform(get("/api/personel-odemes/{id}", personelOdeme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personelOdeme.getId().intValue()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.miktar").value(DEFAULT_MIKTAR.doubleValue()))
            .andExpect(jsonPath("$.turu").value(DEFAULT_TURU.toString()))
            .andExpect(jsonPath("$.yontem").value(DEFAULT_YONTEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonelOdeme() throws Exception {
        // Get the personelOdeme
        restPersonelOdemeMockMvc.perform(get("/api/personel-odemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonelOdeme() throws Exception {
        // Initialize the database
        personelOdemeService.save(personelOdeme);

        int databaseSizeBeforeUpdate = personelOdemeRepository.findAll().size();

        // Update the personelOdeme
        PersonelOdeme updatedPersonelOdeme = personelOdemeRepository.findById(personelOdeme.getId()).get();
        // Disconnect from session so that the updates on updatedPersonelOdeme are not directly saved in db
        em.detach(updatedPersonelOdeme);
        updatedPersonelOdeme
            .tarih(UPDATED_TARIH)
            .miktar(UPDATED_MIKTAR)
            .turu(UPDATED_TURU)
            .yontem(UPDATED_YONTEM);

        restPersonelOdemeMockMvc.perform(put("/api/personel-odemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonelOdeme)))
            .andExpect(status().isOk());

        // Validate the PersonelOdeme in the database
        List<PersonelOdeme> personelOdemeList = personelOdemeRepository.findAll();
        assertThat(personelOdemeList).hasSize(databaseSizeBeforeUpdate);
        PersonelOdeme testPersonelOdeme = personelOdemeList.get(personelOdemeList.size() - 1);
        assertThat(testPersonelOdeme.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testPersonelOdeme.getMiktar()).isEqualTo(UPDATED_MIKTAR);
        assertThat(testPersonelOdeme.getTuru()).isEqualTo(UPDATED_TURU);
        assertThat(testPersonelOdeme.getYontem()).isEqualTo(UPDATED_YONTEM);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonelOdeme() throws Exception {
        int databaseSizeBeforeUpdate = personelOdemeRepository.findAll().size();

        // Create the PersonelOdeme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonelOdemeMockMvc.perform(put("/api/personel-odemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelOdeme)))
            .andExpect(status().isBadRequest());

        // Validate the PersonelOdeme in the database
        List<PersonelOdeme> personelOdemeList = personelOdemeRepository.findAll();
        assertThat(personelOdemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonelOdeme() throws Exception {
        // Initialize the database
        personelOdemeService.save(personelOdeme);

        int databaseSizeBeforeDelete = personelOdemeRepository.findAll().size();

        // Delete the personelOdeme
        restPersonelOdemeMockMvc.perform(delete("/api/personel-odemes/{id}", personelOdeme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonelOdeme> personelOdemeList = personelOdemeRepository.findAll();
        assertThat(personelOdemeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonelOdeme.class);
        PersonelOdeme personelOdeme1 = new PersonelOdeme();
        personelOdeme1.setId(1L);
        PersonelOdeme personelOdeme2 = new PersonelOdeme();
        personelOdeme2.setId(personelOdeme1.getId());
        assertThat(personelOdeme1).isEqualTo(personelOdeme2);
        personelOdeme2.setId(2L);
        assertThat(personelOdeme1).isNotEqualTo(personelOdeme2);
        personelOdeme1.setId(null);
        assertThat(personelOdeme1).isNotEqualTo(personelOdeme2);
    }
}
