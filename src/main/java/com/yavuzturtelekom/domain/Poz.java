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
public class Poz extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "tenzilatsiz_fiyat")
    private Double tenzilatsizFiyat;

    @Column(name = "tenzilatli_fiyat")
    private Double tenzilatliFiyat;

    @Column(name = "taseron_fiyat")
    private Double taseronFiyat;

    @Column(name = "tasere_fiyat")
    private Double tasereFiyat;

    @Column(name = "malzeme_mi")
    private Boolean malzemeMi;

    @OneToMany(mappedBy = "poz")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HakedisDetay> hakedisDetays = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pozs")
    private Birim birim;

    @ManyToMany(mappedBy = "pozListesis")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<PozGrubu> grups = new HashSet<>();

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

    public Double getTenzilatsizFiyat() {
        return tenzilatsizFiyat;
    }

    public Poz tenzilatsizFiyat(Double tenzilatsizFiyat) {
        this.tenzilatsizFiyat = tenzilatsizFiyat;
        return this;
    }

    public void setTenzilatsizFiyat(Double tenzilatsizFiyat) {
        this.tenzilatsizFiyat = tenzilatsizFiyat;
    }

    public Double getTenzilatliFiyat() {
        return tenzilatliFiyat;
    }

    public Poz tenzilatliFiyat(Double tenzilatliFiyat) {
        this.tenzilatliFiyat = tenzilatliFiyat;
        return this;
    }

    public void setTenzilatliFiyat(Double tenzilatliFiyat) {
        this.tenzilatliFiyat = tenzilatliFiyat;
    }

    public Double getTaseronFiyat() {
        return taseronFiyat;
    }

    public Poz taseronFiyat(Double taseronFiyat) {
        this.taseronFiyat = taseronFiyat;
        return this;
    }

    public void setTaseronFiyat(Double taseronFiyat) {
        this.taseronFiyat = taseronFiyat;
    }

    public Double getTasereFiyat() {
        return tasereFiyat;
    }

    public Poz tasereFiyat(Double tasereFiyat) {
        this.tasereFiyat = tasereFiyat;
        return this;
    }

    public void setTasereFiyat(Double tasereFiyat) {
        this.tasereFiyat = tasereFiyat;
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

    public Set<HakedisDetay> getHakedisDetays() {
        return hakedisDetays;
    }

    public Poz hakedisDetays(Set<HakedisDetay> hakedisDetays) {
        this.hakedisDetays = hakedisDetays;
        return this;
    }

    public Poz addHakedisDetay(HakedisDetay hakedisDetay) {
        this.hakedisDetays.add(hakedisDetay);
        hakedisDetay.setPoz(this);
        return this;
    }

    public Poz removeHakedisDetay(HakedisDetay hakedisDetay) {
        this.hakedisDetays.remove(hakedisDetay);
        hakedisDetay.setPoz(null);
        return this;
    }

    public void setHakedisDetays(Set<HakedisDetay> hakedisDetays) {
        this.hakedisDetays = hakedisDetays;
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

    public Set<PozGrubu> getGrups() {
        return grups;
    }

    public Poz grups(Set<PozGrubu> pozGrubus) {
        this.grups = pozGrubus;
        return this;
    }

    public Poz addGrup(PozGrubu pozGrubu) {
        this.grups.add(pozGrubu);
        pozGrubu.getPozListesis().add(this);
        return this;
    }

    public Poz removeGrup(PozGrubu pozGrubu) {
        this.grups.remove(pozGrubu);
        pozGrubu.getPozListesis().remove(this);
        return this;
    }

    public void setGrups(Set<PozGrubu> pozGrubus) {
        this.grups = pozGrubus;
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
            ", tenzilatsizFiyat=" + getTenzilatsizFiyat() +
            ", tenzilatliFiyat=" + getTenzilatliFiyat() +
            ", taseronFiyat=" + getTaseronFiyat() +
            ", tasereFiyat=" + getTasereFiyat() +
            ", malzemeMi='" + isMalzemeMi() + "'" +
            "}";
    }
}
