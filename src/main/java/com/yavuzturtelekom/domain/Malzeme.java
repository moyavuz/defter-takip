package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Malzeme.
 */
@Entity
@Table(name = "malzeme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Malzeme implements Serializable {

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

    @Column(name = "fiyat_maliyet", precision = 10, scale = 2)
    private BigDecimal fiyatMaliyet;

    @OneToMany(mappedBy = "malzeme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Iscilik> isciliks = new HashSet<>();
    @OneToMany(mappedBy = "malzeme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Birim> birims = new HashSet<>();
    @OneToOne(mappedBy = "malzeme")
    @JsonIgnore
    private Poz poz;

    @ManyToMany(mappedBy = "malzemes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<MalzemeGrubu> grups = new HashSet<>();

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

    public Malzeme ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Malzeme aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKisaltma() {
        return kisaltma;
    }

    public Malzeme kisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
        return this;
    }

    public void setKisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
    }

    public BigDecimal getFiyatMaliyet() {
        return fiyatMaliyet;
    }

    public Malzeme fiyatMaliyet(BigDecimal fiyatMaliyet) {
        this.fiyatMaliyet = fiyatMaliyet;
        return this;
    }

    public void setFiyatMaliyet(BigDecimal fiyatMaliyet) {
        this.fiyatMaliyet = fiyatMaliyet;
    }

    public Set<Iscilik> getIsciliks() {
        return isciliks;
    }

    public Malzeme isciliks(Set<Iscilik> isciliks) {
        this.isciliks = isciliks;
        return this;
    }

    public Malzeme addIscilik(Iscilik iscilik) {
        this.isciliks.add(iscilik);
        iscilik.setMalzeme(this);
        return this;
    }

    public Malzeme removeIscilik(Iscilik iscilik) {
        this.isciliks.remove(iscilik);
        iscilik.setMalzeme(null);
        return this;
    }

    public void setIsciliks(Set<Iscilik> isciliks) {
        this.isciliks = isciliks;
    }

    public Set<Birim> getBirims() {
        return birims;
    }

    public Malzeme birims(Set<Birim> birims) {
        this.birims = birims;
        return this;
    }

    public Malzeme addBirim(Birim birim) {
        this.birims.add(birim);
        birim.setMalzeme(this);
        return this;
    }

    public Malzeme removeBirim(Birim birim) {
        this.birims.remove(birim);
        birim.setMalzeme(null);
        return this;
    }

    public void setBirims(Set<Birim> birims) {
        this.birims = birims;
    }

    public Poz getPoz() {
        return poz;
    }

    public Malzeme poz(Poz poz) {
        this.poz = poz;
        return this;
    }

    public void setPoz(Poz poz) {
        this.poz = poz;
    }

    public Set<MalzemeGrubu> getGrups() {
        return grups;
    }

    public Malzeme grups(Set<MalzemeGrubu> malzemeGrubus) {
        this.grups = malzemeGrubus;
        return this;
    }

    public Malzeme addGrup(MalzemeGrubu malzemeGrubu) {
        this.grups.add(malzemeGrubu);
        malzemeGrubu.getMalzemes().add(this);
        return this;
    }

    public Malzeme removeGrup(MalzemeGrubu malzemeGrubu) {
        this.grups.remove(malzemeGrubu);
        malzemeGrubu.getMalzemes().remove(this);
        return this;
    }

    public void setGrups(Set<MalzemeGrubu> malzemeGrubus) {
        this.grups = malzemeGrubus;
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
        Malzeme malzeme = (Malzeme) o;
        if (malzeme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), malzeme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Malzeme{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", kisaltma='" + getKisaltma() + "'" +
            ", fiyatMaliyet=" + getFiyatMaliyet() +
            "}";
    }
}
