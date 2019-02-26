package com.yavuzturtelekom.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ZimmetTuru.
 */
@Entity
@Table(name = "zimmet_turu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ZimmetTuru extends AbstractAuditingEntity implements Serializable {

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

    public ZimmetTuru ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public ZimmetTuru aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
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
        ZimmetTuru zimmetTuru = (ZimmetTuru) o;
        if (zimmetTuru.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zimmetTuru.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZimmetTuru{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            "}";
    }
}
