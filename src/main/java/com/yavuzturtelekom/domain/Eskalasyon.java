package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Eskalasyon.
 */
@Entity
@Table(name = "eskalasyon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Eskalasyon extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "deger", nullable = false)
    private Double deger;

    @NotNull
    @Column(name = "tarih", nullable = false)
    private LocalDate tarih;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("eskalasyons")
    private EskalasyonTuru turu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDeger() {
        return deger;
    }

    public Eskalasyon deger(Double deger) {
        this.deger = deger;
        return this;
    }

    public void setDeger(Double deger) {
        this.deger = deger;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public Eskalasyon tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public EskalasyonTuru getTuru() {
        return turu;
    }

    public Eskalasyon turu(EskalasyonTuru eskalasyonTuru) {
        this.turu = eskalasyonTuru;
        return this;
    }

    public void setTuru(EskalasyonTuru eskalasyonTuru) {
        this.turu = eskalasyonTuru;
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
        Eskalasyon eskalasyon = (Eskalasyon) o;
        if (eskalasyon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eskalasyon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Eskalasyon{" +
            "id=" + getId() +
            ", deger=" + getDeger() +
            ", tarih='" + getTarih() + "'" +
            "}";
    }
}
