package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.StokHareketTuru;

/**
 * A StokTakip.
 */
@Entity
@Table(name = "stok_takip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StokTakip implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "miktar", nullable = false)
    private Long miktar;

    @Lob
    @Column(name = "aciklama")
    private String aciklama;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Enumerated(EnumType.STRING)
    @Column(name = "hareket_turu")
    private StokHareketTuru hareketTuru;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ekips")
    private Ekip ekip;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("malzemes")
    private Malzeme malzeme;

    @ManyToOne
    @JsonIgnoreProperties("depos")
    private Depo depo;

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

    public StokTakip miktar(Long miktar) {
        this.miktar = miktar;
        return this;
    }

    public void setMiktar(Long miktar) {
        this.miktar = miktar;
    }

    public String getAciklama() {
        return aciklama;
    }

    public StokTakip aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public StokTakip tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public StokHareketTuru getHareketTuru() {
        return hareketTuru;
    }

    public StokTakip hareketTuru(StokHareketTuru hareketTuru) {
        this.hareketTuru = hareketTuru;
        return this;
    }

    public void setHareketTuru(StokHareketTuru hareketTuru) {
        this.hareketTuru = hareketTuru;
    }

    public Ekip getEkip() {
        return ekip;
    }

    public StokTakip ekip(Ekip ekip) {
        this.ekip = ekip;
        return this;
    }

    public void setEkip(Ekip ekip) {
        this.ekip = ekip;
    }

    public Malzeme getMalzeme() {
        return malzeme;
    }

    public StokTakip malzeme(Malzeme malzeme) {
        this.malzeme = malzeme;
        return this;
    }

    public void setMalzeme(Malzeme malzeme) {
        this.malzeme = malzeme;
    }

    public Depo getDepo() {
        return depo;
    }

    public StokTakip depo(Depo depo) {
        this.depo = depo;
        return this;
    }

    public void setDepo(Depo depo) {
        this.depo = depo;
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
        StokTakip stokTakip = (StokTakip) o;
        if (stokTakip.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stokTakip.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StokTakip{" +
            "id=" + getId() +
            ", miktar=" + getMiktar() +
            ", aciklama='" + getAciklama() + "'" +
            ", tarih='" + getTarih() + "'" +
            ", hareketTuru='" + getHareketTuru() + "'" +
            "}";
    }
}
