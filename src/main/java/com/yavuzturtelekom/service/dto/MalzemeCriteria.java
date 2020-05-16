package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.ParaBirimi;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Malzeme entity. This class is used in MalzemeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /malzemes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MalzemeCriteria implements Serializable {
    /**
     * Class for filtering ParaBirimi
     */
    public static class ParaBirimiFilter extends Filter<ParaBirimi> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private LongFilter malzemeNo;

    private StringFilter aciklama;

    private StringFilter kisaltma;

    private DoubleFilter tenzilatsizFiyat;

    private DoubleFilter tenzilatliFiyat;

    private DoubleFilter taseronFiyat;

    private ParaBirimiFilter paraBirimi;

    private LongFilter stokTakipId;

    private LongFilter birimId;

    private LongFilter depoId;

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

    public LongFilter getMalzemeNo() {
        return malzemeNo;
    }

    public void setMalzemeNo(LongFilter malzemeNo) {
        this.malzemeNo = malzemeNo;
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

    public ParaBirimiFilter getParaBirimi() {
        return paraBirimi;
    }

    public void setParaBirimi(ParaBirimiFilter paraBirimi) {
        this.paraBirimi = paraBirimi;
    }

    public LongFilter getStokTakipId() {
        return stokTakipId;
    }

    public void setStokTakipId(LongFilter stokTakipId) {
        this.stokTakipId = stokTakipId;
    }

    public LongFilter getBirimId() {
        return birimId;
    }

    public void setBirimId(LongFilter birimId) {
        this.birimId = birimId;
    }

    public LongFilter getDepoId() {
        return depoId;
    }

    public void setDepoId(LongFilter depoId) {
        this.depoId = depoId;
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
        final MalzemeCriteria that = (MalzemeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(malzemeNo, that.malzemeNo) &&
            Objects.equals(aciklama, that.aciklama) &&
            Objects.equals(kisaltma, that.kisaltma) &&
            Objects.equals(tenzilatsizFiyat, that.tenzilatsizFiyat) &&
            Objects.equals(tenzilatliFiyat, that.tenzilatliFiyat) &&
            Objects.equals(taseronFiyat, that.taseronFiyat) &&
            Objects.equals(paraBirimi, that.paraBirimi) &&
            Objects.equals(stokTakipId, that.stokTakipId) &&
            Objects.equals(birimId, that.birimId) &&
            Objects.equals(depoId, that.depoId) &&
            Objects.equals(grupId, that.grupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        malzemeNo,
        aciklama,
        kisaltma,
        tenzilatsizFiyat,
        tenzilatliFiyat,
        taseronFiyat,
        paraBirimi,
        stokTakipId,
        birimId,
        depoId,
        grupId
        );
    }

    @Override
    public String toString() {
        return "MalzemeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (malzemeNo != null ? "malzemeNo=" + malzemeNo + ", " : "") +
                (aciklama != null ? "aciklama=" + aciklama + ", " : "") +
                (kisaltma != null ? "kisaltma=" + kisaltma + ", " : "") +
                (tenzilatsizFiyat != null ? "tenzilatsizFiyat=" + tenzilatsizFiyat + ", " : "") +
                (tenzilatliFiyat != null ? "tenzilatliFiyat=" + tenzilatliFiyat + ", " : "") +
                (taseronFiyat != null ? "taseronFiyat=" + taseronFiyat + ", " : "") +
                (paraBirimi != null ? "paraBirimi=" + paraBirimi + ", " : "") +
                (stokTakipId != null ? "stokTakipId=" + stokTakipId + ", " : "") +
                (birimId != null ? "birimId=" + birimId + ", " : "") +
                (depoId != null ? "depoId=" + depoId + ", " : "") +
                (grupId != null ? "grupId=" + grupId + ", " : "") +
            "}";
    }

}
