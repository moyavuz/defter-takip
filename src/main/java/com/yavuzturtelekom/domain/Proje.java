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

import com.yavuzturtelekom.domain.enumeration.OnemDurumu;

import com.yavuzturtelekom.domain.enumeration.GorevDurumu;

/**
 * A Proje.
 */
@Entity
@Table(name = "proje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proje implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ad", nullable = false)
    private String ad;

    @Enumerated(EnumType.STRING)
    @Column(name = "onem_durumu")
    private OnemDurumu onemDurumu;

    @Column(name = "aciklama")
    private String aciklama;

    @Enumerated(EnumType.STRING)
    @Column(name = "durumu")
    private GorevDurumu durumu;

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

    @Column(name = "baslama_tarihi")
    private LocalDate baslamaTarihi;

    @Column(name = "bitis_tarihi")
    private LocalDate bitisTarihi;

    @OneToMany(mappedBy = "proje")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Iscilik> isciliks = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("projes")
    private ProjeTuru turu;

    @ManyToOne
    @JsonIgnoreProperties("projes")
    private Bolge bolge;

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

    public Proje ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public OnemDurumu getOnemDurumu() {
        return onemDurumu;
    }

    public Proje onemDurumu(OnemDurumu onemDurumu) {
        this.onemDurumu = onemDurumu;
        return this;
    }

    public void setOnemDurumu(OnemDurumu onemDurumu) {
        this.onemDurumu = onemDurumu;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Proje aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public GorevDurumu getDurumu() {
        return durumu;
    }

    public Proje durumu(GorevDurumu durumu) {
        this.durumu = durumu;
        return this;
    }

    public void setDurumu(GorevDurumu durumu) {
        this.durumu = durumu;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public Proje tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public String getDetay() {
        return detay;
    }

    public Proje detay(String detay) {
        this.detay = detay;
        return this;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public byte[] getResim() {
        return resim;
    }

    public Proje resim(byte[] resim) {
        this.resim = resim;
        return this;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }

    public String getResimContentType() {
        return resimContentType;
    }

    public Proje resimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
        return this;
    }

    public void setResimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
    }

    public byte[] getDosya() {
        return dosya;
    }

    public Proje dosya(byte[] dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(byte[] dosya) {
        this.dosya = dosya;
    }

    public String getDosyaContentType() {
        return dosyaContentType;
    }

    public Proje dosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
        return this;
    }

    public void setDosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
    }

    public LocalDate getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public Proje baslamaTarihi(LocalDate baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
        return this;
    }

    public void setBaslamaTarihi(LocalDate baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public LocalDate getBitisTarihi() {
        return bitisTarihi;
    }

    public Proje bitisTarihi(LocalDate bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
        return this;
    }

    public void setBitisTarihi(LocalDate bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public Set<Iscilik> getIsciliks() {
        return isciliks;
    }

    public Proje isciliks(Set<Iscilik> isciliks) {
        this.isciliks = isciliks;
        return this;
    }

    public Proje addIscilik(Iscilik iscilik) {
        this.isciliks.add(iscilik);
        iscilik.setProje(this);
        return this;
    }

    public Proje removeIscilik(Iscilik iscilik) {
        this.isciliks.remove(iscilik);
        iscilik.setProje(null);
        return this;
    }

    public void setIsciliks(Set<Iscilik> isciliks) {
        this.isciliks = isciliks;
    }

    public ProjeTuru getTuru() {
        return turu;
    }

    public Proje turu(ProjeTuru projeTuru) {
        this.turu = projeTuru;
        return this;
    }

    public void setTuru(ProjeTuru projeTuru) {
        this.turu = projeTuru;
    }

    public Bolge getBolge() {
        return bolge;
    }

    public Proje bolge(Bolge bolge) {
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
        Proje proje = (Proje) o;
        if (proje.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proje{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", onemDurumu='" + getOnemDurumu() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", durumu='" + getDurumu() + "'" +
            ", tarih='" + getTarih() + "'" +
            ", detay='" + getDetay() + "'" +
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            ", baslamaTarihi='" + getBaslamaTarihi() + "'" +
            ", bitisTarihi='" + getBitisTarihi() + "'" +
            "}";
    }
}
