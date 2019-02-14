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

import com.yavuzturtelekom.domain.enumeration.CalisanTuru;

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

    @NotNull
    @Size(min = 2)
    @Column(name = "soyad", nullable = false)
    private String soyad;

    @Column(name = "eposta")
    private String eposta;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "dogum_tarihi")
    private LocalDate dogumTarihi;

    @Column(name = "giris_tarihi")
    private LocalDate girisTarihi;

    @Column(name = "cikis_tarihi")
    private LocalDate cikisTarihi;

    @Column(name = "ucret", precision = 10, scale = 2)
    private BigDecimal ucret;

    @Enumerated(EnumType.STRING)
    @Column(name = "calisan_turu")
    private CalisanTuru calisanTuru;

    @OneToMany(mappedBy = "calisan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Unvan> unvans = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("calisans")
    private Calisan sorumlu;

    @ManyToMany(mappedBy = "calisans")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Ekip> ekips = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("sorumlus")
    private Ekip ekip;

    @ManyToOne
    @JsonIgnoreProperties("sorumlus")
    private Bolge bolge;

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

    public String getSoyad() {
        return soyad;
    }

    public Calisan soyad(String soyad) {
        this.soyad = soyad;
        return this;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
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

    public CalisanTuru getCalisanTuru() {
        return calisanTuru;
    }

    public Calisan calisanTuru(CalisanTuru calisanTuru) {
        this.calisanTuru = calisanTuru;
        return this;
    }

    public void setCalisanTuru(CalisanTuru calisanTuru) {
        this.calisanTuru = calisanTuru;
    }

    public Set<Unvan> getUnvans() {
        return unvans;
    }

    public Calisan unvans(Set<Unvan> unvans) {
        this.unvans = unvans;
        return this;
    }

    public Calisan addUnvan(Unvan unvan) {
        this.unvans.add(unvan);
        unvan.setCalisan(this);
        return this;
    }

    public Calisan removeUnvan(Unvan unvan) {
        this.unvans.remove(unvan);
        unvan.setCalisan(null);
        return this;
    }

    public void setUnvans(Set<Unvan> unvans) {
        this.unvans = unvans;
    }

    public Calisan getSorumlu() {
        return sorumlu;
    }

    public Calisan sorumlu(Calisan calisan) {
        this.sorumlu = calisan;
        return this;
    }

    public void setSorumlu(Calisan calisan) {
        this.sorumlu = calisan;
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
        ekip.getCalisans().add(this);
        return this;
    }

    public Calisan removeEkip(Ekip ekip) {
        this.ekips.remove(ekip);
        ekip.getCalisans().remove(this);
        return this;
    }

    public void setEkips(Set<Ekip> ekips) {
        this.ekips = ekips;
    }

    public Ekip getEkip() {
        return ekip;
    }

    public Calisan ekip(Ekip ekip) {
        this.ekip = ekip;
        return this;
    }

    public void setEkip(Ekip ekip) {
        this.ekip = ekip;
    }

    public Bolge getBolge() {
        return bolge;
    }

    public Calisan bolge(Bolge bolge) {
        this.bolge = bolge;
        return this;
    }

    public void setBolge(Bolge bolge) {
        this.bolge = bolge;
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
            ", soyad='" + getSoyad() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", dogumTarihi='" + getDogumTarihi() + "'" +
            ", girisTarihi='" + getGirisTarihi() + "'" +
            ", cikisTarihi='" + getCikisTarihi() + "'" +
            ", ucret=" + getUcret() +
            ", calisanTuru='" + getCalisanTuru() + "'" +
            "}";
    }
}
