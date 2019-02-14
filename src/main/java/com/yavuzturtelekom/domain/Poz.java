package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Poz.
 */
@Entity
@Table(name = "poz")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Poz implements Serializable {

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

    @Column(name = "yil")
    private Integer yil;

    @Column(name = "fiyat_telekom", precision = 10, scale = 2)
    private BigDecimal fiyatTelekom;

    @Column(name = "fiyat_girisim", precision = 10, scale = 2)
    private BigDecimal fiyatGirisim;

    @Column(name = "fiyat_taseron", precision = 10, scale = 2)
    private BigDecimal fiyatTaseron;

    @Column(name = "fiyat_tasere", precision = 10, scale = 2)
    private BigDecimal fiyatTasere;

    @Column(name = "kdv_oran")
    private Double kdvOran;

    @Column(name = "malzeme_mi")
    private Boolean malzemeMi;

    @ManyToOne
    @JsonIgnoreProperties("pozBirims")
    private Birim birim;

    @OneToMany(mappedBy = "poz")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Iscilik> pozs = new HashSet<>();
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

    public Poz ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Poz aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKisaltma() {
        return kisaltma;
    }

    public Poz kisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
        return this;
    }

    public void setKisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
    }

    public Integer getYil() {
        return yil;
    }

    public Poz yil(Integer yil) {
        this.yil = yil;
        return this;
    }

    public void setYil(Integer yil) {
        this.yil = yil;
    }

    public BigDecimal getFiyatTelekom() {
        return fiyatTelekom;
    }

    public Poz fiyatTelekom(BigDecimal fiyatTelekom) {
        this.fiyatTelekom = fiyatTelekom;
        return this;
    }

    public void setFiyatTelekom(BigDecimal fiyatTelekom) {
        this.fiyatTelekom = fiyatTelekom;
    }

    public BigDecimal getFiyatGirisim() {
        return fiyatGirisim;
    }

    public Poz fiyatGirisim(BigDecimal fiyatGirisim) {
        this.fiyatGirisim = fiyatGirisim;
        return this;
    }

    public void setFiyatGirisim(BigDecimal fiyatGirisim) {
        this.fiyatGirisim = fiyatGirisim;
    }

    public BigDecimal getFiyatTaseron() {
        return fiyatTaseron;
    }

    public Poz fiyatTaseron(BigDecimal fiyatTaseron) {
        this.fiyatTaseron = fiyatTaseron;
        return this;
    }

    public void setFiyatTaseron(BigDecimal fiyatTaseron) {
        this.fiyatTaseron = fiyatTaseron;
    }

    public BigDecimal getFiyatTasere() {
        return fiyatTasere;
    }

    public Poz fiyatTasere(BigDecimal fiyatTasere) {
        this.fiyatTasere = fiyatTasere;
        return this;
    }

    public void setFiyatTasere(BigDecimal fiyatTasere) {
        this.fiyatTasere = fiyatTasere;
    }

    public Double getKdvOran() {
        return kdvOran;
    }

    public Poz kdvOran(Double kdvOran) {
        this.kdvOran = kdvOran;
        return this;
    }

    public void setKdvOran(Double kdvOran) {
        this.kdvOran = kdvOran;
    }

    public Boolean isMalzemeMi() {
        return malzemeMi;
    }

    public Poz malzemeMi(Boolean malzemeMi) {
        this.malzemeMi = malzemeMi;
        return this;
    }

    public void setMalzemeMi(Boolean malzemeMi) {
        this.malzemeMi = malzemeMi;
    }

    public Birim getBirim() {
        return birim;
    }

    public Poz birim(Birim birim) {
        this.birim = birim;
        return this;
    }

    public void setBirim(Birim birim) {
        this.birim = birim;
    }

    public Set<Iscilik> getPozs() {
        return pozs;
    }

    public Poz pozs(Set<Iscilik> isciliks) {
        this.pozs = isciliks;
        return this;
    }

    public Poz addPoz(Iscilik iscilik) {
        this.pozs.add(iscilik);
        iscilik.setPoz(this);
        return this;
    }

    public Poz removePoz(Iscilik iscilik) {
        this.pozs.remove(iscilik);
        iscilik.setPoz(null);
        return this;
    }

    public void setPozs(Set<Iscilik> isciliks) {
        this.pozs = isciliks;
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
        Poz poz = (Poz) o;
        if (poz.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poz.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Poz{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", kisaltma='" + getKisaltma() + "'" +
            ", yil=" + getYil() +
            ", fiyatTelekom=" + getFiyatTelekom() +
            ", fiyatGirisim=" + getFiyatGirisim() +
            ", fiyatTaseron=" + getFiyatTaseron() +
            ", fiyatTasere=" + getFiyatTasere() +
            ", kdvOran=" + getKdvOran() +
            ", malzemeMi='" + isMalzemeMi() + "'" +
            "}";
    }
}
