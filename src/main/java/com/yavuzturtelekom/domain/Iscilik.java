package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

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
    @Column(name = "resim")
    private byte[] resim;

    @Column(name = "resim_content_type")
    private String resimContentType;

    @OneToMany(mappedBy = "iscilik")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IscilikDetay> detays = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("ekips")
    private Ekip iscilikEkip;

    @ManyToOne
    @JsonIgnoreProperties("projes")
    private Proje iscilik;

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

    public byte[] getResim() {
        return resim;
    }

    public Iscilik resim(byte[] resim) {
        this.resim = resim;
        return this;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }

    public String getResimContentType() {
        return resimContentType;
    }

    public Iscilik resimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
        return this;
    }

    public void setResimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
    }

    public Set<IscilikDetay> getDetays() {
        return detays;
    }

    public Iscilik detays(Set<IscilikDetay> iscilikDetays) {
        this.detays = iscilikDetays;
        return this;
    }

    public Iscilik addDetay(IscilikDetay iscilikDetay) {
        this.detays.add(iscilikDetay);
        iscilikDetay.setIscilik(this);
        return this;
    }

    public Iscilik removeDetay(IscilikDetay iscilikDetay) {
        this.detays.remove(iscilikDetay);
        iscilikDetay.setIscilik(null);
        return this;
    }

    public void setDetays(Set<IscilikDetay> iscilikDetays) {
        this.detays = iscilikDetays;
    }

    public Ekip getIscilikEkip() {
        return iscilikEkip;
    }

    public Iscilik iscilikEkip(Ekip ekip) {
        this.iscilikEkip = ekip;
        return this;
    }

    public void setIscilikEkip(Ekip ekip) {
        this.iscilikEkip = ekip;
    }

    public Proje getIscilik() {
        return iscilik;
    }

    public Iscilik iscilik(Proje proje) {
        this.iscilik = proje;
        return this;
    }

    public void setIscilik(Proje proje) {
        this.iscilik = proje;
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
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            "}";
    }
}
