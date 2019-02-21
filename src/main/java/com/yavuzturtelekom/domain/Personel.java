package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.Cinsiyet;

import com.yavuzturtelekom.domain.enumeration.EgitimDurumu;

import com.yavuzturtelekom.domain.enumeration.KanGrubu;

import com.yavuzturtelekom.domain.enumeration.PersonelTuru;

import com.yavuzturtelekom.domain.enumeration.MedeniHali;

/**
 * A Personel.
 */
@Entity
@Table(name = "personel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Personel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tckimlikno", nullable = false)
    private Long tckimlikno;

    @NotNull
    @Size(min = 2)
    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "cep_telefon")
    private String cepTelefon;

    @Column(name = "sabit_telefon")
    private String sabitTelefon;

    @Column(name = "eposta")
    private String eposta;

    @Enumerated(EnumType.STRING)
    @Column(name = "cinsiyet")
    private Cinsiyet cinsiyet;

    @Enumerated(EnumType.STRING)
    @Column(name = "egitim_durumu")
    private EgitimDurumu egitimDurumu;

    @Enumerated(EnumType.STRING)
    @Column(name = "kan_grubu")
    private KanGrubu kanGrubu;

    @Enumerated(EnumType.STRING)
    @Column(name = "personel_turu")
    private PersonelTuru personelTuru;

    @Column(name = "ucret")
    private Double ucret;

    @Column(name = "iban")
    private String iban;

    @Enumerated(EnumType.STRING)
    @Column(name = "medeni_hali")
    private MedeniHali medeniHali;

    @Column(name = "dogum_tarihi")
    private LocalDate dogumTarihi;

    @Column(name = "giris_tarihi")
    private LocalDate girisTarihi;

    @Column(name = "izin_hakedis")
    private Double izinHakedis;

    @Column(name = "cikis_tarihi")
    private LocalDate cikisTarihi;

    @Lob
    @Column(name = "resim")
    private byte[] resim;

    @Column(name = "resim_content_type")
    private String resimContentType;

    @Lob
    @Column(name = "dosya")
    private byte[] dosya;

    @Column(name = "dosya_content_type")
    private String dosyaContentType;

    @Lob
    @Column(name = "jhi_not")
    private String not;

    @ManyToOne
    @JsonIgnoreProperties("personels")
    private Personel yonetici;

    @ManyToOne
    @JsonIgnoreProperties("personels")
    private Unvan unvan;

    @ManyToMany(mappedBy = "ekipPersonels")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Ekip> ekips = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTckimlikno() {
        return tckimlikno;
    }

    public Personel tckimlikno(Long tckimlikno) {
        this.tckimlikno = tckimlikno;
        return this;
    }

    public void setTckimlikno(Long tckimlikno) {
        this.tckimlikno = tckimlikno;
    }

    public String getAd() {
        return ad;
    }

    public Personel ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getCepTelefon() {
        return cepTelefon;
    }

    public Personel cepTelefon(String cepTelefon) {
        this.cepTelefon = cepTelefon;
        return this;
    }

    public void setCepTelefon(String cepTelefon) {
        this.cepTelefon = cepTelefon;
    }

    public String getSabitTelefon() {
        return sabitTelefon;
    }

    public Personel sabitTelefon(String sabitTelefon) {
        this.sabitTelefon = sabitTelefon;
        return this;
    }

    public void setSabitTelefon(String sabitTelefon) {
        this.sabitTelefon = sabitTelefon;
    }

    public String getEposta() {
        return eposta;
    }

    public Personel eposta(String eposta) {
        this.eposta = eposta;
        return this;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public Cinsiyet getCinsiyet() {
        return cinsiyet;
    }

    public Personel cinsiyet(Cinsiyet cinsiyet) {
        this.cinsiyet = cinsiyet;
        return this;
    }

    public void setCinsiyet(Cinsiyet cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public EgitimDurumu getEgitimDurumu() {
        return egitimDurumu;
    }

    public Personel egitimDurumu(EgitimDurumu egitimDurumu) {
        this.egitimDurumu = egitimDurumu;
        return this;
    }

    public void setEgitimDurumu(EgitimDurumu egitimDurumu) {
        this.egitimDurumu = egitimDurumu;
    }

    public KanGrubu getKanGrubu() {
        return kanGrubu;
    }

    public Personel kanGrubu(KanGrubu kanGrubu) {
        this.kanGrubu = kanGrubu;
        return this;
    }

    public void setKanGrubu(KanGrubu kanGrubu) {
        this.kanGrubu = kanGrubu;
    }

    public PersonelTuru getPersonelTuru() {
        return personelTuru;
    }

    public Personel personelTuru(PersonelTuru personelTuru) {
        this.personelTuru = personelTuru;
        return this;
    }

    public void setPersonelTuru(PersonelTuru personelTuru) {
        this.personelTuru = personelTuru;
    }

    public Double getUcret() {
        return ucret;
    }

    public Personel ucret(Double ucret) {
        this.ucret = ucret;
        return this;
    }

    public void setUcret(Double ucret) {
        this.ucret = ucret;
    }

    public String getIban() {
        return iban;
    }

    public Personel iban(String iban) {
        this.iban = iban;
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public MedeniHali getMedeniHali() {
        return medeniHali;
    }

    public Personel medeniHali(MedeniHali medeniHali) {
        this.medeniHali = medeniHali;
        return this;
    }

    public void setMedeniHali(MedeniHali medeniHali) {
        this.medeniHali = medeniHali;
    }

    public LocalDate getDogumTarihi() {
        return dogumTarihi;
    }

    public Personel dogumTarihi(LocalDate dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
        return this;
    }

    public void setDogumTarihi(LocalDate dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public LocalDate getGirisTarihi() {
        return girisTarihi;
    }

    public Personel girisTarihi(LocalDate girisTarihi) {
        this.girisTarihi = girisTarihi;
        return this;
    }

    public void setGirisTarihi(LocalDate girisTarihi) {
        this.girisTarihi = girisTarihi;
    }

    public Double getIzinHakedis() {
        return izinHakedis;
    }

    public Personel izinHakedis(Double izinHakedis) {
        this.izinHakedis = izinHakedis;
        return this;
    }

    public void setIzinHakedis(Double izinHakedis) {
        this.izinHakedis = izinHakedis;
    }

    public LocalDate getCikisTarihi() {
        return cikisTarihi;
    }

    public Personel cikisTarihi(LocalDate cikisTarihi) {
        this.cikisTarihi = cikisTarihi;
        return this;
    }

    public void setCikisTarihi(LocalDate cikisTarihi) {
        this.cikisTarihi = cikisTarihi;
    }

    public byte[] getResim() {
        return resim;
    }

    public Personel resim(byte[] resim) {
        this.resim = resim;
        return this;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }

    public String getResimContentType() {
        return resimContentType;
    }

    public Personel resimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
        return this;
    }

    public void setResimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
    }

    public byte[] getDosya() {
        return dosya;
    }

    public Personel dosya(byte[] dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(byte[] dosya) {
        this.dosya = dosya;
    }

    public String getDosyaContentType() {
        return dosyaContentType;
    }

    public Personel dosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
        return this;
    }

    public void setDosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
    }

    public String getNot() {
        return not;
    }

    public Personel not(String not) {
        this.not = not;
        return this;
    }

    public void setNot(String not) {
        this.not = not;
    }

    public Personel getYonetici() {
        return yonetici;
    }

    public Personel yonetici(Personel personel) {
        this.yonetici = personel;
        return this;
    }

    public void setYonetici(Personel personel) {
        this.yonetici = personel;
    }

    public Unvan getUnvan() {
        return unvan;
    }

    public Personel unvan(Unvan unvan) {
        this.unvan = unvan;
        return this;
    }

    public void setUnvan(Unvan unvan) {
        this.unvan = unvan;
    }

    public Set<Ekip> getEkips() {
        return ekips;
    }

    public Personel ekips(Set<Ekip> ekips) {
        this.ekips = ekips;
        return this;
    }

    public Personel addEkip(Ekip ekip) {
        this.ekips.add(ekip);
        ekip.getEkipPersonels().add(this);
        return this;
    }

    public Personel removeEkip(Ekip ekip) {
        this.ekips.remove(ekip);
        ekip.getEkipPersonels().remove(this);
        return this;
    }

    public void setEkips(Set<Ekip> ekips) {
        this.ekips = ekips;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Personel personel = (Personel) o;
        if (personel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Personel{" +
            "id=" + getId() +
            ", tckimlikno=" + getTckimlikno() +
            ", ad='" + getAd() + "'" +
            ", cepTelefon='" + getCepTelefon() + "'" +
            ", sabitTelefon='" + getSabitTelefon() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", cinsiyet='" + getCinsiyet() + "'" +
            ", egitimDurumu='" + getEgitimDurumu() + "'" +
            ", kanGrubu='" + getKanGrubu() + "'" +
            ", personelTuru='" + getPersonelTuru() + "'" +
            ", ucret=" + getUcret() +
            ", iban='" + getIban() + "'" +
            ", medeniHali='" + getMedeniHali() + "'" +
            ", dogumTarihi='" + getDogumTarihi() + "'" +
            ", girisTarihi='" + getGirisTarihi() + "'" +
            ", izinHakedis=" + getIzinHakedis() +
            ", cikisTarihi='" + getCikisTarihi() + "'" +
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            ", not='" + getNot() + "'" +
            "}";
    }
}
