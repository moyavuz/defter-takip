package com.yavuzturtelekom.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PozGrubu.
 */
@Entity
@Table(name = "poz_grubu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PozGrubu implements Serializable {

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "poz_grubu_poz_listesi",
               joinColumns = @JoinColumn(name = "poz_grubu_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "poz_listesi_id", referencedColumnName = "id"))
    private Set<Poz> pozListesis = new HashSet<>();

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

    public PozGrubu ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public PozGrubu aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKisaltma() {
        return kisaltma;
    }

    public PozGrubu kisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
        return this;
    }

    public void setKisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
    }

    public Set<Poz> getPozListesis() {
        return pozListesis;
    }

    public PozGrubu pozListesis(Set<Poz> pozs) {
        this.pozListesis = pozs;
        return this;
    }

    public PozGrubu addPozListesi(Poz poz) {
        this.pozListesis.add(poz);
        poz.getGrups().add(this);
        return this;
    }

    public PozGrubu removePozListesi(Poz poz) {
        this.pozListesis.remove(poz);
        poz.getGrups().remove(this);
        return this;
    }

    public void setPozListesis(Set<Poz> pozs) {
        this.pozListesis = pozs;
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
        PozGrubu pozGrubu = (PozGrubu) o;
        if (pozGrubu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pozGrubu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PozGrubu{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", kisaltma='" + getKisaltma() + "'" +
            "}";
    }
}
