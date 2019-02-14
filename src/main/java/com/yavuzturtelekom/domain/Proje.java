package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.GorevDurumu;

import com.yavuzturtelekom.domain.enumeration.OnemDurumu;

/**
 * A Proje.
 */
@Entity
@Table(name = "proje")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proje implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_tanimi")
    private String isTanimi;

    @Lob
    @Column(name = "aciklama")
    private String aciklama;

    @Lob
    @Column(name = "dosya")
    private byte[] dosya;

    @Column(name = "dosya_content_type")
    private String dosyaContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "durumu")
    private GorevDurumu durumu;

    @Enumerated(EnumType.STRING)
    @Column(name = "onem_durumu")
    private OnemDurumu onemDurumu;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Column(name = "baslama_tarihi")
    private LocalDate baslamaTarihi;

    @Column(name = "bitis_tarihi")
    private LocalDate bitisTarihi;

    @OneToMany(mappedBy = "proje")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjeTuru> turus = new HashSet<>();
    @OneToMany(mappedBy = "proje")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Bolge> bolges = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "proje_iscilik",
               joinColumns = @JoinColumn(name = "proje_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "iscilik_id", referencedColumnName = "id"))
    private Set<Iscilik> isciliks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsTanimi() {
        return isTanimi;
    }

    public Proje isTanimi(String isTanimi) {
        this.isTanimi = isTanimi;
        return this;
    }

    public void setIsTanimi(String isTanimi) {
        this.isTanimi = isTanimi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Proje aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public byte[] getDosya() {
        return dosya;
    }

    public Proje dosya(byte[] dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(byte[] dosya) {
        this.dosya = dosya;
    }

    public String getDosyaContentType() {
        return dosyaContentType;
    }

    public Proje dosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
        return this;
    }

    public void setDosyaContentType(String dosyaContentType) {
        this.dosyaContentType = dosyaContentType;
    }

    public GorevDurumu getDurumu() {
        return durumu;
    }

    public Proje durumu(GorevDurumu durumu) {
        this.durumu = durumu;
        return this;
    }

    public void setDurumu(GorevDurumu durumu) {
        this.durumu = durumu;
    }

    public OnemDurumu getOnemDurumu() {
        return onemDurumu;
    }

    public Proje onemDurumu(OnemDurumu onemDurumu) {
        this.onemDurumu = onemDurumu;
        return this;
    }

    public void setOnemDurumu(OnemDurumu onemDurumu) {
        this.onemDurumu = onemDurumu;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public Proje tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public LocalDate getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public Proje baslamaTarihi(LocalDate baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
        return this;
    }

    public void setBaslamaTarihi(LocalDate baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public LocalDate getBitisTarihi() {
        return bitisTarihi;
    }

    public Proje bitisTarihi(LocalDate bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
        return this;
    }

    public void setBitisTarihi(LocalDate bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public Set<ProjeTuru> getTurus() {
        return turus;
    }

    public Proje turus(Set<ProjeTuru> projeTurus) {
        this.turus = projeTurus;
        return this;
    }

    public Proje addTuru(ProjeTuru projeTuru) {
        this.turus.add(projeTuru);
        projeTuru.setProje(this);
        return this;
    }

    public Proje removeTuru(ProjeTuru projeTuru) {
        this.turus.remove(projeTuru);
        projeTuru.setProje(null);
        return this;
    }

    public void setTurus(Set<ProjeTuru> projeTurus) {
        this.turus = projeTurus;
    }

    public Set<Bolge> getBolges() {
        return bolges;
    }

    public Proje bolges(Set<Bolge> bolges) {
        this.bolges = bolges;
        return this;
    }

    public Proje addBolge(Bolge bolge) {
        this.bolges.add(bolge);
        bolge.setProje(this);
        return this;
    }

    public Proje removeBolge(Bolge bolge) {
        this.bolges.remove(bolge);
        bolge.setProje(null);
        return this;
    }

    public void setBolges(Set<Bolge> bolges) {
        this.bolges = bolges;
    }

    public Set<Iscilik> getIsciliks() {
        return isciliks;
    }

    public Proje isciliks(Set<Iscilik> isciliks) {
        this.isciliks = isciliks;
        return this;
    }

    public Proje addIscilik(Iscilik iscilik) {
        this.isciliks.add(iscilik);
        iscilik.getProjes().add(this);
        return this;
    }

    public Proje removeIscilik(Iscilik iscilik) {
        this.isciliks.remove(iscilik);
        iscilik.getProjes().remove(this);
        return this;
    }

    public void setIsciliks(Set<Iscilik> isciliks) {
        this.isciliks = isciliks;
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
        Proje proje = (Proje) o;
        if (proje.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proje{" +
            "id=" + getId() +
            ", isTanimi='" + getIsTanimi() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", dosya='" + getDosya() + "'" +
            ", dosyaContentType='" + getDosyaContentType() + "'" +
            ", durumu='" + getDurumu() + "'" +
            ", onemDurumu='" + getOnemDurumu() + "'" +
            ", tarih='" + getTarih() + "'" +
            ", baslamaTarihi='" + getBaslamaTarihi() + "'" +
            ", bitisTarihi='" + getBitisTarihi() + "'" +
            "}";
    }
}
