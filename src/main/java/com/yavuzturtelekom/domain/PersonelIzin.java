package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.IzinTuru;

/**
 * A PersonelIzin.
 */
@Entity
@Table(name = "personel_izin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonelIzin extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Column(name = "miktar")
    private Double miktar;

    @Enumerated(EnumType.STRING)
    @Column(name = "turu")
    private IzinTuru turu;

    @Lob
    @Column(name = "dosya")
    private byte[] dosya;

    @Column(name = "dosya_content_type")
    private String dosyaContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("personelIzins")
    private Personel personel;

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

    public PersonelIzin tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public Double getMiktar() {
        return miktar;
    }

    public PersonelIzin miktar(Double miktar) {
        this.miktar = miktar;
        return this;
    }

    public void setMiktar(Double miktar) {
        this.miktar = miktar;
    }

    public IzinTuru getTuru() {
        return turu;
    }

    public PersonelIzin turu(IzinTuru turu) {
        this.turu = turu;
        return this;
    }

    public void setTuru(IzinTuru turu) {
        this.turu = turu;
    }

    public byte[] getDosya() {
        return dosya;
    }

    public PersonelIzin dosya(byte[] dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(byte[] dosya) {
        this.dosya = dosya;
    }

    public String getDosyaContentType() {
        return dosyaContentType;
    }

    public PersonelIzin dosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
        return this;
    }

    public void setDosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
    }

    public Personel getPersonel() {
        return personel;
    }

    public PersonelIzin personel(Personel personel) {
        this.personel = personel;
        return this;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
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
        PersonelIzin personelIzin = (PersonelIzin) o;
        if (personelIzin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personelIzin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonelIzin{" +
            "id=" + getId() +
            ", tarih='" + getTarih() + "'" +
            ", miktar=" + getMiktar() +
            ", turu='" + getTuru() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            "}";
    }
}
