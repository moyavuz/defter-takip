package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A IscilikDetay.
 */
@Entity
@Table(name = "iscilik_detay")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IscilikDetay implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "miktar", nullable = false)
    private Long miktar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("isciliks")
    private Iscilik iscilik;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pozs")
    private Poz poz;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMiktar() {
        return miktar;
    }

    public IscilikDetay miktar(Long miktar) {
        this.miktar = miktar;
        return this;
    }

    public void setMiktar(Long miktar) {
        this.miktar = miktar;
    }

    public Iscilik getIscilik() {
        return iscilik;
    }

    public IscilikDetay iscilik(Iscilik iscilik) {
        this.iscilik = iscilik;
        return this;
    }

    public void setIscilik(Iscilik iscilik) {
        this.iscilik = iscilik;
    }

    public Poz getPoz() {
        return poz;
    }

    public IscilikDetay poz(Poz poz) {
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
        IscilikDetay iscilikDetay = (IscilikDetay) o;
        if (iscilikDetay.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iscilikDetay.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IscilikDetay{" +
            "id=" + getId() +
            ", miktar=" + getMiktar() +
            "}";
    }
}
