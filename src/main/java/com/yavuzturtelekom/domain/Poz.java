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

    @Column(name = "fiyat_telekom")
    private Double fiyatTelekom;

    @Column(name = "fiyat_girisim")
    private Double fiyatGirisim;

    @Column(name = "fiyat_taseron")
    private Double fiyatTaseron;

    @Column(name = "fiyat_tasere")
    private Double fiyatTasere;

    @Column(name = "kdv")
    private Double kdv;

    @Column(name = "malzeme_mi")
    private Boolean malzemeMi;

    @OneToMany(mappedBy = "poz")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IscilikDetay> iscilikDetays = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pozs")
    private Birim birim;

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

    public Double getFiyatTelekom() {
        return fiyatTelekom;
    }

    public Poz fiyatTelekom(Double fiyatTelekom) {
        this.fiyatTelekom = fiyatTelekom;
        return this;
    }

    public void setFiyatTelekom(Double fiyatTelekom) {
        this.fiyatTelekom = fiyatTelekom;
    }

    public Double getFiyatGirisim() {
        return fiyatGirisim;
    }

    public Poz fiyatGirisim(Double fiyatGirisim) {
        this.fiyatGirisim = fiyatGirisim;
        return this;
    }

    public void setFiyatGirisim(Double fiyatGirisim) {
        this.fiyatGirisim = fiyatGirisim;
    }

    public Double getFiyatTaseron() {
        return fiyatTaseron;
    }

    public Poz fiyatTaseron(Double fiyatTaseron) {
        this.fiyatTaseron = fiyatTaseron;
        return this;
    }

    public void setFiyatTaseron(Double fiyatTaseron) {
        this.fiyatTaseron = fiyatTaseron;
    }

    public Double getFiyatTasere() {
        return fiyatTasere;
    }

    public Poz fiyatTasere(Double fiyatTasere) {
        this.fiyatTasere = fiyatTasere;
        return this;
    }

    public void setFiyatTasere(Double fiyatTasere) {
        this.fiyatTasere = fiyatTasere;
    }

    public Double getKdv() {
        return kdv;
    }

    public Poz kdv(Double kdv) {
        this.kdv = kdv;
        return this;
    }

    public void setKdv(Double kdv) {
        this.kdv = kdv;
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

    public Set<IscilikDetay> getIscilikDetays() {
        return iscilikDetays;
    }

    public Poz iscilikDetays(Set<IscilikDetay> iscilikDetays) {
        this.iscilikDetays = iscilikDetays;
        return this;
    }

    public Poz addIscilikDetay(IscilikDetay iscilikDetay) {
        this.iscilikDetays.add(iscilikDetay);
        iscilikDetay.setPoz(this);
        return this;
    }

    public Poz removeIscilikDetay(IscilikDetay iscilikDetay) {
        this.iscilikDetays.remove(iscilikDetay);
        iscilikDetay.setPoz(null);
        return this;
    }

    public void setIscilikDetays(Set<IscilikDetay> iscilikDetays) {
        this.iscilikDetays = iscilikDetays;
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
            ", kdv=" + getKdv() +
            ", malzemeMi='" + isMalzemeMi() + "'" +
            "}";
    }
}
