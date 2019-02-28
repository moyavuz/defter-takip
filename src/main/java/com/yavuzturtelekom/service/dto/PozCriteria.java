package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Poz entity. This class is used in PozResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /pozs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PozCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private StringFilter aciklama;

    private StringFilter kisaltma;

    private IntegerFilter yil;

    private DoubleFilter tenzilatsizFiyat;

    private DoubleFilter tenzilatliFiyat;

    private DoubleFilter taseronFiyat;

    private DoubleFilter tasereFiyat;

    private BooleanFilter malzemeMi;

    private LongFilter hakedisDetayId;

    private LongFilter birimId;

    private LongFilter grupId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAd() {
        return ad;
    }

    public void setAd(StringFilter ad) {
        this.ad = ad;
    }

    public StringFilter getAciklama() {
        return aciklama;
    }

    public void setAciklama(StringFilter aciklama) {
        this.aciklama = aciklama;
    }

    public StringFilter getKisaltma() {
        return kisaltma;
    }

    public void setKisaltma(StringFilter kisaltma) {
        this.kisaltma = kisaltma;
    }

    public IntegerFilter getYil() {
        return yil;
    }

    public void setYil(IntegerFilter yil) {
        this.yil = yil;
    }

    public DoubleFilter getTenzilatsizFiyat() {
        return tenzilatsizFiyat;
    }

    public void setTenzilatsizFiyat(DoubleFilter tenzilatsizFiyat) {
        this.tenzilatsizFiyat = tenzilatsizFiyat;
    }

    public DoubleFilter getTenzilatliFiyat() {
        return tenzilatliFiyat;
    }

    public void setTenzilatliFiyat(DoubleFilter tenzilatliFiyat) {
        this.tenzilatliFiyat = tenzilatliFiyat;
    }

    public DoubleFilter getTaseronFiyat() {
        return taseronFiyat;
    }

    public void setTaseronFiyat(DoubleFilter taseronFiyat) {
        this.taseronFiyat = taseronFiyat;
    }

    public DoubleFilter getTasereFiyat() {
        return tasereFiyat;
    }

    public void setTasereFiyat(DoubleFilter tasereFiyat) {
        this.tasereFiyat = tasereFiyat;
    }

    public BooleanFilter getMalzemeMi() {
        return malzemeMi;
    }

    public void setMalzemeMi(BooleanFilter malzemeMi) {
        this.malzemeMi = malzemeMi;
    }

    public LongFilter getHakedisDetayId() {
        return hakedisDetayId;
    }

    public void setHakedisDetayId(LongFilter hakedisDetayId) {
        this.hakedisDetayId = hakedisDetayId;
    }

    public LongFilter getBirimId() {
        return birimId;
    }

    public void setBirimId(LongFilter birimId) {
        this.birimId = birimId;
    }

    public LongFilter getGrupId() {
        return grupId;
    }

    public void setGrupId(LongFilter grupId) {
        this.grupId = grupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PozCriteria that = (PozCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(aciklama, that.aciklama) &&
            Objects.equals(kisaltma, that.kisaltma) &&
            Objects.equals(yil, that.yil) &&
            Objects.equals(tenzilatsizFiyat, that.tenzilatsizFiyat) &&
            Objects.equals(tenzilatliFiyat, that.tenzilatliFiyat) &&
            Objects.equals(taseronFiyat, that.taseronFiyat) &&
            Objects.equals(tasereFiyat, that.tasereFiyat) &&
            Objects.equals(malzemeMi, that.malzemeMi) &&
            Objects.equals(hakedisDetayId, that.hakedisDetayId) &&
            Objects.equals(birimId, that.birimId) &&
            Objects.equals(grupId, that.grupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        aciklama,
        kisaltma,
        yil,
        tenzilatsizFiyat,
        tenzilatliFiyat,
        taseronFiyat,
        tasereFiyat,
        malzemeMi,
        hakedisDetayId,
        birimId,
        grupId
        );
    }

    @Override
    public String toString() {
        return "PozCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (aciklama != null ? "aciklama=" + aciklama + ", " : "") +
                (kisaltma != null ? "kisaltma=" + kisaltma + ", " : "") +
                (yil != null ? "yil=" + yil + ", " : "") +
                (tenzilatsizFiyat != null ? "tenzilatsizFiyat=" + tenzilatsizFiyat + ", " : "") +
                (tenzilatliFiyat != null ? "tenzilatliFiyat=" + tenzilatliFiyat + ", " : "") +
                (taseronFiyat != null ? "taseronFiyat=" + taseronFiyat + ", " : "") +
                (tasereFiyat != null ? "tasereFiyat=" + tasereFiyat + ", " : "") +
                (malzemeMi != null ? "malzemeMi=" + malzemeMi + ", " : "") +
                (hakedisDetayId != null ? "hakedisDetayId=" + hakedisDetayId + ", " : "") +
                (birimId != null ? "birimId=" + birimId + ", " : "") +
                (grupId != null ? "grupId=" + grupId + ", " : "") +
            "}";
    }

}
