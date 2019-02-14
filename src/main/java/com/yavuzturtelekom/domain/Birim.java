package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties("birims")
    private Malzeme malzeme;

    @ManyToOne
    @JsonIgnoreProperties("birims")
    private Poz poz;

    @ManyToOne
    @JsonIgnoreProperties("birims")
    private Iscilik iscilik;

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

    public Malzeme getMalzeme() {
        return malzeme;
    }

    public Birim malzeme(Malzeme malzeme) {
        this.malzeme = malzeme;
        return this;
    }

    public void setMalzeme(Malzeme malzeme) {
        this.malzeme = malzeme;
    }

    public Poz getPoz() {
        return poz;
    }

    public Birim poz(Poz poz) {
        this.poz = poz;
        return this;
    }

    public void setPoz(Poz poz) {
        this.poz = poz;
    }

    public Iscilik getIscilik() {
        return iscilik;
    }

    public Birim iscilik(Iscilik iscilik) {
        this.iscilik = iscilik;
        return this;
    }

    public void setIscilik(Iscilik iscilik) {
        this.iscilik = iscilik;
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
