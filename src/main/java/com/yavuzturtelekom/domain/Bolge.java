package com.yavuzturtelekom.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bolge.
 */
@Entity
@Table(name = "bolge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bolge implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "adi", nullable = false)
    private String adi;

    @Column(name = "adresi")
    private String adresi;

    @Column(name = "aktif_mi")
    private Boolean aktifMi;

    @OneToOne
    @JoinColumn(unique = true)
    private Calisan sorumlu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public Bolge adi(String adi) {
        this.adi = adi;
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getAdresi() {
        return adresi;
    }

    public Bolge adresi(String adresi) {
        this.adresi = adresi;
        return this;
    }

    public void setAdresi(String adresi) {
        this.adresi = adresi;
    }

    public Boolean isAktifMi() {
        return aktifMi;
    }

    public Bolge aktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
        return this;
    }

    public void setAktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
    }

    public Calisan getSorumlu() {
        return sorumlu;
    }

    public Bolge sorumlu(Calisan calisan) {
        this.sorumlu = calisan;
        return this;
    }

    public void setSorumlu(Calisan calisan) {
        this.sorumlu = calisan;
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
        Bolge bolge = (Bolge) o;
        if (bolge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bolge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bolge{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", adresi='" + getAdresi() + "'" +
            ", aktifMi='" + isAktifMi() + "'" +
            "}";
    }
}
