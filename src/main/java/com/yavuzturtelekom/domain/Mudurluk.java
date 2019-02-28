package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Mudurluk.
 */
@Entity
@Table(name = "mudurluk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mudurluk implements Serializable {

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
    @JsonIgnoreProperties("mudurluks")
    private Personel mudurlukSorumlu;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mudurluks")
    private Il il;

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

    public Mudurluk ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAdres() {
        return adres;
    }

    public Mudurluk adres(String adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Personel getMudurlukSorumlu() {
        return mudurlukSorumlu;
    }

    public Mudurluk mudurlukSorumlu(Personel personel) {
        this.mudurlukSorumlu = personel;
        return this;
    }

    public void setMudurlukSorumlu(Personel personel) {
        this.mudurlukSorumlu = personel;
    }

    public Il getIl() {
        return il;
    }

    public Mudurluk il(Il il) {
        this.il = il;
        return this;
    }

    public void setIl(Il il) {
        this.il = il;
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
        Mudurluk mudurluk = (Mudurluk) o;
        if (mudurluk.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mudurluk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mudurluk{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", adres='" + getAdres() + "'" +
            "}";
    }
}
