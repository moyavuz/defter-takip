package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Santral.
 */
@Entity
@Table(name = "santral")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Santral implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ad", nullable = false)
    private String ad;

    @ManyToOne
    @JsonIgnoreProperties("santrals")
    private Mudurluk mudurluk;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("santrals")
    private Personel santralSorumlu;

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

    public Santral ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public Mudurluk getMudurluk() {
        return mudurluk;
    }

    public Santral mudurluk(Mudurluk mudurluk) {
        this.mudurluk = mudurluk;
        return this;
    }

    public void setMudurluk(Mudurluk mudurluk) {
        this.mudurluk = mudurluk;
    }

    public Personel getSantralSorumlu() {
        return santralSorumlu;
    }

    public Santral santralSorumlu(Personel personel) {
        this.santralSorumlu = personel;
        return this;
    }

    public void setSantralSorumlu(Personel personel) {
        this.santralSorumlu = personel;
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
        Santral santral = (Santral) o;
        if (santral.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), santral.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Santral{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            "}";
    }
}
