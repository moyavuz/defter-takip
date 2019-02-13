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
 * A MalzemeGrubu.
 */
@Entity
@Table(name = "malzeme_grubu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MalzemeGrubu implements Serializable {

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

    @Column(name = "aktif_mi")
    private Boolean aktifMi;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "malzeme_grubu_malzeme",
               joinColumns = @JoinColumn(name = "malzeme_grubu_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "malzeme_id", referencedColumnName = "id"))
    private Set<Malzeme> malzemes = new HashSet<>();

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

    public MalzemeGrubu ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public MalzemeGrubu aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Boolean isAktifMi() {
        return aktifMi;
    }

    public MalzemeGrubu aktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
        return this;
    }

    public void setAktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
    }

    public Set<Malzeme> getMalzemes() {
        return malzemes;
    }

    public MalzemeGrubu malzemes(Set<Malzeme> malzemes) {
        this.malzemes = malzemes;
        return this;
    }

    public MalzemeGrubu addMalzeme(Malzeme malzeme) {
        this.malzemes.add(malzeme);
        malzeme.getGrups().add(this);
        return this;
    }

    public MalzemeGrubu removeMalzeme(Malzeme malzeme) {
        this.malzemes.remove(malzeme);
        malzeme.getGrups().remove(this);
        return this;
    }

    public void setMalzemes(Set<Malzeme> malzemes) {
        this.malzemes = malzemes;
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
        MalzemeGrubu malzemeGrubu = (MalzemeGrubu) o;
        if (malzemeGrubu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), malzemeGrubu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MalzemeGrubu{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", aktifMi='" + isAktifMi() + "'" +
            "}";
    }
}
