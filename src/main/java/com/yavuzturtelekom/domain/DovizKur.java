package com.yavuzturtelekom.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.ParaBirimi;

/**
 * A DovizKur.
 */
@Entity
@Table(name = "doviz_kur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DovizKur extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "para_birimi")
    private ParaBirimi paraBirimi;

    @Column(name = "deger")
    private Double deger;

    @Column(name = "tarih")
    private LocalDate tarih;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParaBirimi getParaBirimi() {
        return paraBirimi;
    }

    public DovizKur paraBirimi(ParaBirimi paraBirimi) {
        this.paraBirimi = paraBirimi;
        return this;
    }

    public void setParaBirimi(ParaBirimi paraBirimi) {
        this.paraBirimi = paraBirimi;
    }

    public Double getDeger() {
        return deger;
    }

    public DovizKur deger(Double deger) {
        this.deger = deger;
        return this;
    }

    public void setDeger(Double deger) {
        this.deger = deger;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public DovizKur tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
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
        DovizKur dovizKur = (DovizKur) o;
        if (dovizKur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dovizKur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DovizKur{" +
            "id=" + getId() +
            ", paraBirimi='" + getParaBirimi() + "'" +
            ", deger=" + getDeger() +
            ", tarih='" + getTarih() + "'" +
            "}";
    }
}
