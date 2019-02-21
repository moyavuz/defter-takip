package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.repository.PersonelRepository;
import com.yavuzturtelekom.service.PersonelService;
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

import com.yavuzturtelekom.domain.enumeration.Cinsiyet;
import com.yavuzturtelekom.domain.enumeration.EgitimDurumu;
import com.yavuzturtelekom.domain.enumeration.KanGrubu;
import com.yavuzturtelekom.domain.enumeration.PersonelTuru;
import com.yavuzturtelekom.domain.enumeration.MedeniHali;
/**
 * Test class for the PersonelResource REST controller.
 *
 * @see PersonelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefterTakipApp.class)
public class PersonelResourceIntTest {

    private static final Long DEFAULT_TCKIMLIKNO = 1L;
    private static final Long UPDATED_TCKIMLIKNO = 2L;

    private static final String DEFAULT_AD = "AAAAAAAAAA";
    private static final String UPDATED_AD = "BBBBBBBBBB";

    private static final String DEFAULT_CEP_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_CEP_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_SABIT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_SABIT_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_EPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_EPOSTA = "BBBBBBBBBB";

    private static final Cinsiyet DEFAULT_CINSIYET = Cinsiyet.ERKEK;
    private static final Cinsiyet UPDATED_CINSIYET = Cinsiyet.KADIN;

    private static final EgitimDurumu DEFAULT_EGITIM_DURUMU = EgitimDurumu.ILKOKUL;
    private static final EgitimDurumu UPDATED_EGITIM_DURUMU = EgitimDurumu.ORTAOKUL;

    private static final KanGrubu DEFAULT_KAN_GRUBU = KanGrubu.A_RH_POZITIF;
    private static final KanGrubu UPDATED_KAN_GRUBU = KanGrubu.A_RH_NEGATIF;

    private static final PersonelTuru DEFAULT_PERSONEL_TURU = PersonelTuru.MAASLI;
    private static final PersonelTuru UPDATED_PERSONEL_TURU = PersonelTuru.GOTURU;

    private static final Double DEFAULT_UCRET = 1D;
    private static final Double UPDATED_UCRET = 2D;

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final MedeniHali DEFAULT_MEDENI_HALI = MedeniHali.BEKAR;
    private static final MedeniHali UPDATED_MEDENI_HALI = MedeniHali.EVLI;

    private static final LocalDate DEFAULT_DOGUM_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOGUM_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_GIRIS_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GIRIS_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_IZIN_HAKEDIS = 1D;
    private static final Double UPDATED_IZIN_HAKEDIS = 2D;

    private static final LocalDate DEFAULT_CIKIS_TARIHI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CIKIS_TARIHI = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_RESIM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESIM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESIM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESIM_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOSYA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSYA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOSYA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSYA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOT = "AAAAAAAAAA";
    private static final String UPDATED_NOT = "BBBBBBBBBB";

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private PersonelService personelService;

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

    private MockMvc restPersonelMockMvc;

    private Personel personel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonelResource personelResource = new PersonelResource(personelService);
        this.restPersonelMockMvc = MockMvcBuilders.standaloneSetup(personelResource)
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
    public static Personel createEntity(EntityManager em) {
        Personel personel = new Personel()
            .tckimlikno(DEFAULT_TCKIMLIKNO)
            .ad(DEFAULT_AD)
            .cepTelefon(DEFAULT_CEP_TELEFON)
            .sabitTelefon(DEFAULT_SABIT_TELEFON)
            .eposta(DEFAULT_EPOSTA)
            .cinsiyet(DEFAULT_CINSIYET)
            .egitimDurumu(DEFAULT_EGITIM_DURUMU)
            .kanGrubu(DEFAULT_KAN_GRUBU)
            .personelTuru(DEFAULT_PERSONEL_TURU)
            .ucret(DEFAULT_UCRET)
            .iban(DEFAULT_IBAN)
            .medeniHali(DEFAULT_MEDENI_HALI)
            .dogumTarihi(DEFAULT_DOGUM_TARIHI)
            .girisTarihi(DEFAULT_GIRIS_TARIHI)
            .izinHakedis(DEFAULT_IZIN_HAKEDIS)
            .cikisTarihi(DEFAULT_CIKIS_TARIHI)
            .resim(DEFAULT_RESIM)
            .resimContentType(DEFAULT_RESIM_CONTENT_TYPE)
            .dosya(DEFAULT_DOSYA)
            .dosyaContentType(DEFAULT_DOSYA_CONTENT_TYPE)
            .not(DEFAULT_NOT);
        return personel;
    }

    @Before
    public void initTest() {
        personel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonel() throws Exception {
        int databaseSizeBeforeCreate = personelRepository.findAll().size();

        // Create the Personel
        restPersonelMockMvc.perform(post("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isCreated());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeCreate + 1);
        Personel testPersonel = personelList.get(personelList.size() - 1);
        assertThat(testPersonel.getTckimlikno()).isEqualTo(DEFAULT_TCKIMLIKNO);
        assertThat(testPersonel.getAd()).isEqualTo(DEFAULT_AD);
        assertThat(testPersonel.getCepTelefon()).isEqualTo(DEFAULT_CEP_TELEFON);
        assertThat(testPersonel.getSabitTelefon()).isEqualTo(DEFAULT_SABIT_TELEFON);
        assertThat(testPersonel.getEposta()).isEqualTo(DEFAULT_EPOSTA);
        assertThat(testPersonel.getCinsiyet()).isEqualTo(DEFAULT_CINSIYET);
        assertThat(testPersonel.getEgitimDurumu()).isEqualTo(DEFAULT_EGITIM_DURUMU);
        assertThat(testPersonel.getKanGrubu()).isEqualTo(DEFAULT_KAN_GRUBU);
        assertThat(testPersonel.getPersonelTuru()).isEqualTo(DEFAULT_PERSONEL_TURU);
        assertThat(testPersonel.getUcret()).isEqualTo(DEFAULT_UCRET);
        assertThat(testPersonel.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testPersonel.getMedeniHali()).isEqualTo(DEFAULT_MEDENI_HALI);
        assertThat(testPersonel.getDogumTarihi()).isEqualTo(DEFAULT_DOGUM_TARIHI);
        assertThat(testPersonel.getGirisTarihi()).isEqualTo(DEFAULT_GIRIS_TARIHI);
        assertThat(testPersonel.getIzinHakedis()).isEqualTo(DEFAULT_IZIN_HAKEDIS);
        assertThat(testPersonel.getCikisTarihi()).isEqualTo(DEFAULT_CIKIS_TARIHI);
        assertThat(testPersonel.getResim()).isEqualTo(DEFAULT_RESIM);
        assertThat(testPersonel.getResimContentType()).isEqualTo(DEFAULT_RESIM_CONTENT_TYPE);
        assertThat(testPersonel.getDosya()).isEqualTo(DEFAULT_DOSYA);
        assertThat(testPersonel.getDosyaContentType()).isEqualTo(DEFAULT_DOSYA_CONTENT_TYPE);
        assertThat(testPersonel.getNot()).isEqualTo(DEFAULT_NOT);
    }

    @Test
    @Transactional
    public void createPersonelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personelRepository.findAll().size();

        // Create the Personel with an existing ID
        personel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonelMockMvc.perform(post("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isBadRequest());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTckimliknoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelRepository.findAll().size();
        // set the field null
        personel.setTckimlikno(null);

        // Create the Personel, which fails.

        restPersonelMockMvc.perform(post("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isBadRequest());

        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelRepository.findAll().size();
        // set the field null
        personel.setAd(null);

        // Create the Personel, which fails.

        restPersonelMockMvc.perform(post("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isBadRequest());

        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonels() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList
        restPersonelMockMvc.perform(get("/api/personels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personel.getId().intValue())))
            .andExpect(jsonPath("$.[*].tckimlikno").value(hasItem(DEFAULT_TCKIMLIKNO.intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD.toString())))
            .andExpect(jsonPath("$.[*].cepTelefon").value(hasItem(DEFAULT_CEP_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].sabitTelefon").value(hasItem(DEFAULT_SABIT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA.toString())))
            .andExpect(jsonPath("$.[*].cinsiyet").value(hasItem(DEFAULT_CINSIYET.toString())))
            .andExpect(jsonPath("$.[*].egitimDurumu").value(hasItem(DEFAULT_EGITIM_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].kanGrubu").value(hasItem(DEFAULT_KAN_GRUBU.toString())))
            .andExpect(jsonPath("$.[*].personelTuru").value(hasItem(DEFAULT_PERSONEL_TURU.toString())))
            .andExpect(jsonPath("$.[*].ucret").value(hasItem(DEFAULT_UCRET.doubleValue())))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN.toString())))
            .andExpect(jsonPath("$.[*].medeniHali").value(hasItem(DEFAULT_MEDENI_HALI.toString())))
            .andExpect(jsonPath("$.[*].dogumTarihi").value(hasItem(DEFAULT_DOGUM_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].girisTarihi").value(hasItem(DEFAULT_GIRIS_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].izinHakedis").value(hasItem(DEFAULT_IZIN_HAKEDIS.doubleValue())))
            .andExpect(jsonPath("$.[*].cikisTarihi").value(hasItem(DEFAULT_CIKIS_TARIHI.toString())))
            .andExpect(jsonPath("$.[*].resimContentType").value(hasItem(DEFAULT_RESIM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resim").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESIM))))
            .andExpect(jsonPath("$.[*].dosyaContentType").value(hasItem(DEFAULT_DOSYA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dosya").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSYA))))
            .andExpect(jsonPath("$.[*].not").value(hasItem(DEFAULT_NOT.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonel() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get the personel
        restPersonelMockMvc.perform(get("/api/personels/{id}", personel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personel.getId().intValue()))
            .andExpect(jsonPath("$.tckimlikno").value(DEFAULT_TCKIMLIKNO.intValue()))
            .andExpect(jsonPath("$.ad").value(DEFAULT_AD.toString()))
            .andExpect(jsonPath("$.cepTelefon").value(DEFAULT_CEP_TELEFON.toString()))
            .andExpect(jsonPath("$.sabitTelefon").value(DEFAULT_SABIT_TELEFON.toString()))
            .andExpect(jsonPath("$.eposta").value(DEFAULT_EPOSTA.toString()))
            .andExpect(jsonPath("$.cinsiyet").value(DEFAULT_CINSIYET.toString()))
            .andExpect(jsonPath("$.egitimDurumu").value(DEFAULT_EGITIM_DURUMU.toString()))
            .andExpect(jsonPath("$.kanGrubu").value(DEFAULT_KAN_GRUBU.toString()))
            .andExpect(jsonPath("$.personelTuru").value(DEFAULT_PERSONEL_TURU.toString()))
            .andExpect(jsonPath("$.ucret").value(DEFAULT_UCRET.doubleValue()))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN.toString()))
            .andExpect(jsonPath("$.medeniHali").value(DEFAULT_MEDENI_HALI.toString()))
            .andExpect(jsonPath("$.dogumTarihi").value(DEFAULT_DOGUM_TARIHI.toString()))
            .andExpect(jsonPath("$.girisTarihi").value(DEFAULT_GIRIS_TARIHI.toString()))
            .andExpect(jsonPath("$.izinHakedis").value(DEFAULT_IZIN_HAKEDIS.doubleValue()))
            .andExpect(jsonPath("$.cikisTarihi").value(DEFAULT_CIKIS_TARIHI.toString()))
            .andExpect(jsonPath("$.resimContentType").value(DEFAULT_RESIM_CONTENT_TYPE))
            .andExpect(jsonPath("$.resim").value(Base64Utils.encodeToString(DEFAULT_RESIM)))
            .andExpect(jsonPath("$.dosyaContentType").value(DEFAULT_DOSYA_CONTENT_TYPE))
            .andExpect(jsonPath("$.dosya").value(Base64Utils.encodeToString(DEFAULT_DOSYA)))
            .andExpect(jsonPath("$.not").value(DEFAULT_NOT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonel() throws Exception {
        // Get the personel
        restPersonelMockMvc.perform(get("/api/personels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonel() throws Exception {
        // Initialize the database
        personelService.save(personel);

        int databaseSizeBeforeUpdate = personelRepository.findAll().size();

        // Update the personel
        Personel updatedPersonel = personelRepository.findById(personel.getId()).get();
        // Disconnect from session so that the updates on updatedPersonel are not directly saved in db
        em.detach(updatedPersonel);
        updatedPersonel
            .tckimlikno(UPDATED_TCKIMLIKNO)
            .ad(UPDATED_AD)
            .cepTelefon(UPDATED_CEP_TELEFON)
            .sabitTelefon(UPDATED_SABIT_TELEFON)
            .eposta(UPDATED_EPOSTA)
            .cinsiyet(UPDATED_CINSIYET)
            .egitimDurumu(UPDATED_EGITIM_DURUMU)
            .kanGrubu(UPDATED_KAN_GRUBU)
            .personelTuru(UPDATED_PERSONEL_TURU)
            .ucret(UPDATED_UCRET)
            .iban(UPDATED_IBAN)
            .medeniHali(UPDATED_MEDENI_HALI)
            .dogumTarihi(UPDATED_DOGUM_TARIHI)
            .girisTarihi(UPDATED_GIRIS_TARIHI)
            .izinHakedis(UPDATED_IZIN_HAKEDIS)
            .cikisTarihi(UPDATED_CIKIS_TARIHI)
            .resim(UPDATED_RESIM)
            .resimContentType(UPDATED_RESIM_CONTENT_TYPE)
            .dosya(UPDATED_DOSYA)
            .dosyaContentType(UPDATED_DOSYA_CONTENT_TYPE)
            .not(UPDATED_NOT);

        restPersonelMockMvc.perform(put("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonel)))
            .andExpect(status().isOk());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
        Personel testPersonel = personelList.get(personelList.size() - 1);
        assertThat(testPersonel.getTckimlikno()).isEqualTo(UPDATED_TCKIMLIKNO);
        assertThat(testPersonel.getAd()).isEqualTo(UPDATED_AD);
        assertThat(testPersonel.getCepTelefon()).isEqualTo(UPDATED_CEP_TELEFON);
        assertThat(testPersonel.getSabitTelefon()).isEqualTo(UPDATED_SABIT_TELEFON);
        assertThat(testPersonel.getEposta()).isEqualTo(UPDATED_EPOSTA);
        assertThat(testPersonel.getCinsiyet()).isEqualTo(UPDATED_CINSIYET);
        assertThat(testPersonel.getEgitimDurumu()).isEqualTo(UPDATED_EGITIM_DURUMU);
        assertThat(testPersonel.getKanGrubu()).isEqualTo(UPDATED_KAN_GRUBU);
        assertThat(testPersonel.getPersonelTuru()).isEqualTo(UPDATED_PERSONEL_TURU);
        assertThat(testPersonel.getUcret()).isEqualTo(UPDATED_UCRET);
        assertThat(testPersonel.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testPersonel.getMedeniHali()).isEqualTo(UPDATED_MEDENI_HALI);
        assertThat(testPersonel.getDogumTarihi()).isEqualTo(UPDATED_DOGUM_TARIHI);
        assertThat(testPersonel.getGirisTarihi()).isEqualTo(UPDATED_GIRIS_TARIHI);
        assertThat(testPersonel.getIzinHakedis()).isEqualTo(UPDATED_IZIN_HAKEDIS);
        assertThat(testPersonel.getCikisTarihi()).isEqualTo(UPDATED_CIKIS_TARIHI);
        assertThat(testPersonel.getResim()).isEqualTo(UPDATED_RESIM);
        assertThat(testPersonel.getResimContentType()).isEqualTo(UPDATED_RESIM_CONTENT_TYPE);
        assertThat(testPersonel.getDosya()).isEqualTo(UPDATED_DOSYA);
        assertThat(testPersonel.getDosyaContentType()).isEqualTo(UPDATED_DOSYA_CONTENT_TYPE);
        assertThat(testPersonel.getNot()).isEqualTo(UPDATED_NOT);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonel() throws Exception {
        int databaseSizeBeforeUpdate = personelRepository.findAll().size();

        // Create the Personel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonelMockMvc.perform(put("/api/personels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personel)))
            .andExpect(status().isBadRequest());

        // Validate the Personel in the database
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonel() throws Exception {
        // Initialize the database
        personelService.save(personel);

        int databaseSizeBeforeDelete = personelRepository.findAll().size();

        // Delete the personel
        restPersonelMockMvc.perform(delete("/api/personels/{id}", personel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Personel> personelList = personelRepository.findAll();
        assertThat(personelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personel.class);
        Personel personel1 = new Personel();
        personel1.setId(1L);
        Personel personel2 = new Personel();
        personel2.setId(personel1.getId());
        assertThat(personel1).isEqualTo(personel2);
        personel2.setId(2L);
        assertThat(personel1).isNotEqualTo(personel2);
        personel1.setId(null);
        assertThat(personel1).isNotEqualTo(personel2);
    }
}
