package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Ekip.
 */
@Entity
@Table(name = "ekip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ekip implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "eposta")
    private String eposta;

    @Column(name = "taseron_mu")
    private Boolean taseronMu;

    @OneToOne
    @JoinColumn(unique = true)
    private Calisan sorumlu;

    @ManyToOne
    @JsonIgnoreProperties("ekips")
    private Calisan calisan;

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

    public Ekip ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getTelefon() {
        return telefon;
    }

    public Ekip telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEposta() {
        return eposta;
    }

    public Ekip eposta(String eposta) {
        this.eposta = eposta;
        return this;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public Boolean isTaseronMu() {
        return taseronMu;
    }

    public Ekip taseronMu(Boolean taseronMu) {
        this.taseronMu = taseronMu;
        return this;
    }

    public void setTaseronMu(Boolean taseronMu) {
        this.taseronMu = taseronMu;
    }

    public Calisan getSorumlu() {
        return sorumlu;
    }

    public Ekip sorumlu(Calisan calisan) {
        this.sorumlu = calisan;
        return this;
    }

    public void setSorumlu(Calisan calisan) {
        this.sorumlu = calisan;
    }

    public Calisan getCalisan() {
        return calisan;
    }

    public Ekip calisan(Calisan calisan) {
        this.calisan = calisan;
        return this;
    }

    public void setCalisan(Calisan calisan) {
        this.calisan = calisan;
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
        Ekip ekip = (Ekip) o;
        if (ekip.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ekip.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ekip{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", taseronMu='" + isTaseronMu() + "'" +
            "}";
    }
}
