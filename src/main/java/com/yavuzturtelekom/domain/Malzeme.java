package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.ParaBirimi;

/**
 * A Malzeme.
 */
@Entity
@Table(name = "malzeme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Malzeme implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "malzeme_no")
    private Long malzemeNo;

    @Column(name = "aciklama")
    private String aciklama;

    @Column(name = "kisaltma")
    private String kisaltma;

    @Column(name = "tenzilatsiz_fiyat")
    private Double tenzilatsizFiyat;

    @Column(name = "tenzilatli_fiyat")
    private Double tenzilatliFiyat;

    @Column(name = "taseron_fiyat")
    private Double taseronFiyat;

    @Enumerated(EnumType.STRING)
    @Column(name = "para_birimi")
    private ParaBirimi paraBirimi;

    @OneToMany(mappedBy = "malzeme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StokTakip> stokTakips = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("malzemes")
    private Birim birim;

    @ManyToOne
    @JsonIgnoreProperties("malzemes")
    private Depo depo;

    @ManyToMany(mappedBy = "malzemeListesis")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<MalzemeGrubu> grups = new HashSet<>();

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

    public Malzeme ad(String ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public Long getMalzemeNo() {
        return malzemeNo;
    }

    public Malzeme malzemeNo(Long malzemeNo) {
        this.malzemeNo = malzemeNo;
        return this;
    }

    public void setMalzemeNo(Long malzemeNo) {
        this.malzemeNo = malzemeNo;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Malzeme aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKisaltma() {
        return kisaltma;
    }

    public Malzeme kisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
        return this;
    }

    public void setKisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
    }

    public Double getTenzilatsizFiyat() {
        return tenzilatsizFiyat;
    }

    public Malzeme tenzilatsizFiyat(Double tenzilatsizFiyat) {
        this.tenzilatsizFiyat = tenzilatsizFiyat;
        return this;
    }

    public void setTenzilatsizFiyat(Double tenzilatsizFiyat) {
        this.tenzilatsizFiyat = tenzilatsizFiyat;
    }

    public Double getTenzilatliFiyat() {
        return tenzilatliFiyat;
    }

    public Malzeme tenzilatliFiyat(Double tenzilatliFiyat) {
        this.tenzilatliFiyat = tenzilatliFiyat;
        return this;
    }

    public void setTenzilatliFiyat(Double tenzilatliFiyat) {
        this.tenzilatliFiyat = tenzilatliFiyat;
    }

    public Double getTaseronFiyat() {
        return taseronFiyat;
    }

    public Malzeme taseronFiyat(Double taseronFiyat) {
        this.taseronFiyat = taseronFiyat;
        return this;
    }

    public void setTaseronFiyat(Double taseronFiyat) {
        this.taseronFiyat = taseronFiyat;
    }

    public ParaBirimi getParaBirimi() {
        return paraBirimi;
    }

    public Malzeme paraBirimi(ParaBirimi paraBirimi) {
        this.paraBirimi = paraBirimi;
        return this;
    }

    public void setParaBirimi(ParaBirimi paraBirimi) {
        this.paraBirimi = paraBirimi;
    }

    public Set<StokTakip> getStokTakips() {
        return stokTakips;
    }

    public Malzeme stokTakips(Set<StokTakip> stokTakips) {
        this.stokTakips = stokTakips;
        return this;
    }

    public Malzeme addStokTakip(StokTakip stokTakip) {
        this.stokTakips.add(stokTakip);
        stokTakip.setMalzeme(this);
        return this;
    }

    public Malzeme removeStokTakip(StokTakip stokTakip) {
        this.stokTakips.remove(stokTakip);
        stokTakip.setMalzeme(null);
        return this;
    }

    public void setStokTakips(Set<StokTakip> stokTakips) {
        this.stokTakips = stokTakips;
    }

    public Birim getBirim() {
        return birim;
    }

    public Malzeme birim(Birim birim) {
        this.birim = birim;
        return this;
    }

    public void setBirim(Birim birim) {
        this.birim = birim;
    }

    public Depo getDepo() {
        return depo;
    }

    public Malzeme depo(Depo depo) {
        this.depo = depo;
        return this;
    }

    public void setDepo(Depo depo) {
        this.depo = depo;
    }

    public Set<MalzemeGrubu> getGrups() {
        return grups;
    }

    public Malzeme grups(Set<MalzemeGrubu> malzemeGrubus) {
        this.grups = malzemeGrubus;
        return this;
    }

    public Malzeme addGrup(MalzemeGrubu malzemeGrubu) {
        this.grups.add(malzemeGrubu);
        malzemeGrubu.getMalzemeListesis().add(this);
        return this;
    }

    public Malzeme removeGrup(MalzemeGrubu malzemeGrubu) {
        this.grups.remove(malzemeGrubu);
        malzemeGrubu.getMalzemeListesis().remove(this);
        return this;
    }

    public void setGrups(Set<MalzemeGrubu> malzemeGrubus) {
        this.grups = malzemeGrubus;
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
        Malzeme malzeme = (Malzeme) o;
        if (malzeme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), malzeme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Malzeme{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            ", malzemeNo=" + getMalzemeNo() +
            ", aciklama='" + getAciklama() + "'" +
            ", kisaltma='" + getKisaltma() + "'" +
            ", tenzilatsizFiyat=" + getTenzilatsizFiyat() +
            ", tenzilatliFiyat=" + getTenzilatliFiyat() +
            ", taseronFiyat=" + getTaseronFiyat() +
            ", paraBirimi='" + getParaBirimi() + "'" +
            "}";
    }
}
