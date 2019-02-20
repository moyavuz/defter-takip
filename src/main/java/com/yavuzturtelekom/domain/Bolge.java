package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bolge.
 */
@Entity
@Table(name = "bolge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bolge implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "adres")
    private String adres;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("bolges")
    private Personel bolgeSorumlu;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("bolges")
    private Departman bolge;

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

    public Bolge ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAdres() {
        return adres;
    }

    public Bolge adres(String adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Personel getBolgeSorumlu() {
        return bolgeSorumlu;
    }

    public Bolge bolgeSorumlu(Personel personel) {
        this.bolgeSorumlu = personel;
        return this;
    }

    public void setBolgeSorumlu(Personel personel) {
        this.bolgeSorumlu = personel;
    }

    public Departman getBolge() {
        return bolge;
    }

    public Bolge bolge(Departman departman) {
        this.bolge = departman;
        return this;
    }

    public void setBolge(Departman departman) {
        this.bolge = departman;
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
        Bolge bolge = (Bolge) o;
        if (bolge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bolge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bolge{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", adres='" + getAdres() + "'" +
            "}";
    }
}
