package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PersonelArac.
 */
@Entity
@Table(name = "personel_arac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonelArac extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Lob
    @Column(name = "detay")
    private String detay;

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
    @JsonIgnoreProperties("personelAracs")
    private Arac arac;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("personelAracs")
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

    public PersonelArac tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public String getDetay() {
        return detay;
    }

    public PersonelArac detay(String detay) {
        this.detay = detay;
        return this;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public byte[] getResim() {
        return resim;
    }

    public PersonelArac resim(byte[] resim) {
        this.resim = resim;
        return this;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }

    public String getResimContentType() {
        return resimContentType;
    }

    public PersonelArac resimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
        return this;
    }

    public void setResimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
    }

    public byte[] getDosya() {
        return dosya;
    }

    public PersonelArac dosya(byte[] dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(byte[] dosya) {
        this.dosya = dosya;
    }

    public String getDosyaContentType() {
        return dosyaContentType;
    }

    public PersonelArac dosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
        return this;
    }

    public void setDosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
    }

    public Arac getArac() {
        return arac;
    }

    public PersonelArac arac(Arac arac) {
        this.arac = arac;
        return this;
    }

    public void setArac(Arac arac) {
        this.arac = arac;
    }

    public Personel getPersonel() {
        return personel;
    }

    public PersonelArac personel(Personel personel) {
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
        PersonelArac personelArac = (PersonelArac) o;
        if (personelArac.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personelArac.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonelArac{" +
            "id=" + getId() +
            ", tarih='" + getTarih() + "'" +
            ", detay='" + getDetay() + "'" +
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            "}";
    }
}
