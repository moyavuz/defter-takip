package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.PersonelArac;
import com.yavuzturtelekom.domain.Arac;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.repository.PersonelAracRepository;
import com.yavuzturtelekom.service.PersonelAracService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.PersonelAracCriteria;
import com.yavuzturtelekom.service.PersonelAracQueryService;

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
 * Test class for the PersonelAracResource REST controller.
 *
 * @see PersonelAracResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class PersonelAracResourceIntTest {

    private static final LocalDate DEFAULT_TARIH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARIH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DETAY = "AAAAAAAAAA";
    private static final String UPDATED_DETAY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_RESIM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESIM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESIM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESIM_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOSYA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYA_CONTENT_TYPE = "image/png";

    @Autowired
    private PersonelAracRepository personelAracRepository;

    @Autowired
    private PersonelAracService personelAracService;

    @Autowired
    private PersonelAracQueryService personelAracQueryService;

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

    private MockMvc restPersonelAracMockMvc;

    private PersonelArac personelArac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonelAracResource personelAracResource = new PersonelAracResource(personelAracService, personelAracQueryService);
        this.restPersonelAracMockMvc = MockMvcBuilders.standaloneSetup(personelAracResource)
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
    public static PersonelArac createEntity(EntityManager em) {
        PersonelArac personelArac = new PersonelArac()
            .tarih(DEFAULT_TARIH)
            .detay(DEFAULT_DETAY)
            .resim(DEFAULT_RESIM)
            .resimContentType(DEFAULT_RESIM_CONTENT_TYPE)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE);
        // Add required entity
        Arac arac = AracResourceIntTest.createEntity(em);
        em.persist(arac);
        em.flush();
        personelArac.setArac(arac);
        // Add required entity
        Personel personel = PersonelResourceIntTest.createEntity(em);
        em.persist(personel);
        em.flush();
        personelArac.setPersonel(personel);
        return personelArac;
    }

    @Before
    public void initTest() {
        personelArac = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonelArac() throws Exception {
        int databaseSizeBeforeCreate = personelAracRepository.findAll().size();

        // Create the PersonelArac
        restPersonelAracMockMvc.perform(post("/api/personel-aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelArac)))
            .andExpect(status().isCreated());

        // Validate the PersonelArac in the database
        List<PersonelArac> personelAracList = personelAracRepository.findAll();
        assertThat(personelAracList).hasSize(databaseSizeBeforeCreate + 1);
        PersonelArac testPersonelArac = personelAracList.get(personelAracList.size() - 1);
        assertThat(testPersonelArac.getTarih()).isEqualTo(DEFAULT_TARIH);
        assertThat(testPersonelArac.getDetay()).isEqualTo(DEFAULT_DETAY);
        assertThat(testPersonelArac.getResim()).isEqualTo(DEFAULT_RESIM);
        assertThat(testPersonelArac.getResimContentType()).isEqualTo(DEFAULT_RESIM_CONTENT_TYPE);
        assertThat(testPersonelArac.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testPersonelArac.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPersonelAracWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personelAracRepository.findAll().size();

        // Create the PersonelArac with an existing ID
        personelArac.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonelAracMockMvc.perform(post("/api/personel-aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelArac)))
            .andExpect(status().isBadRequest());

        // Validate the PersonelArac in the database
        List<PersonelArac> personelAracList = personelAracRepository.findAll();
        assertThat(personelAracList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonelAracs() throws Exception {
        // Initialize the database
        personelAracRepository.saveAndFlush(personelArac);

        // Get all the personelAracList
        restPersonelAracMockMvc.perform(get("/api/personel-aracs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personelArac.getId().intValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].detay").value(hasItem(DEFAULT_DETAY.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))));
    }
    
    @Test
    @Transactional
    public void getPersonelArac() throws Exception {
        // Initialize the database
        personelAracRepository.saveAndFlush(personelArac);

        // Get the personelArac
        restPersonelAracMockMvc.perform(get("/api/personel-aracs/{id}", personelArac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personelArac.getId().intValue()))
            .andExpect(jsonPath("$.tarih").value(DEFAULT_TARIH.toString()))
            .andExpect(jsonPath("$.detay").value(DEFAULT_DETAY.toString()))
            .andExpect(jsonPath("$.resimContentType").value(DEFAULT_RESIM_CONTENT_TYPE))
            .andExpect(jsonPath("$.resim").value(Base64Utils.encodeToString(DEFAULT_RESIM)))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)));
    }

    @Test
    @Transactional
    public void getAllPersonelAracsByTarihIsEqualToSomething() throws Exception {
        // Initialize the database
        personelAracRepository.saveAndFlush(personelArac);

        // Get all the personelAracList where tarih equals to DEFAULT_TARIH
        defaultPersonelAracShouldBeFound("tarih.equals=" + DEFAULT_TARIH);

        // Get all the personelAracList where tarih equals to UPDATED_TARIH
        defaultPersonelAracShouldNotBeFound("tarih.equals=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllPersonelAracsByTarihIsInShouldWork() throws Exception {
        // Initialize the database
        personelAracRepository.saveAndFlush(personelArac);

        // Get all the personelAracList where tarih in DEFAULT_TARIH or UPDATED_TARIH
        defaultPersonelAracShouldBeFound("tarih.in=" + DEFAULT_TARIH + "," + UPDATED_TARIH);

        // Get all the personelAracList where tarih equals to UPDATED_TARIH
        defaultPersonelAracShouldNotBeFound("tarih.in=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllPersonelAracsByTarihIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelAracRepository.saveAndFlush(personelArac);

        // Get all the personelAracList where tarih is not null
        defaultPersonelAracShouldBeFound("tarih.specified=true");

        // Get all the personelAracList where tarih is null
        defaultPersonelAracShouldNotBeFound("tarih.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelAracsByTarihIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personelAracRepository.saveAndFlush(personelArac);

        // Get all the personelAracList where tarih greater than or equals to DEFAULT_TARIH
        defaultPersonelAracShouldBeFound("tarih.greaterOrEqualThan=" + DEFAULT_TARIH);

        // Get all the personelAracList where tarih greater than or equals to UPDATED_TARIH
        defaultPersonelAracShouldNotBeFound("tarih.greaterOrEqualThan=" + UPDATED_TARIH);
    }

    @Test
    @Transactional
    public void getAllPersonelAracsByTarihIsLessThanSomething() throws Exception {
        // Initialize the database
        personelAracRepository.saveAndFlush(personelArac);

        // Get all the personelAracList where tarih less than or equals to DEFAULT_TARIH
        defaultPersonelAracShouldNotBeFound("tarih.lessThan=" + DEFAULT_TARIH);

        // Get all the personelAracList where tarih less than or equals to UPDATED_TARIH
        defaultPersonelAracShouldBeFound("tarih.lessThan=" + UPDATED_TARIH);
    }


    @Test
    @Transactional
    public void getAllPersonelAracsByAracIsEqualToSomething() throws Exception {
        // Initialize the database
        Arac arac = AracResourceIntTest.createEntity(em);
        em.persist(arac);
        em.flush();
        personelArac.setArac(arac);
        personelAracRepository.saveAndFlush(personelArac);
        Long aracId = arac.getId();

        // Get all the personelAracList where arac equals to aracId
        defaultPersonelAracShouldBeFound("aracId.equals=" + aracId);

        // Get all the personelAracList where arac equals to aracId + 1
        defaultPersonelAracShouldNotBeFound("aracId.equals=" + (aracId + 1));
    }


    @Test
    @Transactional
    public void getAllPersonelAracsByPersonelIsEqualToSomething() throws Exception {
        // Initialize the database
        Personel personel = PersonelResourceIntTest.createEntity(em);
        em.persist(personel);
        em.flush();
        personelArac.setPersonel(personel);
        personelAracRepository.saveAndFlush(personelArac);
        Long personelId = personel.getId();

        // Get all the personelAracList where personel equals to personelId
        defaultPersonelAracShouldBeFound("personelId.equals=" + personelId);

        // Get all the personelAracList where personel equals to personelId + 1
        defaultPersonelAracShouldNotBeFound("personelId.equals=" + (personelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPersonelAracShouldBeFound(String filter) throws Exception {
        restPersonelAracMockMvc.perform(get("/api/personel-aracs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personelArac.getId().intValue())))
            .andExpect(jsonPath("$.[*].tarih").value(hasItem(DEFAULT_TARIH.toString())))
            .andExpect(jsonPath("$.[*].detay").value(hasItem(DEFAULT_DETAY.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))));

        // Check, that the count call also returns 1
        restPersonelAracMockMvc.perform(get("/api/personel-aracs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPersonelAracShouldNotBeFound(String filter) throws Exception {
        restPersonelAracMockMvc.perform(get("/api/personel-aracs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonelAracMockMvc.perform(get("/api/personel-aracs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPersonelArac() throws Exception {
        // Get the personelArac
        restPersonelAracMockMvc.perform(get("/api/personel-aracs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonelArac() throws Exception {
        // Initialize the database
        personelAracService.save(personelArac);

        int databaseSizeBeforeUpdate = personelAracRepository.findAll().size();

        // Update the personelArac
        PersonelArac updatedPersonelArac = personelAracRepository.findById(personelArac.getId()).get();
        // Disconnect from session so that the updates on updatedPersonelArac are not directly saved in db
        em.detach(updatedPersonelArac);
        updatedPersonelArac
            .tarih(UPDATED_TARIH)
            .detay(UPDATED_DETAY)
            .resim(UPDATED_RESIM)
            .resimContentType(UPDATED_RESIM_CONTENT_TYPE)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE);

        restPersonelAracMockMvc.perform(put("/api/personel-aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonelArac)))
            .andExpect(status().isOk());

        // Validate the PersonelArac in the database
        List<PersonelArac> personelAracList = personelAracRepository.findAll();
        assertThat(personelAracList).hasSize(databaseSizeBeforeUpdate);
        PersonelArac testPersonelArac = personelAracList.get(personelAracList.size() - 1);
        assertThat(testPersonelArac.getTarih()).isEqualTo(UPDATED_TARIH);
        assertThat(testPersonelArac.getDetay()).isEqualTo(UPDATED_DETAY);
        assertThat(testPersonelArac.getResim()).isEqualTo(UPDATED_RESIM);
        assertThat(testPersonelArac.getResimContentType()).isEqualTo(UPDATED_RESIM_CONTENT_TYPE);
        assertThat(testPersonelArac.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testPersonelArac.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonelArac() throws Exception {
        int databaseSizeBeforeUpdate = personelAracRepository.findAll().size();

        // Create the PersonelArac

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonelAracMockMvc.perform(put("/api/personel-aracs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personelArac)))
            .andExpect(status().isBadRequest());

        // Validate the PersonelArac in the database
        List<PersonelArac> personelAracList = personelAracRepository.findAll();
        assertThat(personelAracList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonelArac() throws Exception {
        // Initialize the database
        personelAracService.save(personelArac);

        int databaseSizeBeforeDelete = personelAracRepository.findAll().size();

        // Delete the personelArac
        restPersonelAracMockMvc.perform(delete("/api/personel-aracs/{id}", personelArac.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonelArac> personelAracList = personelAracRepository.findAll();
        assertThat(personelAracList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonelArac.class);
        PersonelArac personelArac1 = new PersonelArac();
        personelArac1.setId(1L);
        PersonelArac personelArac2 = new PersonelArac();
        personelArac2.setId(personelArac1.getId());
        assertThat(personelArac1).isEqualTo(personelArac2);
        personelArac2.setId(2L);
        assertThat(personelArac1).isNotEqualTo(personelArac2);
        personelArac1.setId(null);
        assertThat(personelArac1).isNotEqualTo(personelArac2);
    }
}
