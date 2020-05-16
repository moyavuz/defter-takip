package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.IsDurumu;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Proje entity. This class is used in ProjeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /projes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjeCriteria implements Serializable {
    /**
     * Class for filtering IsDurumu
     */
    public static class IsDurumuFilter extends Filter<IsDurumu> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private StringFilter aciklama;

    private LongFilter protokolNo;

    private IsDurumuFilter durumu;

    private LocalDateFilter tarih;

    private LocalDateFilter baslamaTarihi;

    private LocalDateFilter bitisTarihi;

    private LongFilter hakedisId;

    private LongFilter turuId;

    private LongFilter mudurlukId;

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

    public LongFilter getProtokolNo() {
        return protokolNo;
    }

    public void setProtokolNo(LongFilter protokolNo) {
        this.protokolNo = protokolNo;
    }

    public IsDurumuFilter getDurumu() {
        return durumu;
    }

    public void setDurumu(IsDurumuFilter durumu) {
        this.durumu = durumu;
    }

    public LocalDateFilter getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateFilter tarih) {
        this.tarih = tarih;
    }

    public LocalDateFilter getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public void setBaslamaTarihi(LocalDateFilter baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public LocalDateFilter getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(LocalDateFilter bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public LongFilter getHakedisId() {
        return hakedisId;
    }

    public void setHakedisId(LongFilter hakedisId) {
        this.hakedisId = hakedisId;
    }

    public LongFilter getTuruId() {
        return turuId;
    }

    public void setTuruId(LongFilter turuId) {
        this.turuId = turuId;
    }

    public LongFilter getMudurlukId() {
        return mudurlukId;
    }

    public void setMudurlukId(LongFilter mudurlukId) {
        this.mudurlukId = mudurlukId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjeCriteria that = (ProjeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(aciklama, that.aciklama) &&
            Objects.equals(protokolNo, that.protokolNo) &&
            Objects.equals(durumu, that.durumu) &&
            Objects.equals(tarih, that.tarih) &&
            Objects.equals(baslamaTarihi, that.baslamaTarihi) &&
            Objects.equals(bitisTarihi, that.bitisTarihi) &&
            Objects.equals(hakedisId, that.hakedisId) &&
            Objects.equals(turuId, that.turuId) &&
            Objects.equals(mudurlukId, that.mudurlukId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        aciklama,
        protokolNo,
        durumu,
        tarih,
        baslamaTarihi,
        bitisTarihi,
        hakedisId,
        turuId,
        mudurlukId
        );
    }

    @Override
    public String toString() {
        return "ProjeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (aciklama != null ? "aciklama=" + aciklama + ", " : "") +
                (protokolNo != null ? "protokolNo=" + protokolNo + ", " : "") +
                (durumu != null ? "durumu=" + durumu + ", " : "") +
                (tarih != null ? "tarih=" + tarih + ", " : "") +
                (baslamaTarihi != null ? "baslamaTarihi=" + baslamaTarihi + ", " : "") +
                (bitisTarihi != null ? "bitisTarihi=" + bitisTarihi + ", " : "") +
                (hakedisId != null ? "hakedisId=" + hakedisId + ", " : "") +
                (turuId != null ? "turuId=" + turuId + ", " : "") +
                (mudurlukId != null ? "mudurlukId=" + mudurlukId + ", " : "") +
            "}";
    }

}
