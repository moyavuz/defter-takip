package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.ZimmetDurumu;

/**
 * A PersonelZimmet.
 */
@Entity
@Table(name = "personel_zimmet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonelZimmet implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Enumerated(EnumType.STRING)
    @Column(name = "durumu")
    private ZimmetDurumu durumu;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("personelZimmets")
    private Personel personel;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("personelZimmets")
    private ZimmetTuru zimmet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public PersonelZimmet tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public ZimmetDurumu getDurumu() {
        return durumu;
    }

    public PersonelZimmet durumu(ZimmetDurumu durumu) {
        this.durumu = durumu;
        return this;
    }

    public void setDurumu(ZimmetDurumu durumu) {
        this.durumu = durumu;
    }

    public byte[] getResim() {
        return resim;
    }

    public PersonelZimmet resim(byte[] resim) {
        this.resim = resim;
        return this;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }

    public String getResimContentType() {
        return resimContentType;
    }

    public PersonelZimmet resimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
        return this;
    }

    public void setResimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
    }

    public byte[] getDosya() {
        return dosya;
    }

    public PersonelZimmet dosya(byte[] dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(byte[] dosya) {
        this.dosya = dosya;
    }

    public String getDosyaContentType() {
        return dosyaContentType;
    }

    public PersonelZimmet dosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
        return this;
    }

    public void setDosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
    }

    public Personel getPersonel() {
        return personel;
    }

    public PersonelZimmet personel(Personel personel) {
        this.personel = personel;
        return this;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
    }

    public ZimmetTuru getZimmet() {
        return zimmet;
    }

    public PersonelZimmet zimmet(ZimmetTuru zimmetTuru) {
        this.zimmet = zimmetTuru;
        return this;
    }

    public void setZimmet(ZimmetTuru zimmetTuru) {
        this.zimmet = zimmetTuru;
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
        PersonelZimmet personelZimmet = (PersonelZimmet) o;
        if (personelZimmet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personelZimmet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonelZimmet{" +
            "id=" + getId() +
            ", tarih='" + getTarih() + "'" +
            ", durumu='" + getDurumu() + "'" +
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            "}";
    }
}
