package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.PersonelIzin;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.repository.PersonelIzinRepository;
import com.yavuzturtelekom.service.PersonelIzinService;
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

import com.yavuzturtelekom.domain.enumeration.IzinTuru;
/**
 * Test class for the PersonelIzinResource REST controller.
 *
 * @see PersonelIzinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class PersonelIzinResourceIntTest {

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MIKTAR = 1D;
    private static final Double UPDATED_MIKTAR = 2D;

    private static final IzinTuru DEFAULT_TURU = IzinTuru.YILLIK_IZIN;
    private static final IzinTuru UPDATED_TURU = IzinTuru.UCRETSIZ_IZIN;

    private static final byte[] DEFAULT_DOSYA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYA_CONTENT_TYPE = "image/png";

    @Autowired
    private PersonelIzinRepository personelIzinRepository;

    @Autowired
    private PersonelIzinService personelIzinService;

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

    private MockMvc restPersonelIzinMockMvc;

    private PersonelIzin personelIzin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonelIzinResource personelIzinResource = new PersonelIzinResource(personelIzinService);
        this.restPersonelIzinMockMvc = MockMvcBuilders.standaloneSetup(personelIzinResource)
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
    public static PersonelIzin createEntity(EntityManager em) {
        PersonelIzin personelIzin = new PersonelIzin()
            .tarih(DEFAULT_TARIH)
            .miktar(DEFAULT_MIKTAR)
            .turu(DEFAULT_TURU)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE);
        // Add required entity
        Personel personel = PersonelResourceIntTest.createEntity(em);
        em.persist(personel);
        em.flush();
        personelIzin.setPersonel(personel);
        return personelIzin;
    }

    @Before
    public void initTest() {
        personelIzin = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonelIzin() throws Exception {
        int databaseSizeBeforeCreate = personelIzinRepository.findAll().size();

        // Create the PersonelIzin
        restPersonelIzinMockMvc.perform(post("/api/personel-izins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelIzin)))
            .andExpect(status().isCreated());

        // Validate the PersonelIzin in the database
        List<PersonelIzin> personelIzinList = personelIzinRepository.findAll();
        assertThat(personelIzinList).hasSize(databaseSizeBeforeCreate + 1);
        PersonelIzin testPersonelIzin = personelIzinList.get(personelIzinList.size() - 1);
        assertThat(testPersonelIzin.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testPersonelIzin.getMiktar()).isEqualTo(DEFAULT_MIKTAR);
        assertThat(testPersonelIzin.getTuru()).isEqualTo(DEFAULT_TURU);
        assertThat(testPersonelIzin.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testPersonelIzin.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPersonelIzinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personelIzinRepository.findAll().size();

        // Create the PersonelIzin with an existing ID
        personelIzin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonelIzinMockMvc.perform(post("/api/personel-izins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelIzin)))
            .andExpect(status().isBadRequest());

        // Validate the PersonelIzin in the database
        List<PersonelIzin> personelIzinList = personelIzinRepository.findAll();
        assertThat(personelIzinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonelIzins() throws Exception {
        // Initialize the database
        personelIzinRepository.saveAndFlush(personelIzin);

        // Get all the personelIzinList
        restPersonelIzinMockMvc.perform(get("/api/personel-izins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personelIzin.getId().intValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].miktar").value(hasItem(DEFAULT_MIKTAR.doubleValue())))
            .andExpect(jsonPath("$.[*].turu").value(hasItem(DEFAULT_TURU.toString())))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))));
    }
    
    @Test
    @Transactional
    public void getPersonelIzin() throws Exception {
        // Initialize the database
        personelIzinRepository.saveAndFlush(personelIzin);

        // Get the personelIzin
        restPersonelIzinMockMvc.perform(get("/api/personel-izins/{id}", personelIzin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personelIzin.getId().intValue()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.miktar").value(DEFAULT_MIKTAR.doubleValue()))
            .andExpect(jsonPath("$.turu").value(DEFAULT_TURU.toString()))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)));
    }

    @Test
    @Transactional
    public void getNonExistingPersonelIzin() throws Exception {
        // Get the personelIzin
        restPersonelIzinMockMvc.perform(get("/api/personel-izins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonelIzin() throws Exception {
        // Initialize the database
        personelIzinService.save(personelIzin);

        int databaseSizeBeforeUpdate = personelIzinRepository.findAll().size();

        // Update the personelIzin
        PersonelIzin updatedPersonelIzin = personelIzinRepository.findById(personelIzin.getId()).get();
        // Disconnect from session so that the updates on updatedPersonelIzin are not directly saved in db
        em.detach(updatedPersonelIzin);
        updatedPersonelIzin
            .tarih(UPDATED_TARIH)
            .miktar(UPDATED_MIKTAR)
            .turu(UPDATED_TURU)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE);

        restPersonelIzinMockMvc.perform(put("/api/personel-izins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonelIzin)))
            .andExpect(status().isOk());

        // Validate the PersonelIzin in the database
        List<PersonelIzin> personelIzinList = personelIzinRepository.findAll();
        assertThat(personelIzinList).hasSize(databaseSizeBeforeUpdate);
        PersonelIzin testPersonelIzin = personelIzinList.get(personelIzinList.size() - 1);
        assertThat(testPersonelIzin.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testPersonelIzin.getMiktar()).isEqualTo(UPDATED_MIKTAR);
        assertThat(testPersonelIzin.getTuru()).isEqualTo(UPDATED_TURU);
        assertThat(testPersonelIzin.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testPersonelIzin.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonelIzin() throws Exception {
        int databaseSizeBeforeUpdate = personelIzinRepository.findAll().size();

        // Create the PersonelIzin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonelIzinMockMvc.perform(put("/api/personel-izins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelIzin)))
            .andExpect(status().isBadRequest());

        // Validate the PersonelIzin in the database
        List<PersonelIzin> personelIzinList = personelIzinRepository.findAll();
        assertThat(personelIzinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonelIzin() throws Exception {
        // Initialize the database
        personelIzinService.save(personelIzin);

        int databaseSizeBeforeDelete = personelIzinRepository.findAll().size();

        // Delete the personelIzin
        restPersonelIzinMockMvc.perform(delete("/api/personel-izins/{id}", personelIzin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonelIzin> personelIzinList = personelIzinRepository.findAll();
        assertThat(personelIzinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonelIzin.class);
        PersonelIzin personelIzin1 = new PersonelIzin();
        personelIzin1.setId(1L);
        PersonelIzin personelIzin2 = new PersonelIzin();
        personelIzin2.setId(personelIzin1.getId());
        assertThat(personelIzin1).isEqualTo(personelIzin2);
        personelIzin2.setId(2L);
        assertThat(personelIzin1).isNotEqualTo(personelIzin2);
        personelIzin1.setId(null);
        assertThat(personelIzin1).isNotEqualTo(personelIzin2);
    }
}
