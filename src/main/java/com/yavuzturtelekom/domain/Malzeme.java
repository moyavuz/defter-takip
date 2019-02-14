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

    @ManyToOne
    @JsonIgnoreProperties("malzemeBirims")
    private Birim birim;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "malzeme_malzeme_iscilik",
               joinColumns = @JoinColumn(name = "malzeme_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "malzeme_iscilik_id", referencedColumnName = "id"))
    private Set<Iscilik> malzemeIsciliks = new HashSet<>();

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

    public Birim getBirim() {
        return birim;
    }

    public Malzeme birim(Birim birim) {
        this.birim = birim;
        return this;
    }

    public void setBirim(Birim birim) {
        this.birim = birim;
    }

    public Set<Iscilik> getMalzemeIsciliks() {
        return malzemeIsciliks;
    }

    public Malzeme malzemeIsciliks(Set<Iscilik> isciliks) {
        this.malzemeIsciliks = isciliks;
        return this;
    }

    public Malzeme addMalzemeIscilik(Iscilik iscilik) {
        this.malzemeIsciliks.add(iscilik);
        iscilik.getMalzemes().add(this);
        return this;
    }

    public Malzeme removeMalzemeIscilik(Iscilik iscilik) {
        this.malzemeIsciliks.remove(iscilik);
        iscilik.getMalzemes().remove(this);
        return this;
    }

    public void setMalzemeIsciliks(Set<Iscilik> isciliks) {
        this.malzemeIsciliks = isciliks;
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
