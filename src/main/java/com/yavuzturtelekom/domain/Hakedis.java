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

    @Column(name = "ad")
    private String ad;

    @Column(name = "aciklama")
    private String aciklama;

    @Lob
    @Column(name = "detay")
    private String detay;

    @Enumerated(EnumType.STRING)
    @Column(name = "durumu")
    private IsDurumu durumu;

    @Column(name = "seri_no")
    private Long seriNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "odeme_durumu")
    private OdemeDurumu odemeDurumu;

    @Column(name = "odeme_no")
    private String odemeNo;

    @Lob
    @Column(name = "resim")
    private byte[] resim;

    @Column(name = "resim_content_type")
    private String resimContentType;

    @Column(name = "tarih")
    private LocalDate tarih;

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

    public Hakedis ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
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

    public IsDurumu getDurumu() {
        return durumu;
    }

    public Hakedis durumu(IsDurumu durumu) {
        this.durumu = durumu;
        return this;
    }

    public void setDurumu(IsDurumu durumu) {
        this.durumu = durumu;
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
            ", aciklama='" + getAciklama() + "'" +
            ", detay='" + getDetay() + "'" +
            ", durumu='" + getDurumu() + "'" +
            ", seriNo=" + getSeriNo() +
            ", odemeDurumu='" + getOdemeDurumu() + "'" +
            ", odemeNo='" + getOdemeNo() + "'" +
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            ", tarih='" + getTarih() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            "}";
    }
}
