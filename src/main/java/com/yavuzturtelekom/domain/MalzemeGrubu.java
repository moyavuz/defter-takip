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
public class MalzemeGrubu extends AbstractAuditingEntity implements Serializable {

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "malzeme_grubu_malzeme_listesi",
               joinColumns = @JoinColumn(name = "malzeme_grubu_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "malzeme_listesi_id", referencedColumnName = "id"))
    private Set<Malzeme> malzemeListesis = new HashSet<>();

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

    public Set<Malzeme> getMalzemeListesis() {
        return malzemeListesis;
    }

    public MalzemeGrubu malzemeListesis(Set<Malzeme> malzemes) {
        this.malzemeListesis = malzemes;
        return this;
    }

    public MalzemeGrubu addMalzemeListesi(Malzeme malzeme) {
        this.malzemeListesis.add(malzeme);
        malzeme.getGrups().add(this);
        return this;
    }

    public MalzemeGrubu removeMalzemeListesi(Malzeme malzeme) {
        this.malzemeListesis.remove(malzeme);
        malzeme.getGrups().remove(this);
        return this;
    }

    public void setMalzemeListesis(Set<Malzeme> malzemes) {
        this.malzemeListesis = malzemes;
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
            "}";
    }
}
