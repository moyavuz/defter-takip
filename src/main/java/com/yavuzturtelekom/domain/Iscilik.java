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

    @Column(name = "miktar")
    private Long miktar;

    @Lob
    @Column(name = "aciklama")
    private byte[] aciklama;

    @Column(name = "aciklama_content_type")
    private String aciklamaContentType;

    @Column(name = "aktif_mi")
    private Boolean aktifMi;

    @OneToOne
    @JoinColumn(unique = true)
    private Ekip calisanEkip;

    @OneToMany(mappedBy = "iscilik")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Poz> pozs = new HashSet<>();
    @OneToMany(mappedBy = "iscilik")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Malzeme> malzemes = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("isciliks")
    private Proje proje;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMiktar() {
        return miktar;
    }

    public Iscilik miktar(Long miktar) {
        this.miktar = miktar;
        return this;
    }

    public void setMiktar(Long miktar) {
        this.miktar = miktar;
    }

    public byte[] getAciklama() {
        return aciklama;
    }

    public Iscilik aciklama(byte[] aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(byte[] aciklama) {
        this.aciklama = aciklama;
    }

    public String getAciklamaContentType() {
        return aciklamaContentType;
    }

    public Iscilik aciklamaContentType(String aciklamaContentType) {
        this.aciklamaContentType = aciklamaContentType;
        return this;
    }

    public void setAciklamaContentType(String aciklamaContentType) {
        this.aciklamaContentType = aciklamaContentType;
    }

    public Boolean isAktifMi() {
        return aktifMi;
    }

    public Iscilik aktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
        return this;
    }

    public void setAktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
    }

    public Ekip getCalisanEkip() {
        return calisanEkip;
    }

    public Iscilik calisanEkip(Ekip ekip) {
        this.calisanEkip = ekip;
        return this;
    }

    public void setCalisanEkip(Ekip ekip) {
        this.calisanEkip = ekip;
    }

    public Set<Poz> getPozs() {
        return pozs;
    }

    public Iscilik pozs(Set<Poz> pozs) {
        this.pozs = pozs;
        return this;
    }

    public Iscilik addPoz(Poz poz) {
        this.pozs.add(poz);
        poz.setIscilik(this);
        return this;
    }

    public Iscilik removePoz(Poz poz) {
        this.pozs.remove(poz);
        poz.setIscilik(null);
        return this;
    }

    public void setPozs(Set<Poz> pozs) {
        this.pozs = pozs;
    }

    public Set<Malzeme> getMalzemes() {
        return malzemes;
    }

    public Iscilik malzemes(Set<Malzeme> malzemes) {
        this.malzemes = malzemes;
        return this;
    }

    public Iscilik addMalzeme(Malzeme malzeme) {
        this.malzemes.add(malzeme);
        malzeme.setIscilik(this);
        return this;
    }

    public Iscilik removeMalzeme(Malzeme malzeme) {
        this.malzemes.remove(malzeme);
        malzeme.setIscilik(null);
        return this;
    }

    public void setMalzemes(Set<Malzeme> malzemes) {
        this.malzemes = malzemes;
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
            ", miktar=" + getMiktar() +
            ", aciklama='" + getAciklama() + "'" +
            ", aciklamaContentType='" + getAciklamaContentType() + "'" +
            ", aktifMi='" + isAktifMi() + "'" +
            "}";
    }
}
