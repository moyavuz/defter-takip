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

import com.yavuzturtelekom.domain.enumeration.IsDurumu;

import com.yavuzturtelekom.domain.enumeration.OdemeDurumu;

/**
 * A Hakedis.
 */
@Entity
@Table(name = "hakedis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hakedis implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Column(name = "seri_no")
    private Long seriNo;

    @Column(name = "defter_no")
    private String defterNo;

    @Column(name = "cizim_no")
    private Long cizimNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "onem_derecesi")
    private OnemDurumu onemDerecesi;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_durumu")
    private IsDurumu isDurumu;

    @Enumerated(EnumType.STRING)
    @Column(name = "odeme_durumu")
    private OdemeDurumu odemeDurumu;

    @Column(name = "odeme_no")
    private String odemeNo;

    @Column(name = "aciklama")
    private String aciklama;

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

    @OneToMany(mappedBy = "hakedis")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HakedisDetay> hakedisDetays = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("hakedis")
    private Santral santral;

    @ManyToOne
    @JsonIgnoreProperties("hakedis")
    private HakedisTuru turu;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("hakedis")
    private Ekip ekip;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("hakedis")
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

    public Hakedis ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public Hakedis tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public Long getSeriNo() {
        return seriNo;
    }

    public Hakedis seriNo(Long seriNo) {
        this.seriNo = seriNo;
        return this;
    }

    public void setSeriNo(Long seriNo) {
        this.seriNo = seriNo;
    }

    public String getDefterNo() {
        return defterNo;
    }

    public Hakedis defterNo(String defterNo) {
        this.defterNo = defterNo;
        return this;
    }

    public void setDefterNo(String defterNo) {
        this.defterNo = defterNo;
    }

    public Long getCizimNo() {
        return cizimNo;
    }

    public Hakedis cizimNo(Long cizimNo) {
        this.cizimNo = cizimNo;
        return this;
    }

    public void setCizimNo(Long cizimNo) {
        this.cizimNo = cizimNo;
    }

    public OnemDurumu getOnemDerecesi() {
        return onemDerecesi;
    }

    public Hakedis onemDerecesi(OnemDurumu onemDerecesi) {
        this.onemDerecesi = onemDerecesi;
        return this;
    }

    public void setOnemDerecesi(OnemDurumu onemDerecesi) {
        this.onemDerecesi = onemDerecesi;
    }

    public IsDurumu getIsDurumu() {
        return isDurumu;
    }

    public Hakedis isDurumu(IsDurumu isDurumu) {
        this.isDurumu = isDurumu;
        return this;
    }

    public void setIsDurumu(IsDurumu isDurumu) {
        this.isDurumu = isDurumu;
    }

    public OdemeDurumu getOdemeDurumu() {
        return odemeDurumu;
    }

    public Hakedis odemeDurumu(OdemeDurumu odemeDurumu) {
        this.odemeDurumu = odemeDurumu;
        return this;
    }

    public void setOdemeDurumu(OdemeDurumu odemeDurumu) {
        this.odemeDurumu = odemeDurumu;
    }

    public String getOdemeNo() {
        return odemeNo;
    }

    public Hakedis odemeNo(String odemeNo) {
        this.odemeNo = odemeNo;
        return this;
    }

    public void setOdemeNo(String odemeNo) {
        this.odemeNo = odemeNo;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Hakedis aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getDetay() {
        return detay;
    }

    public Hakedis detay(String detay) {
        this.detay = detay;
        return this;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public byte[] getResim() {
        return resim;
    }

    public Hakedis resim(byte[] resim) {
        this.resim = resim;
        return this;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }

    public String getResimContentType() {
        return resimContentType;
    }

    public Hakedis resimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
        return this;
    }

    public void setResimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
    }

    public byte[] getDosya() {
        return dosya;
    }

    public Hakedis dosya(byte[] dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(byte[] dosya) {
        this.dosya = dosya;
    }

    public String getDosyaContentType() {
        return dosyaContentType;
    }

    public Hakedis dosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
        return this;
    }

    public void setDosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
    }

    public Set<HakedisDetay> getHakedisDetays() {
        return hakedisDetays;
    }

    public Hakedis hakedisDetays(Set<HakedisDetay> hakedisDetays) {
        this.hakedisDetays = hakedisDetays;
        return this;
    }

    public Hakedis addHakedisDetay(HakedisDetay hakedisDetay) {
        this.hakedisDetays.add(hakedisDetay);
        hakedisDetay.setHakedis(this);
        return this;
    }

    public Hakedis removeHakedisDetay(HakedisDetay hakedisDetay) {
        this.hakedisDetays.remove(hakedisDetay);
        hakedisDetay.setHakedis(null);
        return this;
    }

    public void setHakedisDetays(Set<HakedisDetay> hakedisDetays) {
        this.hakedisDetays = hakedisDetays;
    }

    public Santral getSantral() {
        return santral;
    }

    public Hakedis santral(Santral santral) {
        this.santral = santral;
        return this;
    }

    public void setSantral(Santral santral) {
        this.santral = santral;
    }

    public HakedisTuru getTuru() {
        return turu;
    }

    public Hakedis turu(HakedisTuru hakedisTuru) {
        this.turu = hakedisTuru;
        return this;
    }

    public void setTuru(HakedisTuru hakedisTuru) {
        this.turu = hakedisTuru;
    }

    public Ekip getEkip() {
        return ekip;
    }

    public Hakedis ekip(Ekip ekip) {
        this.ekip = ekip;
        return this;
    }

    public void setEkip(Ekip ekip) {
        this.ekip = ekip;
    }

    public Proje getProje() {
        return proje;
    }

    public Hakedis proje(Proje proje) {
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
        Hakedis hakedis = (Hakedis) o;
        if (hakedis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hakedis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Hakedis{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", tarih='" + getTarih() + "'" +
            ", seriNo=" + getSeriNo() +
            ", defterNo='" + getDefterNo() + "'" +
            ", cizimNo=" + getCizimNo() +
            ", onemDerecesi='" + getOnemDerecesi() + "'" +
            ", isDurumu='" + getIsDurumu() + "'" +
            ", odemeDurumu='" + getOdemeDurumu() + "'" +
            ", odemeNo='" + getOdemeNo() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", detay='" + getDetay() + "'" +
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            "}";
    }
}
