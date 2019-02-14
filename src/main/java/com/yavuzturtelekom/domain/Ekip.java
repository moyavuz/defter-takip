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

    @OneToMany(mappedBy = "calisanEkip")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Iscilik> calisanEkips = new HashSet<>();
    @OneToMany(mappedBy = "ekip")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MalzemeTakip> ekipMalzemeTakips = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ekip_ekip_calisan",
               joinColumns = @JoinColumn(name = "ekip_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ekip_calisan_id", referencedColumnName = "id"))
    private Set<Calisan> ekipCalisans = new HashSet<>();

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
        iscilik.setCalisanEkip(this);
        return this;
    }

    public Ekip removeCalisanEkip(Iscilik iscilik) {
        this.calisanEkips.remove(iscilik);
        iscilik.setCalisanEkip(null);
        return this;
    }

    public void setCalisanEkips(Set<Iscilik> isciliks) {
        this.calisanEkips = isciliks;
    }

    public Set<MalzemeTakip> getEkipMalzemeTakips() {
        return ekipMalzemeTakips;
    }

    public Ekip ekipMalzemeTakips(Set<MalzemeTakip> malzemeTakips) {
        this.ekipMalzemeTakips = malzemeTakips;
        return this;
    }

    public Ekip addEkipMalzemeTakip(MalzemeTakip malzemeTakip) {
        this.ekipMalzemeTakips.add(malzemeTakip);
        malzemeTakip.setEkip(this);
        return this;
    }

    public Ekip removeEkipMalzemeTakip(MalzemeTakip malzemeTakip) {
        this.ekipMalzemeTakips.remove(malzemeTakip);
        malzemeTakip.setEkip(null);
        return this;
    }

    public void setEkipMalzemeTakips(Set<MalzemeTakip> malzemeTakips) {
        this.ekipMalzemeTakips = malzemeTakips;
    }

    public Set<Calisan> getEkipCalisans() {
        return ekipCalisans;
    }

    public Ekip ekipCalisans(Set<Calisan> calisans) {
        this.ekipCalisans = calisans;
        return this;
    }

    public Ekip addEkipCalisan(Calisan calisan) {
        this.ekipCalisans.add(calisan);
        calisan.getEkips().add(this);
        return this;
    }

    public Ekip removeEkipCalisan(Calisan calisan) {
        this.ekipCalisans.remove(calisan);
        calisan.getEkips().remove(this);
        return this;
    }

    public void setEkipCalisans(Set<Calisan> calisans) {
        this.ekipCalisans = calisans;
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
