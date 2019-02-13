package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
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

    @Column(name = "aciklama")
    private String aciklama;

    @Enumerated(EnumType.STRING)
    @Column(name = "durumu")
    private GorevDurumu durumu;

    @Enumerated(EnumType.STRING)
    @Column(name = "onem_durumu")
    private OnemDurumu onemDurumu;

    @Column(name = "tarih")
    private Instant tarih;

    @Column(name = "baslama_tarihi")
    private Instant baslamaTarihi;

    @Column(name = "bitis_tarihi")
    private Instant bitisTarihi;

    @Lob
    @Column(name = "detay")
    private byte[] detay;

    @Column(name = "detay_content_type")
    private String detayContentType;

    @Column(name = "aktif_mi")
    private Boolean aktifMi;

    @OneToOne
    @JoinColumn(unique = true)
    private ProjeTuru turu;

    @OneToOne
    @JoinColumn(unique = true)
    private Bolge bolge;

    @OneToMany(mappedBy = "proje")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    public Instant getTarih() {
        return tarih;
    }

    public Proje tarih(Instant tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(Instant tarih) {
        this.tarih = tarih;
    }

    public Instant getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public Proje baslamaTarihi(Instant baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
        return this;
    }

    public void setBaslamaTarihi(Instant baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public Instant getBitisTarihi() {
        return bitisTarihi;
    }

    public Proje bitisTarihi(Instant bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
        return this;
    }

    public void setBitisTarihi(Instant bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public byte[] getDetay() {
        return detay;
    }

    public Proje detay(byte[] detay) {
        this.detay = detay;
        return this;
    }

    public void setDetay(byte[] detay) {
        this.detay = detay;
    }

    public String getDetayContentType() {
        return detayContentType;
    }

    public Proje detayContentType(String detayContentType) {
        this.detayContentType = detayContentType;
        return this;
    }

    public void setDetayContentType(String detayContentType) {
        this.detayContentType = detayContentType;
    }

    public Boolean isAktifMi() {
        return aktifMi;
    }

    public Proje aktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
        return this;
    }

    public void setAktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
    }

    public ProjeTuru getTuru() {
        return turu;
    }

    public Proje turu(ProjeTuru projeTuru) {
        this.turu = projeTuru;
        return this;
    }

    public void setTuru(ProjeTuru projeTuru) {
        this.turu = projeTuru;
    }

    public Bolge getBolge() {
        return bolge;
    }

    public Proje bolge(Bolge bolge) {
        this.bolge = bolge;
        return this;
    }

    public void setBolge(Bolge bolge) {
        this.bolge = bolge;
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
        iscilik.setProje(this);
        return this;
    }

    public Proje removeIscilik(Iscilik iscilik) {
        this.isciliks.remove(iscilik);
        iscilik.setProje(null);
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
            ", durumu='" + getDurumu() + "'" +
            ", onemDurumu='" + getOnemDurumu() + "'" +
            ", tarih='" + getTarih() + "'" +
            ", baslamaTarihi='" + getBaslamaTarihi() + "'" +
            ", bitisTarihi='" + getBitisTarihi() + "'" +
            ", detay='" + getDetay() + "'" +
            ", detayContentType='" + getDetayContentType() + "'" +
            ", aktifMi='" + isAktifMi() + "'" +
            "}";
    }
}
