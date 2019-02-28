package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A HakedisDetay.
 */
@Entity
@Table(name = "hakedis_detay")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HakedisDetay implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "miktar", nullable = false)
    private Double miktar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("hakedisDetays")
    private Hakedis hakedis;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("hakedisDetays")
    private Poz poz;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMiktar() {
        return miktar;
    }

    public HakedisDetay miktar(Double miktar) {
        this.miktar = miktar;
        return this;
    }

    public void setMiktar(Double miktar) {
        this.miktar = miktar;
    }

    public Hakedis getHakedis() {
        return hakedis;
    }

    public HakedisDetay hakedis(Hakedis hakedis) {
        this.hakedis = hakedis;
        return this;
    }

    public void setHakedis(Hakedis hakedis) {
        this.hakedis = hakedis;
    }

    public Poz getPoz() {
        return poz;
    }

    public HakedisDetay poz(Poz poz) {
        this.poz = poz;
        return this;
    }

    public void setPoz(Poz poz) {
        this.poz = poz;
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
        HakedisDetay hakedisDetay = (HakedisDetay) o;
        if (hakedisDetay.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hakedisDetay.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HakedisDetay{" +
            "id=" + getId() +
            ", miktar=" + getMiktar() +
            "}";
    }
}
