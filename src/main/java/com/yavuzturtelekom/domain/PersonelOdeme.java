package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.OdemeTuru;

import com.yavuzturtelekom.domain.enumeration.OdemeYontemi;

/**
 * A PersonelOdeme.
 */
@Entity
@Table(name = "personel_odeme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonelOdeme extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Column(name = "miktar")
    private Double miktar;

    @Enumerated(EnumType.STRING)
    @Column(name = "turu")
    private OdemeTuru turu;

    @Enumerated(EnumType.STRING)
    @Column(name = "yontem")
    private OdemeYontemi yontem;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("personelOdemes")
    private Personel personel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public PersonelOdeme tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public Double getMiktar() {
        return miktar;
    }

    public PersonelOdeme miktar(Double miktar) {
        this.miktar = miktar;
        return this;
    }

    public void setMiktar(Double miktar) {
        this.miktar = miktar;
    }

    public OdemeTuru getTuru() {
        return turu;
    }

    public PersonelOdeme turu(OdemeTuru turu) {
        this.turu = turu;
        return this;
    }

    public void setTuru(OdemeTuru turu) {
        this.turu = turu;
    }

    public OdemeYontemi getYontem() {
        return yontem;
    }

    public PersonelOdeme yontem(OdemeYontemi yontem) {
        this.yontem = yontem;
        return this;
    }

    public void setYontem(OdemeYontemi yontem) {
        this.yontem = yontem;
    }

    public Personel getPersonel() {
        return personel;
    }

    public PersonelOdeme personel(Personel personel) {
        this.personel = personel;
        return this;
    }

    public void setPersonel(Personel personel) {
        this.personel = personel;
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
        PersonelOdeme personelOdeme = (PersonelOdeme) o;
        if (personelOdeme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personelOdeme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonelOdeme{" +
            "id=" + getId() +
            ", tarih='" + getTarih() + "'" +
            ", miktar=" + getMiktar() +
            ", turu='" + getTuru() + "'" +
            ", yontem='" + getYontem() + "'" +
            "}";
    }
}
