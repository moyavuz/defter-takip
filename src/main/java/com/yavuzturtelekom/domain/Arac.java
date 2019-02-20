package com.yavuzturtelekom.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.YakitTuru;

/**
 * A Arac.
 */
@Entity
@Table(name = "arac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Arac implements Serializable {

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

    @Column(name = "model_yili")
    private Long modelYili;

    @Enumerated(EnumType.STRING)
    @Column(name = "yakit_turu")
    private YakitTuru yakitTuru;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Column(name = "muayene_tarih")
    private LocalDate muayeneTarih;

    @Column(name = "kasko_tarih")
    private LocalDate kaskoTarih;

    @Column(name = "sigorta_tarih")
    private LocalDate sigortaTarih;

    @Column(name = "bakim_tarih")
    private LocalDate bakimTarih;

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

    public Arac ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Arac aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getDetay() {
        return detay;
    }

    public Arac detay(String detay) {
        this.detay = detay;
        return this;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public Long getModelYili() {
        return modelYili;
    }

    public Arac modelYili(Long modelYili) {
        this.modelYili = modelYili;
        return this;
    }

    public void setModelYili(Long modelYili) {
        this.modelYili = modelYili;
    }

    public YakitTuru getYakitTuru() {
        return yakitTuru;
    }

    public Arac yakitTuru(YakitTuru yakitTuru) {
        this.yakitTuru = yakitTuru;
        return this;
    }

    public void setYakitTuru(YakitTuru yakitTuru) {
        this.yakitTuru = yakitTuru;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public Arac tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public LocalDate getMuayeneTarih() {
        return muayeneTarih;
    }

    public Arac muayeneTarih(LocalDate muayeneTarih) {
        this.muayeneTarih = muayeneTarih;
        return this;
    }

    public void setMuayeneTarih(LocalDate muayeneTarih) {
        this.muayeneTarih = muayeneTarih;
    }

    public LocalDate getKaskoTarih() {
        return kaskoTarih;
    }

    public Arac kaskoTarih(LocalDate kaskoTarih) {
        this.kaskoTarih = kaskoTarih;
        return this;
    }

    public void setKaskoTarih(LocalDate kaskoTarih) {
        this.kaskoTarih = kaskoTarih;
    }

    public LocalDate getSigortaTarih() {
        return sigortaTarih;
    }

    public Arac sigortaTarih(LocalDate sigortaTarih) {
        this.sigortaTarih = sigortaTarih;
        return this;
    }

    public void setSigortaTarih(LocalDate sigortaTarih) {
        this.sigortaTarih = sigortaTarih;
    }

    public LocalDate getBakimTarih() {
        return bakimTarih;
    }

    public Arac bakimTarih(LocalDate bakimTarih) {
        this.bakimTarih = bakimTarih;
        return this;
    }

    public void setBakimTarih(LocalDate bakimTarih) {
        this.bakimTarih = bakimTarih;
    }

    public byte[] getResim() {
        return resim;
    }

    public Arac resim(byte[] resim) {
        this.resim = resim;
        return this;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }

    public String getResimContentType() {
        return resimContentType;
    }

    public Arac resimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
        return this;
    }

    public void setResimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
    }

    public byte[] getDosya() {
        return dosya;
    }

    public Arac dosya(byte[] dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(byte[] dosya) {
        this.dosya = dosya;
    }

    public String getDosyaContentType() {
        return dosyaContentType;
    }

    public Arac dosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
        return this;
    }

    public void setDosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
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
        Arac arac = (Arac) o;
        if (arac.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), arac.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Arac{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", detay='" + getDetay() + "'" +
            ", modelYili=" + getModelYili() +
            ", yakitTuru='" + getYakitTuru() + "'" +
            ", tarih='" + getTarih() + "'" +
            ", muayeneTarih='" + getMuayeneTarih() + "'" +
            ", kaskoTarih='" + getKaskoTarih() + "'" +
            ", sigortaTarih='" + getSigortaTarih() + "'" +
            ", bakimTarih='" + getBakimTarih() + "'" +
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            "}";
    }
}
