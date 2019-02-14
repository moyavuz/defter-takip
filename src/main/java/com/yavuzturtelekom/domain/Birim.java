package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Birim.
 */
@Entity
@Table(name = "birim")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Birim implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "aciklama")
    private String aciklama;

    @Column(name = "kisaltma")
    private String kisaltma;

    @Column(name = "carpan")
    private Long carpan;

    @OneToMany(mappedBy = "birim")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Malzeme> birims = new HashSet<>();
    @OneToMany(mappedBy = "birim")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Iscilik> birims = new HashSet<>();
    @OneToMany(mappedBy = "birim")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Poz> birims = new HashSet<>();
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

    public Birim ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Birim aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKisaltma() {
        return kisaltma;
    }

    public Birim kisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
        return this;
    }

    public void setKisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
    }

    public Long getCarpan() {
        return carpan;
    }

    public Birim carpan(Long carpan) {
        this.carpan = carpan;
        return this;
    }

    public void setCarpan(Long carpan) {
        this.carpan = carpan;
    }

    public Set<Malzeme> getBirims() {
        return birims;
    }

    public Birim birims(Set<Malzeme> malzemes) {
        this.birims = malzemes;
        return this;
    }

    public Birim addBirim(Malzeme malzeme) {
        this.birims.add(malzeme);
        malzeme.setBirim(this);
        return this;
    }

    public Birim removeBirim(Malzeme malzeme) {
        this.birims.remove(malzeme);
        malzeme.setBirim(null);
        return this;
    }

    public void setBirims(Set<Malzeme> malzemes) {
        this.birims = malzemes;
    }

    public Set<Iscilik> getBirims() {
        return birims;
    }

    public Birim birims(Set<Iscilik> isciliks) {
        this.birims = isciliks;
        return this;
    }

    public Birim addBirim(Iscilik iscilik) {
        this.birims.add(iscilik);
        iscilik.setBirim(this);
        return this;
    }

    public Birim removeBirim(Iscilik iscilik) {
        this.birims.remove(iscilik);
        iscilik.setBirim(null);
        return this;
    }

    public void setBirims(Set<Iscilik> isciliks) {
        this.birims = isciliks;
    }

    public Set<Poz> getBirims() {
        return birims;
    }

    public Birim birims(Set<Poz> pozs) {
        this.birims = pozs;
        return this;
    }

    public Birim addBirim(Poz poz) {
        this.birims.add(poz);
        poz.setBirim(this);
        return this;
    }

    public Birim removeBirim(Poz poz) {
        this.birims.remove(poz);
        poz.setBirim(null);
        return this;
    }

    public void setBirims(Set<Poz> pozs) {
        this.birims = pozs;
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
        Birim birim = (Birim) o;
        if (birim.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), birim.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Birim{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", kisaltma='" + getKisaltma() + "'" +
            ", carpan=" + getCarpan() +
            "}";
    }
}
