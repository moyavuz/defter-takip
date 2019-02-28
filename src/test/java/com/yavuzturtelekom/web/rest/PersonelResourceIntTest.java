package com.yavuzturtelekom.web.rest;

import com.yavuzturtelekom.DefterTakipApp;

import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.domain.Unvan;
import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.repository.PersonelRepository;
import com.yavuzturtelekom.service.PersonelService;
import com.yavuzturtelekom.web.rest.errors.ExceptionTranslator;
import com.yavuzturtelekom.service.dto.PersonelCriteria;
import com.yavuzturtelekom.service.PersonelQueryService;

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
    private PersonelQueryService personelQueryService;

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
        final PersonelResource personelResource = new PersonelResource(personelService, personelQueryService);
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
    public void getAllPersonelsByTckimliknoIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where tckimlikno equals to DEFAULT_TCKIMLIKNO
        defaultPersonelShouldBeFound("tckimlikno.equals=" + DEFAULT_TCKIMLIKNO);

        // Get all the personelList where tckimlikno equals to UPDATED_TCKIMLIKNO
        defaultPersonelShouldNotBeFound("tckimlikno.equals=" + UPDATED_TCKIMLIKNO);
    }

    @Test
    @Transactional
    public void getAllPersonelsByTckimliknoIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where tckimlikno in DEFAULT_TCKIMLIKNO or UPDATED_TCKIMLIKNO
        defaultPersonelShouldBeFound("tckimlikno.in=" + DEFAULT_TCKIMLIKNO + "," + UPDATED_TCKIMLIKNO);

        // Get all the personelList where tckimlikno equals to UPDATED_TCKIMLIKNO
        defaultPersonelShouldNotBeFound("tckimlikno.in=" + UPDATED_TCKIMLIKNO);
    }

    @Test
    @Transactional
    public void getAllPersonelsByTckimliknoIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where tckimlikno is not null
        defaultPersonelShouldBeFound("tckimlikno.specified=true");

        // Get all the personelList where tckimlikno is null
        defaultPersonelShouldNotBeFound("tckimlikno.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByTckimliknoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where tckimlikno greater than or equals to DEFAULT_TCKIMLIKNO
        defaultPersonelShouldBeFound("tckimlikno.greaterOrEqualThan=" + DEFAULT_TCKIMLIKNO);

        // Get all the personelList where tckimlikno greater than or equals to UPDATED_TCKIMLIKNO
        defaultPersonelShouldNotBeFound("tckimlikno.greaterOrEqualThan=" + UPDATED_TCKIMLIKNO);
    }

    @Test
    @Transactional
    public void getAllPersonelsByTckimliknoIsLessThanSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where tckimlikno less than or equals to DEFAULT_TCKIMLIKNO
        defaultPersonelShouldNotBeFound("tckimlikno.lessThan=" + DEFAULT_TCKIMLIKNO);

        // Get all the personelList where tckimlikno less than or equals to UPDATED_TCKIMLIKNO
        defaultPersonelShouldBeFound("tckimlikno.lessThan=" + UPDATED_TCKIMLIKNO);
    }


    @Test
    @Transactional
    public void getAllPersonelsByAdIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where ad equals to DEFAULT_AD
        defaultPersonelShouldBeFound("ad.equals=" + DEFAULT_AD);

        // Get all the personelList where ad equals to UPDATED_AD
        defaultPersonelShouldNotBeFound("ad.equals=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllPersonelsByAdIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where ad in DEFAULT_AD or UPDATED_AD
        defaultPersonelShouldBeFound("ad.in=" + DEFAULT_AD + "," + UPDATED_AD);

        // Get all the personelList where ad equals to UPDATED_AD
        defaultPersonelShouldNotBeFound("ad.in=" + UPDATED_AD);
    }

    @Test
    @Transactional
    public void getAllPersonelsByAdIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where ad is not null
        defaultPersonelShouldBeFound("ad.specified=true");

        // Get all the personelList where ad is null
        defaultPersonelShouldNotBeFound("ad.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByCepTelefonIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cepTelefon equals to DEFAULT_CEP_TELEFON
        defaultPersonelShouldBeFound("cepTelefon.equals=" + DEFAULT_CEP_TELEFON);

        // Get all the personelList where cepTelefon equals to UPDATED_CEP_TELEFON
        defaultPersonelShouldNotBeFound("cepTelefon.equals=" + UPDATED_CEP_TELEFON);
    }

    @Test
    @Transactional
    public void getAllPersonelsByCepTelefonIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cepTelefon in DEFAULT_CEP_TELEFON or UPDATED_CEP_TELEFON
        defaultPersonelShouldBeFound("cepTelefon.in=" + DEFAULT_CEP_TELEFON + "," + UPDATED_CEP_TELEFON);

        // Get all the personelList where cepTelefon equals to UPDATED_CEP_TELEFON
        defaultPersonelShouldNotBeFound("cepTelefon.in=" + UPDATED_CEP_TELEFON);
    }

    @Test
    @Transactional
    public void getAllPersonelsByCepTelefonIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cepTelefon is not null
        defaultPersonelShouldBeFound("cepTelefon.specified=true");

        // Get all the personelList where cepTelefon is null
        defaultPersonelShouldNotBeFound("cepTelefon.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsBySabitTelefonIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where sabitTelefon equals to DEFAULT_SABIT_TELEFON
        defaultPersonelShouldBeFound("sabitTelefon.equals=" + DEFAULT_SABIT_TELEFON);

        // Get all the personelList where sabitTelefon equals to UPDATED_SABIT_TELEFON
        defaultPersonelShouldNotBeFound("sabitTelefon.equals=" + UPDATED_SABIT_TELEFON);
    }

    @Test
    @Transactional
    public void getAllPersonelsBySabitTelefonIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where sabitTelefon in DEFAULT_SABIT_TELEFON or UPDATED_SABIT_TELEFON
        defaultPersonelShouldBeFound("sabitTelefon.in=" + DEFAULT_SABIT_TELEFON + "," + UPDATED_SABIT_TELEFON);

        // Get all the personelList where sabitTelefon equals to UPDATED_SABIT_TELEFON
        defaultPersonelShouldNotBeFound("sabitTelefon.in=" + UPDATED_SABIT_TELEFON);
    }

    @Test
    @Transactional
    public void getAllPersonelsBySabitTelefonIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where sabitTelefon is not null
        defaultPersonelShouldBeFound("sabitTelefon.specified=true");

        // Get all the personelList where sabitTelefon is null
        defaultPersonelShouldNotBeFound("sabitTelefon.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByEpostaIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where eposta equals to DEFAULT_EPOSTA
        defaultPersonelShouldBeFound("eposta.equals=" + DEFAULT_EPOSTA);

        // Get all the personelList where eposta equals to UPDATED_EPOSTA
        defaultPersonelShouldNotBeFound("eposta.equals=" + UPDATED_EPOSTA);
    }

    @Test
    @Transactional
    public void getAllPersonelsByEpostaIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where eposta in DEFAULT_EPOSTA or UPDATED_EPOSTA
        defaultPersonelShouldBeFound("eposta.in=" + DEFAULT_EPOSTA + "," + UPDATED_EPOSTA);

        // Get all the personelList where eposta equals to UPDATED_EPOSTA
        defaultPersonelShouldNotBeFound("eposta.in=" + UPDATED_EPOSTA);
    }

    @Test
    @Transactional
    public void getAllPersonelsByEpostaIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where eposta is not null
        defaultPersonelShouldBeFound("eposta.specified=true");

        // Get all the personelList where eposta is null
        defaultPersonelShouldNotBeFound("eposta.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByCinsiyetIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cinsiyet equals to DEFAULT_CINSIYET
        defaultPersonelShouldBeFound("cinsiyet.equals=" + DEFAULT_CINSIYET);

        // Get all the personelList where cinsiyet equals to UPDATED_CINSIYET
        defaultPersonelShouldNotBeFound("cinsiyet.equals=" + UPDATED_CINSIYET);
    }

    @Test
    @Transactional
    public void getAllPersonelsByCinsiyetIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cinsiyet in DEFAULT_CINSIYET or UPDATED_CINSIYET
        defaultPersonelShouldBeFound("cinsiyet.in=" + DEFAULT_CINSIYET + "," + UPDATED_CINSIYET);

        // Get all the personelList where cinsiyet equals to UPDATED_CINSIYET
        defaultPersonelShouldNotBeFound("cinsiyet.in=" + UPDATED_CINSIYET);
    }

    @Test
    @Transactional
    public void getAllPersonelsByCinsiyetIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cinsiyet is not null
        defaultPersonelShouldBeFound("cinsiyet.specified=true");

        // Get all the personelList where cinsiyet is null
        defaultPersonelShouldNotBeFound("cinsiyet.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByEgitimDurumuIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where egitimDurumu equals to DEFAULT_EGITIM_DURUMU
        defaultPersonelShouldBeFound("egitimDurumu.equals=" + DEFAULT_EGITIM_DURUMU);

        // Get all the personelList where egitimDurumu equals to UPDATED_EGITIM_DURUMU
        defaultPersonelShouldNotBeFound("egitimDurumu.equals=" + UPDATED_EGITIM_DURUMU);
    }

    @Test
    @Transactional
    public void getAllPersonelsByEgitimDurumuIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where egitimDurumu in DEFAULT_EGITIM_DURUMU or UPDATED_EGITIM_DURUMU
        defaultPersonelShouldBeFound("egitimDurumu.in=" + DEFAULT_EGITIM_DURUMU + "," + UPDATED_EGITIM_DURUMU);

        // Get all the personelList where egitimDurumu equals to UPDATED_EGITIM_DURUMU
        defaultPersonelShouldNotBeFound("egitimDurumu.in=" + UPDATED_EGITIM_DURUMU);
    }

    @Test
    @Transactional
    public void getAllPersonelsByEgitimDurumuIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where egitimDurumu is not null
        defaultPersonelShouldBeFound("egitimDurumu.specified=true");

        // Get all the personelList where egitimDurumu is null
        defaultPersonelShouldNotBeFound("egitimDurumu.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByKanGrubuIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where kanGrubu equals to DEFAULT_KAN_GRUBU
        defaultPersonelShouldBeFound("kanGrubu.equals=" + DEFAULT_KAN_GRUBU);

        // Get all the personelList where kanGrubu equals to UPDATED_KAN_GRUBU
        defaultPersonelShouldNotBeFound("kanGrubu.equals=" + UPDATED_KAN_GRUBU);
    }

    @Test
    @Transactional
    public void getAllPersonelsByKanGrubuIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where kanGrubu in DEFAULT_KAN_GRUBU or UPDATED_KAN_GRUBU
        defaultPersonelShouldBeFound("kanGrubu.in=" + DEFAULT_KAN_GRUBU + "," + UPDATED_KAN_GRUBU);

        // Get all the personelList where kanGrubu equals to UPDATED_KAN_GRUBU
        defaultPersonelShouldNotBeFound("kanGrubu.in=" + UPDATED_KAN_GRUBU);
    }

    @Test
    @Transactional
    public void getAllPersonelsByKanGrubuIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where kanGrubu is not null
        defaultPersonelShouldBeFound("kanGrubu.specified=true");

        // Get all the personelList where kanGrubu is null
        defaultPersonelShouldNotBeFound("kanGrubu.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByPersonelTuruIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where personelTuru equals to DEFAULT_PERSONEL_TURU
        defaultPersonelShouldBeFound("personelTuru.equals=" + DEFAULT_PERSONEL_TURU);

        // Get all the personelList where personelTuru equals to UPDATED_PERSONEL_TURU
        defaultPersonelShouldNotBeFound("personelTuru.equals=" + UPDATED_PERSONEL_TURU);
    }

    @Test
    @Transactional
    public void getAllPersonelsByPersonelTuruIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where personelTuru in DEFAULT_PERSONEL_TURU or UPDATED_PERSONEL_TURU
        defaultPersonelShouldBeFound("personelTuru.in=" + DEFAULT_PERSONEL_TURU + "," + UPDATED_PERSONEL_TURU);

        // Get all the personelList where personelTuru equals to UPDATED_PERSONEL_TURU
        defaultPersonelShouldNotBeFound("personelTuru.in=" + UPDATED_PERSONEL_TURU);
    }

    @Test
    @Transactional
    public void getAllPersonelsByPersonelTuruIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where personelTuru is not null
        defaultPersonelShouldBeFound("personelTuru.specified=true");

        // Get all the personelList where personelTuru is null
        defaultPersonelShouldNotBeFound("personelTuru.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByUcretIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where ucret equals to DEFAULT_UCRET
        defaultPersonelShouldBeFound("ucret.equals=" + DEFAULT_UCRET);

        // Get all the personelList where ucret equals to UPDATED_UCRET
        defaultPersonelShouldNotBeFound("ucret.equals=" + UPDATED_UCRET);
    }

    @Test
    @Transactional
    public void getAllPersonelsByUcretIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where ucret in DEFAULT_UCRET or UPDATED_UCRET
        defaultPersonelShouldBeFound("ucret.in=" + DEFAULT_UCRET + "," + UPDATED_UCRET);

        // Get all the personelList where ucret equals to UPDATED_UCRET
        defaultPersonelShouldNotBeFound("ucret.in=" + UPDATED_UCRET);
    }

    @Test
    @Transactional
    public void getAllPersonelsByUcretIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where ucret is not null
        defaultPersonelShouldBeFound("ucret.specified=true");

        // Get all the personelList where ucret is null
        defaultPersonelShouldNotBeFound("ucret.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByIbanIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where iban equals to DEFAULT_IBAN
        defaultPersonelShouldBeFound("iban.equals=" + DEFAULT_IBAN);

        // Get all the personelList where iban equals to UPDATED_IBAN
        defaultPersonelShouldNotBeFound("iban.equals=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    public void getAllPersonelsByIbanIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where iban in DEFAULT_IBAN or UPDATED_IBAN
        defaultPersonelShouldBeFound("iban.in=" + DEFAULT_IBAN + "," + UPDATED_IBAN);

        // Get all the personelList where iban equals to UPDATED_IBAN
        defaultPersonelShouldNotBeFound("iban.in=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    public void getAllPersonelsByIbanIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where iban is not null
        defaultPersonelShouldBeFound("iban.specified=true");

        // Get all the personelList where iban is null
        defaultPersonelShouldNotBeFound("iban.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByMedeniHaliIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where medeniHali equals to DEFAULT_MEDENI_HALI
        defaultPersonelShouldBeFound("medeniHali.equals=" + DEFAULT_MEDENI_HALI);

        // Get all the personelList where medeniHali equals to UPDATED_MEDENI_HALI
        defaultPersonelShouldNotBeFound("medeniHali.equals=" + UPDATED_MEDENI_HALI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByMedeniHaliIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where medeniHali in DEFAULT_MEDENI_HALI or UPDATED_MEDENI_HALI
        defaultPersonelShouldBeFound("medeniHali.in=" + DEFAULT_MEDENI_HALI + "," + UPDATED_MEDENI_HALI);

        // Get all the personelList where medeniHali equals to UPDATED_MEDENI_HALI
        defaultPersonelShouldNotBeFound("medeniHali.in=" + UPDATED_MEDENI_HALI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByMedeniHaliIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where medeniHali is not null
        defaultPersonelShouldBeFound("medeniHali.specified=true");

        // Get all the personelList where medeniHali is null
        defaultPersonelShouldNotBeFound("medeniHali.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByDogumTarihiIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where dogumTarihi equals to DEFAULT_DOGUM_TARIHI
        defaultPersonelShouldBeFound("dogumTarihi.equals=" + DEFAULT_DOGUM_TARIHI);

        // Get all the personelList where dogumTarihi equals to UPDATED_DOGUM_TARIHI
        defaultPersonelShouldNotBeFound("dogumTarihi.equals=" + UPDATED_DOGUM_TARIHI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByDogumTarihiIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where dogumTarihi in DEFAULT_DOGUM_TARIHI or UPDATED_DOGUM_TARIHI
        defaultPersonelShouldBeFound("dogumTarihi.in=" + DEFAULT_DOGUM_TARIHI + "," + UPDATED_DOGUM_TARIHI);

        // Get all the personelList where dogumTarihi equals to UPDATED_DOGUM_TARIHI
        defaultPersonelShouldNotBeFound("dogumTarihi.in=" + UPDATED_DOGUM_TARIHI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByDogumTarihiIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where dogumTarihi is not null
        defaultPersonelShouldBeFound("dogumTarihi.specified=true");

        // Get all the personelList where dogumTarihi is null
        defaultPersonelShouldNotBeFound("dogumTarihi.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByDogumTarihiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where dogumTarihi greater than or equals to DEFAULT_DOGUM_TARIHI
        defaultPersonelShouldBeFound("dogumTarihi.greaterOrEqualThan=" + DEFAULT_DOGUM_TARIHI);

        // Get all the personelList where dogumTarihi greater than or equals to UPDATED_DOGUM_TARIHI
        defaultPersonelShouldNotBeFound("dogumTarihi.greaterOrEqualThan=" + UPDATED_DOGUM_TARIHI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByDogumTarihiIsLessThanSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where dogumTarihi less than or equals to DEFAULT_DOGUM_TARIHI
        defaultPersonelShouldNotBeFound("dogumTarihi.lessThan=" + DEFAULT_DOGUM_TARIHI);

        // Get all the personelList where dogumTarihi less than or equals to UPDATED_DOGUM_TARIHI
        defaultPersonelShouldBeFound("dogumTarihi.lessThan=" + UPDATED_DOGUM_TARIHI);
    }


    @Test
    @Transactional
    public void getAllPersonelsByGirisTarihiIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where girisTarihi equals to DEFAULT_GIRIS_TARIHI
        defaultPersonelShouldBeFound("girisTarihi.equals=" + DEFAULT_GIRIS_TARIHI);

        // Get all the personelList where girisTarihi equals to UPDATED_GIRIS_TARIHI
        defaultPersonelShouldNotBeFound("girisTarihi.equals=" + UPDATED_GIRIS_TARIHI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByGirisTarihiIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where girisTarihi in DEFAULT_GIRIS_TARIHI or UPDATED_GIRIS_TARIHI
        defaultPersonelShouldBeFound("girisTarihi.in=" + DEFAULT_GIRIS_TARIHI + "," + UPDATED_GIRIS_TARIHI);

        // Get all the personelList where girisTarihi equals to UPDATED_GIRIS_TARIHI
        defaultPersonelShouldNotBeFound("girisTarihi.in=" + UPDATED_GIRIS_TARIHI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByGirisTarihiIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where girisTarihi is not null
        defaultPersonelShouldBeFound("girisTarihi.specified=true");

        // Get all the personelList where girisTarihi is null
        defaultPersonelShouldNotBeFound("girisTarihi.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByGirisTarihiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where girisTarihi greater than or equals to DEFAULT_GIRIS_TARIHI
        defaultPersonelShouldBeFound("girisTarihi.greaterOrEqualThan=" + DEFAULT_GIRIS_TARIHI);

        // Get all the personelList where girisTarihi greater than or equals to UPDATED_GIRIS_TARIHI
        defaultPersonelShouldNotBeFound("girisTarihi.greaterOrEqualThan=" + UPDATED_GIRIS_TARIHI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByGirisTarihiIsLessThanSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where girisTarihi less than or equals to DEFAULT_GIRIS_TARIHI
        defaultPersonelShouldNotBeFound("girisTarihi.lessThan=" + DEFAULT_GIRIS_TARIHI);

        // Get all the personelList where girisTarihi less than or equals to UPDATED_GIRIS_TARIHI
        defaultPersonelShouldBeFound("girisTarihi.lessThan=" + UPDATED_GIRIS_TARIHI);
    }


    @Test
    @Transactional
    public void getAllPersonelsByIzinHakedisIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where izinHakedis equals to DEFAULT_IZIN_HAKEDIS
        defaultPersonelShouldBeFound("izinHakedis.equals=" + DEFAULT_IZIN_HAKEDIS);

        // Get all the personelList where izinHakedis equals to UPDATED_IZIN_HAKEDIS
        defaultPersonelShouldNotBeFound("izinHakedis.equals=" + UPDATED_IZIN_HAKEDIS);
    }

    @Test
    @Transactional
    public void getAllPersonelsByIzinHakedisIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where izinHakedis in DEFAULT_IZIN_HAKEDIS or UPDATED_IZIN_HAKEDIS
        defaultPersonelShouldBeFound("izinHakedis.in=" + DEFAULT_IZIN_HAKEDIS + "," + UPDATED_IZIN_HAKEDIS);

        // Get all the personelList where izinHakedis equals to UPDATED_IZIN_HAKEDIS
        defaultPersonelShouldNotBeFound("izinHakedis.in=" + UPDATED_IZIN_HAKEDIS);
    }

    @Test
    @Transactional
    public void getAllPersonelsByIzinHakedisIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where izinHakedis is not null
        defaultPersonelShouldBeFound("izinHakedis.specified=true");

        // Get all the personelList where izinHakedis is null
        defaultPersonelShouldNotBeFound("izinHakedis.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByCikisTarihiIsEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cikisTarihi equals to DEFAULT_CIKIS_TARIHI
        defaultPersonelShouldBeFound("cikisTarihi.equals=" + DEFAULT_CIKIS_TARIHI);

        // Get all the personelList where cikisTarihi equals to UPDATED_CIKIS_TARIHI
        defaultPersonelShouldNotBeFound("cikisTarihi.equals=" + UPDATED_CIKIS_TARIHI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByCikisTarihiIsInShouldWork() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cikisTarihi in DEFAULT_CIKIS_TARIHI or UPDATED_CIKIS_TARIHI
        defaultPersonelShouldBeFound("cikisTarihi.in=" + DEFAULT_CIKIS_TARIHI + "," + UPDATED_CIKIS_TARIHI);

        // Get all the personelList where cikisTarihi equals to UPDATED_CIKIS_TARIHI
        defaultPersonelShouldNotBeFound("cikisTarihi.in=" + UPDATED_CIKIS_TARIHI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByCikisTarihiIsNullOrNotNull() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cikisTarihi is not null
        defaultPersonelShouldBeFound("cikisTarihi.specified=true");

        // Get all the personelList where cikisTarihi is null
        defaultPersonelShouldNotBeFound("cikisTarihi.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonelsByCikisTarihiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cikisTarihi greater than or equals to DEFAULT_CIKIS_TARIHI
        defaultPersonelShouldBeFound("cikisTarihi.greaterOrEqualThan=" + DEFAULT_CIKIS_TARIHI);

        // Get all the personelList where cikisTarihi greater than or equals to UPDATED_CIKIS_TARIHI
        defaultPersonelShouldNotBeFound("cikisTarihi.greaterOrEqualThan=" + UPDATED_CIKIS_TARIHI);
    }

    @Test
    @Transactional
    public void getAllPersonelsByCikisTarihiIsLessThanSomething() throws Exception {
        // Initialize the database
        personelRepository.saveAndFlush(personel);

        // Get all the personelList where cikisTarihi less than or equals to DEFAULT_CIKIS_TARIHI
        defaultPersonelShouldNotBeFound("cikisTarihi.lessThan=" + DEFAULT_CIKIS_TARIHI);

        // Get all the personelList where cikisTarihi less than or equals to UPDATED_CIKIS_TARIHI
        defaultPersonelShouldBeFound("cikisTarihi.lessThan=" + UPDATED_CIKIS_TARIHI);
    }


    @Test
    @Transactional
    public void getAllPersonelsByYoneticiIsEqualToSomething() throws Exception {
        // Initialize the database
        Personel yonetici = PersonelResourceIntTest.createEntity(em);
        em.persist(yonetici);
        em.flush();
        personel.setYonetici(yonetici);
        personelRepository.saveAndFlush(personel);
        Long yoneticiId = yonetici.getId();

        // Get all the personelList where yonetici equals to yoneticiId
        defaultPersonelShouldBeFound("yoneticiId.equals=" + yoneticiId);

        // Get all the personelList where yonetici equals to yoneticiId + 1
        defaultPersonelShouldNotBeFound("yoneticiId.equals=" + (yoneticiId + 1));
    }


    @Test
    @Transactional
    public void getAllPersonelsByUnvanIsEqualToSomething() throws Exception {
        // Initialize the database
        Unvan unvan = UnvanResourceIntTest.createEntity(em);
        em.persist(unvan);
        em.flush();
        personel.setUnvan(unvan);
        personelRepository.saveAndFlush(personel);
        Long unvanId = unvan.getId();

        // Get all the personelList where unvan equals to unvanId
        defaultPersonelShouldBeFound("unvanId.equals=" + unvanId);

        // Get all the personelList where unvan equals to unvanId + 1
        defaultPersonelShouldNotBeFound("unvanId.equals=" + (unvanId + 1));
    }


    @Test
    @Transactional
    public void getAllPersonelsByEkipIsEqualToSomething() throws Exception {
        // Initialize the database
        Ekip ekip = EkipResourceIntTest.createEntity(em);
        em.persist(ekip);
        em.flush();
        personel.addEkip(ekip);
        personelRepository.saveAndFlush(personel);
        Long ekipId = ekip.getId();

        // Get all the personelList where ekip equals to ekipId
        defaultPersonelShouldBeFound("ekipId.equals=" + ekipId);

        // Get all the personelList where ekip equals to ekipId + 1
        defaultPersonelShouldNotBeFound("ekipId.equals=" + (ekipId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPersonelShouldBeFound(String filter) throws Exception {
        restPersonelMockMvc.perform(get("/api/personels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personel.getId().intValue())))
            .andExpect(jsonPath("$.[*].tckimlikno").value(hasItem(DEFAULT_TCKIMLIKNO.intValue())))
            .andExpect(jsonPath("$.[*].ad").value(hasItem(DEFAULT_AD)))
            .andExpect(jsonPath("$.[*].cepTelefon").value(hasItem(DEFAULT_CEP_TELEFON)))
            .andExpect(jsonPath("$.[*].sabitTelefon").value(hasItem(DEFAULT_SABIT_TELEFON)))
            .andExpect(jsonPath("$.[*].eposta").value(hasItem(DEFAULT_EPOSTA)))
            .andExpect(jsonPath("$.[*].cinsiyet").value(hasItem(DEFAULT_CINSIYET.toString())))
            .andExpect(jsonPath("$.[*].egitimDurumu").value(hasItem(DEFAULT_EGITIM_DURUMU.toString())))
            .andExpect(jsonPath("$.[*].kanGrubu").value(hasItem(DEFAULT_KAN_GRUBU.toString())))
            .andExpect(jsonPath("$.[*].personelTuru").value(hasItem(DEFAULT_PERSONEL_TURU.toString())))
            .andExpect(jsonPath("$.[*].ucret").value(hasItem(DEFAULT_UCRET.doubleValue())))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
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

        // Check, that the count call also returns 1
        restPersonelMockMvc.perform(get("/api/personels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPersonelShouldNotBeFound(String filter) throws Exception {
        restPersonelMockMvc.perform(get("/api/personels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonelMockMvc.perform(get("/api/personels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
