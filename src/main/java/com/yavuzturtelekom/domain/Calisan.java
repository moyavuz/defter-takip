package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.CalismaTuru;

import com.yavuzturtelekom.domain.enumeration.MedeniHali;

/**
 * A Calisan.
 */
@Entity
@Table(name = "calisan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Calisan implements Serializable {

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

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "eposta")
    private String eposta;

    @Enumerated(EnumType.STRING)
    @Column(name = "calisma_turu")
    private CalismaTuru calismaTuru;

    @Column(name = "ucret", precision = 10, scale = 2)
    private BigDecimal ucret;

    @Column(name = "iban")
    private String iban;

    @Enumerated(EnumType.STRING)
    @Column(name = "medeni_hali")
    private MedeniHali medeniHali;

    @Column(name = "dogum_tarihi")
    private LocalDate dogumTarihi;

    @Column(name = "giris_tarihi")
    private LocalDate girisTarihi;

    @Column(name = "cikis_tarihi")
    private LocalDate cikisTarihi;

    @ManyToOne
    @JsonIgnoreProperties("calisans")
    private Calisan yonetici;

    @ManyToOne
    @JsonIgnoreProperties("calisans")
    private Unvan unvan;

    @ManyToMany(mappedBy = "ekipCalisans")
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

    public Calisan tckimlikno(Long tckimlikno) {
        this.tckimlikno = tckimlikno;
        return this;
    }

    public void setTckimlikno(Long tckimlikno) {
        this.tckimlikno = tckimlikno;
    }

    public String getAd() {
        return ad;
    }

    public Calisan ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getTelefon() {
        return telefon;
    }

    public Calisan telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEposta() {
        return eposta;
    }

    public Calisan eposta(String eposta) {
        this.eposta = eposta;
        return this;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public CalismaTuru getCalismaTuru() {
        return calismaTuru;
    }

    public Calisan calismaTuru(CalismaTuru calismaTuru) {
        this.calismaTuru = calismaTuru;
        return this;
    }

    public void setCalismaTuru(CalismaTuru calismaTuru) {
        this.calismaTuru = calismaTuru;
    }

    public BigDecimal getUcret() {
        return ucret;
    }

    public Calisan ucret(BigDecimal ucret) {
        this.ucret = ucret;
        return this;
    }

    public void setUcret(BigDecimal ucret) {
        this.ucret = ucret;
    }

    public String getIban() {
        return iban;
    }

    public Calisan iban(String iban) {
        this.iban = iban;
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public MedeniHali getMedeniHali() {
        return medeniHali;
    }

    public Calisan medeniHali(MedeniHali medeniHali) {
        this.medeniHali = medeniHali;
        return this;
    }

    public void setMedeniHali(MedeniHali medeniHali) {
        this.medeniHali = medeniHali;
    }

    public LocalDate getDogumTarihi() {
        return dogumTarihi;
    }

    public Calisan dogumTarihi(LocalDate dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
        return this;
    }

    public void setDogumTarihi(LocalDate dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public LocalDate getGirisTarihi() {
        return girisTarihi;
    }

    public Calisan girisTarihi(LocalDate girisTarihi) {
        this.girisTarihi = girisTarihi;
        return this;
    }

    public void setGirisTarihi(LocalDate girisTarihi) {
        this.girisTarihi = girisTarihi;
    }

    public LocalDate getCikisTarihi() {
        return cikisTarihi;
    }

    public Calisan cikisTarihi(LocalDate cikisTarihi) {
        this.cikisTarihi = cikisTarihi;
        return this;
    }

    public void setCikisTarihi(LocalDate cikisTarihi) {
        this.cikisTarihi = cikisTarihi;
    }

    public Calisan getYonetici() {
        return yonetici;
    }

    public Calisan yonetici(Calisan calisan) {
        this.yonetici = calisan;
        return this;
    }

    public void setYonetici(Calisan calisan) {
        this.yonetici = calisan;
    }

    public Unvan getUnvan() {
        return unvan;
    }

    public Calisan unvan(Unvan unvan) {
        this.unvan = unvan;
        return this;
    }

    public void setUnvan(Unvan unvan) {
        this.unvan = unvan;
    }

    public Set<Ekip> getEkips() {
        return ekips;
    }

    public Calisan ekips(Set<Ekip> ekips) {
        this.ekips = ekips;
        return this;
    }

    public Calisan addEkip(Ekip ekip) {
        this.ekips.add(ekip);
        ekip.getEkipCalisans().add(this);
        return this;
    }

    public Calisan removeEkip(Ekip ekip) {
        this.ekips.remove(ekip);
        ekip.getEkipCalisans().remove(this);
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
        Calisan calisan = (Calisan) o;
        if (calisan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calisan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Calisan{" +
            "id=" + getId() +
            ", tckimlikno=" + getTckimlikno() +
            ", ad='" + getAd() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", calismaTuru='" + getCalismaTuru() + "'" +
            ", ucret=" + getUcret() +
            ", iban='" + getIban() + "'" +
            ", medeniHali='" + getMedeniHali() + "'" +
            ", dogumTarihi='" + getDogumTarihi() + "'" +
            ", girisTarihi='" + getGirisTarihi() + "'" +
            ", cikisTarihi='" + getCikisTarihi() + "'" +
            "}";
    }
}
