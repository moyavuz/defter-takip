package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.DepoTuru;

/**
 * A Depo.
 */
@Entity
@Table(name = "depo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Depo implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "turu")
    private DepoTuru turu;

    @OneToMany(mappedBy = "depo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StokTakip> stokTakips = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("depos")
    private Personel sorumlu;

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

    public Depo ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAdres() {
        return adres;
    }

    public Depo adres(String adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public DepoTuru getTuru() {
        return turu;
    }

    public Depo turu(DepoTuru turu) {
        this.turu = turu;
        return this;
    }

    public void setTuru(DepoTuru turu) {
        this.turu = turu;
    }

    public Set<StokTakip> getStokTakips() {
        return stokTakips;
    }

    public Depo stokTakips(Set<StokTakip> stokTakips) {
        this.stokTakips = stokTakips;
        return this;
    }

    public Depo addStokTakip(StokTakip stokTakip) {
        this.stokTakips.add(stokTakip);
        stokTakip.setDepo(this);
        return this;
    }

    public Depo removeStokTakip(StokTakip stokTakip) {
        this.stokTakips.remove(stokTakip);
        stokTakip.setDepo(null);
        return this;
    }

    public void setStokTakips(Set<StokTakip> stokTakips) {
        this.stokTakips = stokTakips;
    }

    public Personel getSorumlu() {
        return sorumlu;
    }

    public Depo sorumlu(Personel personel) {
        this.sorumlu = personel;
        return this;
    }

    public void setSorumlu(Personel personel) {
        this.sorumlu = personel;
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
        Depo depo = (Depo) o;
        if (depo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Depo{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", adres='" + getAdres() + "'" +
            ", turu='" + getTuru() + "'" +
            "}";
    }
}
