package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.PersonelZimmet;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.domain.ZimmetTuru;
import com.yavuzturtelekom.repository.PersonelZimmetRepository;
import com.yavuzturtelekom.service.PersonelZimmetService;
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

import com.yavuzturtelekom.domain.enumeration.ZimmetDurumu;
/**
 * Test class for the PersonelZimmetResource REST controller.
 *
 * @see PersonelZimmetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class PersonelZimmetResourceIntTest {

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final ZimmetDurumu DEFAULT_DURUMU = ZimmetDurumu.YENI;
    private static final ZimmetDurumu UPDATED_DURUMU = ZimmetDurumu.KULLANILMIS;

    private static final byte[] DEFAULT_RESIM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESIM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESIM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESIM_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOSYA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYA_CONTENT_TYPE = "image/png";

    @Autowired
    private PersonelZimmetRepository personelZimmetRepository;

    @Autowired
    private PersonelZimmetService personelZimmetService;

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

    private MockMvc restPersonelZimmetMockMvc;

    private PersonelZimmet personelZimmet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonelZimmetResource personelZimmetResource = new PersonelZimmetResource(personelZimmetService);
        this.restPersonelZimmetMockMvc = MockMvcBuilders.standaloneSetup(personelZimmetResource)
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
    public static PersonelZimmet createEntity(EntityManager em) {
        PersonelZimmet personelZimmet = new PersonelZimmet()
            .tarih(DEFAULT_TARIH)
            .durumu(DEFAULT_DURUMU)
            .resim(DEFAULT_RESIM)
            .resimContentType(DEFAULT_RESIM_CONTENT_TYPE)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE);
        // Add required entity
        Personel personel = PersonelResourceIntTest.createEntity(em);
        em.persist(personel);
        em.flush();
        personelZimmet.setPersonel(personel);
        // Add required entity
        ZimmetTuru zimmetTuru = ZimmetTuruResourceIntTest.createEntity(em);
        em.persist(zimmetTuru);
        em.flush();
        personelZimmet.setZimmet(zimmetTuru);
        return personelZimmet;
    }

    @Before
    public void initTest() {
        personelZimmet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonelZimmet() throws Exception {
        int databaseSizeBeforeCreate = personelZimmetRepository.findAll().size();

        // Create the PersonelZimmet
        restPersonelZimmetMockMvc.perform(post("/api/personel-zimmets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelZimmet)))
            .andExpect(status().isCreated());

        // Validate the PersonelZimmet in the database
        List<PersonelZimmet> personelZimmetList = personelZimmetRepository.findAll();
        assertThat(personelZimmetList).hasSize(databaseSizeBeforeCreate + 1);
        PersonelZimmet testPersonelZimmet = personelZimmetList.get(personelZimmetList.size() - 1);
        assertThat(testPersonelZimmet.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testPersonelZimmet.getDurumu()).isEqualTo(DEFAULT_DURUMU);
        assertThat(testPersonelZimmet.getResim()).isEqualTo(DEFAULT_RESIM);
        assertThat(testPersonelZimmet.getResimContentType()).isEqualTo(DEFAULT_RESIM_CONTENT_TYPE);
        assertThat(testPersonelZimmet.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testPersonelZimmet.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPersonelZimmetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personelZimmetRepository.findAll().size();

        // Create the PersonelZimmet with an existing ID
        personelZimmet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonelZimmetMockMvc.perform(post("/api/personel-zimmets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelZimmet)))
            .andExpect(status().isBadRequest());

        // Validate the PersonelZimmet in the database
        List<PersonelZimmet> personelZimmetList = personelZimmetRepository.findAll();
        assertThat(personelZimmetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonelZimmets() throws Exception {
        // Initialize the database
        personelZimmetRepository.saveAndFlush(personelZimmet);

        // Get all the personelZimmetList
        restPersonelZimmetMockMvc.perform(get("/api/personel-zimmets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personelZimmet.getId().intValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].durumu").value(hasItem(DEFAULT_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))));
    }
    
    @Test
    @Transactional
    public void getPersonelZimmet() throws Exception {
        // Initialize the database
        personelZimmetRepository.saveAndFlush(personelZimmet);

        // Get the personelZimmet
        restPersonelZimmetMockMvc.perform(get("/api/personel-zimmets/{id}", personelZimmet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personelZimmet.getId().intValue()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.durumu").value(DEFAULT_DURUMU.toString()))
            .andExpect(jsonPath("$.resimContentType").value(DEFAULT_RESIM_CONTENT_TYPE))
            .andExpect(jsonPath("$.resim").value(Base64Utils.encodeToString(DEFAULT_RESIM)))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)));
    }

    @Test
    @Transactional
    public void getNonExistingPersonelZimmet() throws Exception {
        // Get the personelZimmet
        restPersonelZimmetMockMvc.perform(get("/api/personel-zimmets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonelZimmet() throws Exception {
        // Initialize the database
        personelZimmetService.save(personelZimmet);

        int databaseSizeBeforeUpdate = personelZimmetRepository.findAll().size();

        // Update the personelZimmet
        PersonelZimmet updatedPersonelZimmet = personelZimmetRepository.findById(personelZimmet.getId()).get();
        // Disconnect from session so that the updates on updatedPersonelZimmet are not directly saved in db
        em.detach(updatedPersonelZimmet);
        updatedPersonelZimmet
            .tarih(UPDATED_TARIH)
            .durumu(UPDATED_DURUMU)
            .resim(UPDATED_RESIM)
            .resimContentType(UPDATED_RESIM_CONTENT_TYPE)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE);

        restPersonelZimmetMockMvc.perform(put("/api/personel-zimmets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonelZimmet)))
            .andExpect(status().isOk());

        // Validate the PersonelZimmet in the database
        List<PersonelZimmet> personelZimmetList = personelZimmetRepository.findAll();
        assertThat(personelZimmetList).hasSize(databaseSizeBeforeUpdate);
        PersonelZimmet testPersonelZimmet = personelZimmetList.get(personelZimmetList.size() - 1);
        assertThat(testPersonelZimmet.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testPersonelZimmet.getDurumu()).isEqualTo(UPDATED_DURUMU);
        assertThat(testPersonelZimmet.getResim()).isEqualTo(UPDATED_RESIM);
        assertThat(testPersonelZimmet.getResimContentType()).isEqualTo(UPDATED_RESIM_CONTENT_TYPE);
        assertThat(testPersonelZimmet.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testPersonelZimmet.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonelZimmet() throws Exception {
        int databaseSizeBeforeUpdate = personelZimmetRepository.findAll().size();

        // Create the PersonelZimmet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonelZimmetMockMvc.perform(put("/api/personel-zimmets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelZimmet)))
            .andExpect(status().isBadRequest());

        // Validate the PersonelZimmet in the database
        List<PersonelZimmet> personelZimmetList = personelZimmetRepository.findAll();
        assertThat(personelZimmetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonelZimmet() throws Exception {
        // Initialize the database
        personelZimmetService.save(personelZimmet);

        int databaseSizeBeforeDelete = personelZimmetRepository.findAll().size();

        // Delete the personelZimmet
        restPersonelZimmetMockMvc.perform(delete("/api/personel-zimmets/{id}", personelZimmet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonelZimmet> personelZimmetList = personelZimmetRepository.findAll();
        assertThat(personelZimmetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonelZimmet.class);
        PersonelZimmet personelZimmet1 = new PersonelZimmet();
        personelZimmet1.setId(1L);
        PersonelZimmet personelZimmet2 = new PersonelZimmet();
        personelZimmet2.setId(personelZimmet1.getId());
        assertThat(personelZimmet1).isEqualTo(personelZimmet2);
        personelZimmet2.setId(2L);
        assertThat(personelZimmet1).isNotEqualTo(personelZimmet2);
        personelZimmet1.setId(null);
        assertThat(personelZimmet1).isNotEqualTo(personelZimmet2);
    }
}
