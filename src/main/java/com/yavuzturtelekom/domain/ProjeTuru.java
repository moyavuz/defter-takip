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
 * A ProjeTuru.
 */
@Entity
@Table(name = "proje_turu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjeTuru implements Serializable {

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

    @OneToMany(mappedBy = "projeTuru")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proje> turus = new HashSet<>();
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

    public ProjeTuru ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public ProjeTuru aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKisaltma() {
        return kisaltma;
    }

    public ProjeTuru kisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
        return this;
    }

    public void setKisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
    }

    public Set<Proje> getTurus() {
        return turus;
    }

    public ProjeTuru turus(Set<Proje> projes) {
        this.turus = projes;
        return this;
    }

    public ProjeTuru addTuru(Proje proje) {
        this.turus.add(proje);
        proje.setProjeTuru(this);
        return this;
    }

    public ProjeTuru removeTuru(Proje proje) {
        this.turus.remove(proje);
        proje.setProjeTuru(null);
        return this;
    }

    public void setTurus(Set<Proje> projes) {
        this.turus = projes;
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
        ProjeTuru projeTuru = (ProjeTuru) o;
        if (projeTuru.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projeTuru.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjeTuru{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", kisaltma='" + getKisaltma() + "'" +
            "}";
    }
}
