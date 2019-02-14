package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Iscilik.
 */
@Entity
@Table(name = "iscilik")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Iscilik implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "miktar")
    private Long miktar;

    @Lob
    @Column(name = "aciklama")
    private String aciklama;

    @Lob
    @Column(name = "resim")
    private byte[] resim;

    @Column(name = "resim_content_type")
    private String resimContentType;

    @ManyToOne
    @JsonIgnoreProperties("calisanEkips")
    private Ekip ekip;

    @ManyToOne
    @JsonIgnoreProperties("iscilikBirims")
    private Birim birim;

    @OneToMany(mappedBy = "iscilik")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Poz> pozs = new HashSet<>();
    @ManyToMany(mappedBy = "projeIsciliks")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Proje> projes = new HashSet<>();

    @ManyToMany(mappedBy = "malzemeIsciliks")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Malzeme> malzemes = new HashSet<>();

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

    public Iscilik miktar(Long miktar) {
        this.miktar = miktar;
        return this;
    }

    public void setMiktar(Long miktar) {
        this.miktar = miktar;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Iscilik aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public byte[] getResim() {
        return resim;
    }

    public Iscilik resim(byte[] resim) {
        this.resim = resim;
        return this;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }

    public String getResimContentType() {
        return resimContentType;
    }

    public Iscilik resimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
        return this;
    }

    public void setResimContentType(String resimContentType) {
        this.resimContentType = resimContentType;
    }

    public Ekip getEkip() {
        return ekip;
    }

    public Iscilik ekip(Ekip ekip) {
        this.ekip = ekip;
        return this;
    }

    public void setEkip(Ekip ekip) {
        this.ekip = ekip;
    }

    public Birim getBirim() {
        return birim;
    }

    public Iscilik birim(Birim birim) {
        this.birim = birim;
        return this;
    }

    public void setBirim(Birim birim) {
        this.birim = birim;
    }

    public Set<Poz> getPozs() {
        return pozs;
    }

    public Iscilik pozs(Set<Poz> pozs) {
        this.pozs = pozs;
        return this;
    }

    public Iscilik addPoz(Poz poz) {
        this.pozs.add(poz);
        poz.setIscilik(this);
        return this;
    }

    public Iscilik removePoz(Poz poz) {
        this.pozs.remove(poz);
        poz.setIscilik(null);
        return this;
    }

    public void setPozs(Set<Poz> pozs) {
        this.pozs = pozs;
    }

    public Set<Proje> getProjes() {
        return projes;
    }

    public Iscilik projes(Set<Proje> projes) {
        this.projes = projes;
        return this;
    }

    public Iscilik addProje(Proje proje) {
        this.projes.add(proje);
        proje.getProjeIsciliks().add(this);
        return this;
    }

    public Iscilik removeProje(Proje proje) {
        this.projes.remove(proje);
        proje.getProjeIsciliks().remove(this);
        return this;
    }

    public void setProjes(Set<Proje> projes) {
        this.projes = projes;
    }

    public Set<Malzeme> getMalzemes() {
        return malzemes;
    }

    public Iscilik malzemes(Set<Malzeme> malzemes) {
        this.malzemes = malzemes;
        return this;
    }

    public Iscilik addMalzeme(Malzeme malzeme) {
        this.malzemes.add(malzeme);
        malzeme.getMalzemeIsciliks().add(this);
        return this;
    }

    public Iscilik removeMalzeme(Malzeme malzeme) {
        this.malzemes.remove(malzeme);
        malzeme.getMalzemeIsciliks().remove(this);
        return this;
    }

    public void setMalzemes(Set<Malzeme> malzemes) {
        this.malzemes = malzemes;
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
        Iscilik iscilik = (Iscilik) o;
        if (iscilik.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iscilik.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Iscilik{" +
            "id=" + getId() +
            ", miktar=" + getMiktar() +
            ", aciklama='" + getAciklama() + "'" +
            ", resim='" + getResim() + "'" +
            ", resimContentType='" + getResimContentType() + "'" +
            "}";
    }
}
