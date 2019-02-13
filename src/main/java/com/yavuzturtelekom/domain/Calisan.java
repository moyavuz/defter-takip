package com.yavuzturtelekom.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.yavuzturtelekom.domain.enumeration.CalisanTuru;

/**
 * A Calisan.
 */
@Entity
@Table(name = "calisan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Calisan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tckimlikno", nullable = false)
    private Long tckimlikno;

    @NotNull
    @Size(min = 2)
    @Column(name = "adi", nullable = false)
    private String adi;

    @NotNull
    @Size(min = 2)
    @Column(name = "soyadi", nullable = false)
    private String soyadi;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "giris_tarihi")
    private Instant girisTarihi;

    @Column(name = "cikis_tarihi")
    private Instant cikisTarihi;

    @Column(name = "ucret", precision = 10, scale = 2)
    private BigDecimal ucret;

    @Enumerated(EnumType.STRING)
    @Column(name = "calisan_turu")
    private CalisanTuru calisanTuru;

    @Column(name = "aktif_mi")
    private Boolean aktifMi;

    @OneToOne
    @JoinColumn(unique = true)
    private Unvan unvan;

    @ManyToOne
    @JsonIgnoreProperties("calisans")
    private Calisan sorumlu;

    @OneToMany(mappedBy = "calisan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ekip> ekips = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTckimlikno() {
        return tckimlikno;
    }

    public Calisan tckimlikno(Long tckimlikno) {
        this.tckimlikno = tckimlikno;
        return this;
    }

    public void setTckimlikno(Long tckimlikno) {
        this.tckimlikno = tckimlikno;
    }

    public String getAdi() {
        return adi;
    }

    public Calisan adi(String adi) {
        this.adi = adi;
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public Calisan soyadi(String soyadi) {
        this.soyadi = soyadi;
        return this;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getEmail() {
        return email;
    }

    public Calisan email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Calisan phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getGirisTarihi() {
        return girisTarihi;
    }

    public Calisan girisTarihi(Instant girisTarihi) {
        this.girisTarihi = girisTarihi;
        return this;
    }

    public void setGirisTarihi(Instant girisTarihi) {
        this.girisTarihi = girisTarihi;
    }

    public Instant getCikisTarihi() {
        return cikisTarihi;
    }

    public Calisan cikisTarihi(Instant cikisTarihi) {
        this.cikisTarihi = cikisTarihi;
        return this;
    }

    public void setCikisTarihi(Instant cikisTarihi) {
        this.cikisTarihi = cikisTarihi;
    }

    public BigDecimal getUcret() {
        return ucret;
    }

    public Calisan ucret(BigDecimal ucret) {
        this.ucret = ucret;
        return this;
    }

    public void setUcret(BigDecimal ucret) {
        this.ucret = ucret;
    }

    public CalisanTuru getCalisanTuru() {
        return calisanTuru;
    }

    public Calisan calisanTuru(CalisanTuru calisanTuru) {
        this.calisanTuru = calisanTuru;
        return this;
    }

    public void setCalisanTuru(CalisanTuru calisanTuru) {
        this.calisanTuru = calisanTuru;
    }

    public Boolean isAktifMi() {
        return aktifMi;
    }

    public Calisan aktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
        return this;
    }

    public void setAktifMi(Boolean aktifMi) {
        this.aktifMi = aktifMi;
    }

    public Unvan getUnvan() {
        return unvan;
    }

    public Calisan unvan(Unvan unvan) {
        this.unvan = unvan;
        return this;
    }

    public void setUnvan(Unvan unvan) {
        this.unvan = unvan;
    }

    public Calisan getSorumlu() {
        return sorumlu;
    }

    public Calisan sorumlu(Calisan calisan) {
        this.sorumlu = calisan;
        return this;
    }

    public void setSorumlu(Calisan calisan) {
        this.sorumlu = calisan;
    }

    public Set<Ekip> getEkips() {
        return ekips;
    }

    public Calisan ekips(Set<Ekip> ekips) {
        this.ekips = ekips;
        return this;
    }

    public Calisan addEkip(Ekip ekip) {
        this.ekips.add(ekip);
        ekip.setCalisan(this);
        return this;
    }

    public Calisan removeEkip(Ekip ekip) {
        this.ekips.remove(ekip);
        ekip.setCalisan(null);
        return this;
    }

    public void setEkips(Set<Ekip> ekips) {
        this.ekips = ekips;
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
        Calisan calisan = (Calisan) o;
        if (calisan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calisan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Calisan{" +
            "id=" + getId() +
            ", tckimlikno=" + getTckimlikno() +
            ", adi='" + getAdi() + "'" +
            ", soyadi='" + getSoyadi() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", girisTarihi='" + getGirisTarihi() + "'" +
            ", cikisTarihi='" + getCikisTarihi() + "'" +
            ", ucret=" + getUcret() +
            ", calisanTuru='" + getCalisanTuru() + "'" +
            ", aktifMi='" + isAktifMi() + "'" +
            "}";
    }
}
