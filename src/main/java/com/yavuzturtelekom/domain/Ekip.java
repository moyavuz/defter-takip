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

import com.yavuzturtelekom.domain.enumeration.CalismaTuru;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "calisma_turu")
    private CalismaTuru calismaTuru;

    @OneToMany(mappedBy = "ekip")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Iscilik> isciliks = new HashSet<>();
    @OneToMany(mappedBy = "ekip")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MalzemeTakip> malzemeTakips = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("ekips")
    private Calisan ekipSorumlu;

    @ManyToOne
    @JsonIgnoreProperties("ekips")
    private Bolge bolge;

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

    public CalismaTuru getCalismaTuru() {
        return calismaTuru;
    }

    public Ekip calismaTuru(CalismaTuru calismaTuru) {
        this.calismaTuru = calismaTuru;
        return this;
    }

    public void setCalismaTuru(CalismaTuru calismaTuru) {
        this.calismaTuru = calismaTuru;
    }

    public Set<Iscilik> getIsciliks() {
        return isciliks;
    }

    public Ekip isciliks(Set<Iscilik> isciliks) {
        this.isciliks = isciliks;
        return this;
    }

    public Ekip addIscilik(Iscilik iscilik) {
        this.isciliks.add(iscilik);
        iscilik.setEkip(this);
        return this;
    }

    public Ekip removeIscilik(Iscilik iscilik) {
        this.isciliks.remove(iscilik);
        iscilik.setEkip(null);
        return this;
    }

    public void setIsciliks(Set<Iscilik> isciliks) {
        this.isciliks = isciliks;
    }

    public Set<MalzemeTakip> getMalzemeTakips() {
        return malzemeTakips;
    }

    public Ekip malzemeTakips(Set<MalzemeTakip> malzemeTakips) {
        this.malzemeTakips = malzemeTakips;
        return this;
    }

    public Ekip addMalzemeTakip(MalzemeTakip malzemeTakip) {
        this.malzemeTakips.add(malzemeTakip);
        malzemeTakip.setEkip(this);
        return this;
    }

    public Ekip removeMalzemeTakip(MalzemeTakip malzemeTakip) {
        this.malzemeTakips.remove(malzemeTakip);
        malzemeTakip.setEkip(null);
        return this;
    }

    public void setMalzemeTakips(Set<MalzemeTakip> malzemeTakips) {
        this.malzemeTakips = malzemeTakips;
    }

    public Calisan getEkipSorumlu() {
        return ekipSorumlu;
    }

    public Ekip ekipSorumlu(Calisan calisan) {
        this.ekipSorumlu = calisan;
        return this;
    }

    public void setEkipSorumlu(Calisan calisan) {
        this.ekipSorumlu = calisan;
    }

    public Bolge getBolge() {
        return bolge;
    }

    public Ekip bolge(Bolge bolge) {
        this.bolge = bolge;
        return this;
    }

    public void setBolge(Bolge bolge) {
        this.bolge = bolge;
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
            ", calismaTuru='" + getCalismaTuru() + "'" +
            "}";
    }
}
