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

    @OneToMany(mappedBy = "ekip")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Iscilik> calisanEkips = new HashSet<>();
    @OneToMany(mappedBy = "ekip")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Calisan> sorumlus = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ekip_calisan",
               joinColumns = @JoinColumn(name = "ekip_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "calisan_id", referencedColumnName = "id"))
    private Set<Calisan> calisans = new HashSet<>();

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

    public Set<Iscilik> getCalisanEkips() {
        return calisanEkips;
    }

    public Ekip calisanEkips(Set<Iscilik> isciliks) {
        this.calisanEkips = isciliks;
        return this;
    }

    public Ekip addCalisanEkip(Iscilik iscilik) {
        this.calisanEkips.add(iscilik);
        iscilik.setEkip(this);
        return this;
    }

    public Ekip removeCalisanEkip(Iscilik iscilik) {
        this.calisanEkips.remove(iscilik);
        iscilik.setEkip(null);
        return this;
    }

    public void setCalisanEkips(Set<Iscilik> isciliks) {
        this.calisanEkips = isciliks;
    }

    public Set<Calisan> getSorumlus() {
        return sorumlus;
    }

    public Ekip sorumlus(Set<Calisan> calisans) {
        this.sorumlus = calisans;
        return this;
    }

    public Ekip addSorumlu(Calisan calisan) {
        this.sorumlus.add(calisan);
        calisan.setEkip(this);
        return this;
    }

    public Ekip removeSorumlu(Calisan calisan) {
        this.sorumlus.remove(calisan);
        calisan.setEkip(null);
        return this;
    }

    public void setSorumlus(Set<Calisan> calisans) {
        this.sorumlus = calisans;
    }

    public Set<Calisan> getCalisans() {
        return calisans;
    }

    public Ekip calisans(Set<Calisan> calisans) {
        this.calisans = calisans;
        return this;
    }

    public Ekip addCalisan(Calisan calisan) {
        this.calisans.add(calisan);
        calisan.getEkips().add(this);
        return this;
    }

    public Ekip removeCalisan(Calisan calisan) {
        this.calisans.remove(calisan);
        calisan.getEkips().remove(this);
        return this;
    }

    public void setCalisans(Set<Calisan> calisans) {
        this.calisans = calisans;
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
