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

    @Lob
    @Column(name = "aciklama")
    private String aciklama;

    @Lob
    @Column(name = "resim")
    private byte[] resim;

    @Column(name = "resim_content_type")
    private String resimContentType;

    @ManyToOne
    @JsonIgnoreProperties("iscilikEkips")
    private Ekip ekip;

    @ManyToOne
    @JsonIgnoreProperties("isciliks")
    private Proje proje;

    @OneToMany(mappedBy = "iscilik")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IscilikDetay> isciliks = new HashSet<>();
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

    public Set<IscilikDetay> getIsciliks() {
        return isciliks;
    }

    public Iscilik isciliks(Set<IscilikDetay> iscilikDetays) {
        this.isciliks = iscilikDetays;
        return this;
    }

    public Iscilik addIscilik(IscilikDetay iscilikDetay) {
        this.isciliks.add(iscilikDetay);
        iscilikDetay.setIscilik(this);
        return this;
    }

    public Iscilik removeIscilik(IscilikDetay iscilikDetay) {
        this.isciliks.remove(iscilikDetay);
        iscilikDetay.setIscilik(null);
        return this;
    }

    public void setIsciliks(Set<IscilikDetay> iscilikDetays) {
        this.isciliks = iscilikDetays;
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
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            "}";
    }
}
