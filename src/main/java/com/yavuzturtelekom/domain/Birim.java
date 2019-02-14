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
    private Set<Malzeme> malzemeBirims = new HashSet<>();
    @OneToMany(mappedBy = "birim")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Poz> pozBirims = new HashSet<>();
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

    public Set<Malzeme> getMalzemeBirims() {
        return malzemeBirims;
    }

    public Birim malzemeBirims(Set<Malzeme> malzemes) {
        this.malzemeBirims = malzemes;
        return this;
    }

    public Birim addMalzemeBirim(Malzeme malzeme) {
        this.malzemeBirims.add(malzeme);
        malzeme.setBirim(this);
        return this;
    }

    public Birim removeMalzemeBirim(Malzeme malzeme) {
        this.malzemeBirims.remove(malzeme);
        malzeme.setBirim(null);
        return this;
    }

    public void setMalzemeBirims(Set<Malzeme> malzemes) {
        this.malzemeBirims = malzemes;
    }

    public Set<Poz> getPozBirims() {
        return pozBirims;
    }

    public Birim pozBirims(Set<Poz> pozs) {
        this.pozBirims = pozs;
        return this;
    }

    public Birim addPozBirim(Poz poz) {
        this.pozBirims.add(poz);
        poz.setBirim(this);
        return this;
    }

    public Birim removePozBirim(Poz poz) {
        this.pozBirims.remove(poz);
        poz.setBirim(null);
        return this;
    }

    public void setPozBirims(Set<Poz> pozs) {
        this.pozBirims = pozs;
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
