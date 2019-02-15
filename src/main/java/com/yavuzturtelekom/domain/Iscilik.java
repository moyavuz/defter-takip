package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.IscilikDurumu;

/**
 * A Iscilik.
 */
@Entity
@Table(name = "iscilik")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Iscilik implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ad")
    private String ad;

    @Column(name = "aciklama")
    private String aciklama;

    @Lob
    @Column(name = "detay")
    private String detay;

    @Enumerated(EnumType.STRING)
    @Column(name = "durumu")
    private IscilikDurumu durumu;

    @Lob
    @Column(name = "dosya")
    private byte[] dosya;

    @Column(name = "dosya_content_type")
    private String dosyaContentType;

    @OneToMany(mappedBy = "iscilik")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IscilikDetay> iscilikDetays = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ekips")
    private Ekip ekip;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("projes")
    private Proje proje;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public Iscilik ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Iscilik aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getDetay() {
        return detay;
    }

    public Iscilik detay(String detay) {
        this.detay = detay;
        return this;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public IscilikDurumu getDurumu() {
        return durumu;
    }

    public Iscilik durumu(IscilikDurumu durumu) {
        this.durumu = durumu;
        return this;
    }

    public void setDurumu(IscilikDurumu durumu) {
        this.durumu = durumu;
    }

    public byte[] getDosya() {
        return dosya;
    }

    public Iscilik dosya(byte[] dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(byte[] dosya) {
        this.dosya = dosya;
    }

    public String getDosyaContentType() {
        return dosyaContentType;
    }

    public Iscilik dosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
        return this;
    }

    public void setDosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
    }

    public Set<IscilikDetay> getIscilikDetays() {
        return iscilikDetays;
    }

    public Iscilik iscilikDetays(Set<IscilikDetay> iscilikDetays) {
        this.iscilikDetays = iscilikDetays;
        return this;
    }

    public Iscilik addIscilikDetay(IscilikDetay iscilikDetay) {
        this.iscilikDetays.add(iscilikDetay);
        iscilikDetay.setIscilik(this);
        return this;
    }

    public Iscilik removeIscilikDetay(IscilikDetay iscilikDetay) {
        this.iscilikDetays.remove(iscilikDetay);
        iscilikDetay.setIscilik(null);
        return this;
    }

    public void setIscilikDetays(Set<IscilikDetay> iscilikDetays) {
        this.iscilikDetays = iscilikDetays;
    }

    public Ekip getEkip() {
        return ekip;
    }

    public Iscilik ekip(Ekip ekip) {
        this.ekip = ekip;
        return this;
    }

    public void setEkip(Ekip ekip) {
        this.ekip = ekip;
    }

    public Proje getProje() {
        return proje;
    }

    public Iscilik proje(Proje proje) {
        this.proje = proje;
        return this;
    }

    public void setProje(Proje proje) {
        this.proje = proje;
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
        Iscilik iscilik = (Iscilik) o;
        if (iscilik.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iscilik.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Iscilik{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", detay='" + getDetay() + "'" +
            ", durumu='" + getDurumu() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            "}";
    }
}
