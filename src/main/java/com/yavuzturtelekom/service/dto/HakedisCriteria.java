package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.OnemDurumu;
import com.yavuzturtelekom.domain.enumeration.IsDurumu;
import com.yavuzturtelekom.domain.enumeration.OdemeDurumu;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Hakedis entity. This class is used in HakedisResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /hakedis?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HakedisCriteria implements Serializable {
    /**
     * Class for filtering OnemDurumu
     */
    public static class OnemDurumuFilter extends Filter<OnemDurumu> {
    }
    /**
     * Class for filtering IsDurumu
     */
    public static class IsDurumuFilter extends Filter<IsDurumu> {
    }
    /**
     * Class for filtering OdemeDurumu
     */
    public static class OdemeDurumuFilter extends Filter<OdemeDurumu> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private LocalDateFilter tarih;

    private LongFilter seriNo;

    private StringFilter defterNo;

    private LongFilter cizimNo;

    private OnemDurumuFilter onemDerecesi;

    private IsDurumuFilter isDurumu;

    private OdemeDurumuFilter odemeDurumu;

    private StringFilter odemeNo;

    private StringFilter aciklama;

    private LongFilter hakedisDetayId;

    private LongFilter santralId;

    private LongFilter turuId;

    private LongFilter ekipId;

    private LongFilter projeId;

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

    public LocalDateFilter getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateFilter tarih) {
        this.tarih = tarih;
    }

    public LongFilter getSeriNo() {
        return seriNo;
    }

    public void setSeriNo(LongFilter seriNo) {
        this.seriNo = seriNo;
    }

    public StringFilter getDefterNo() {
        return defterNo;
    }

    public void setDefterNo(StringFilter defterNo) {
        this.defterNo = defterNo;
    }

    public LongFilter getCizimNo() {
        return cizimNo;
    }

    public void setCizimNo(LongFilter cizimNo) {
        this.cizimNo = cizimNo;
    }

    public OnemDurumuFilter getOnemDerecesi() {
        return onemDerecesi;
    }

    public void setOnemDerecesi(OnemDurumuFilter onemDerecesi) {
        this.onemDerecesi = onemDerecesi;
    }

    public IsDurumuFilter getIsDurumu() {
        return isDurumu;
    }

    public void setIsDurumu(IsDurumuFilter isDurumu) {
        this.isDurumu = isDurumu;
    }

    public OdemeDurumuFilter getOdemeDurumu() {
        return odemeDurumu;
    }

    public void setOdemeDurumu(OdemeDurumuFilter odemeDurumu) {
        this.odemeDurumu = odemeDurumu;
    }

    public StringFilter getOdemeNo() {
        return odemeNo;
    }

    public void setOdemeNo(StringFilter odemeNo) {
        this.odemeNo = odemeNo;
    }

    public StringFilter getAciklama() {
        return aciklama;
    }

    public void setAciklama(StringFilter aciklama) {
        this.aciklama = aciklama;
    }

    public LongFilter getHakedisDetayId() {
        return hakedisDetayId;
    }

    public void setHakedisDetayId(LongFilter hakedisDetayId) {
        this.hakedisDetayId = hakedisDetayId;
    }

    public LongFilter getSantralId() {
        return santralId;
    }

    public void setSantralId(LongFilter santralId) {
        this.santralId = santralId;
    }

    public LongFilter getTuruId() {
        return turuId;
    }

    public void setTuruId(LongFilter turuId) {
        this.turuId = turuId;
    }

    public LongFilter getEkipId() {
        return ekipId;
    }

    public void setEkipId(LongFilter ekipId) {
        this.ekipId = ekipId;
    }

    public LongFilter getProjeId() {
        return projeId;
    }

    public void setProjeId(LongFilter projeId) {
        this.projeId = projeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HakedisCriteria that = (HakedisCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(tarih, that.tarih) &&
            Objects.equals(seriNo, that.seriNo) &&
            Objects.equals(defterNo, that.defterNo) &&
            Objects.equals(cizimNo, that.cizimNo) &&
            Objects.equals(onemDerecesi, that.onemDerecesi) &&
            Objects.equals(isDurumu, that.isDurumu) &&
            Objects.equals(odemeDurumu, that.odemeDurumu) &&
            Objects.equals(odemeNo, that.odemeNo) &&
            Objects.equals(aciklama, that.aciklama) &&
            Objects.equals(hakedisDetayId, that.hakedisDetayId) &&
            Objects.equals(santralId, that.santralId) &&
            Objects.equals(turuId, that.turuId) &&
            Objects.equals(ekipId, that.ekipId) &&
            Objects.equals(projeId, that.projeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        tarih,
        seriNo,
        defterNo,
        cizimNo,
        onemDerecesi,
        isDurumu,
        odemeDurumu,
        odemeNo,
        aciklama,
        hakedisDetayId,
        santralId,
        turuId,
        ekipId,
        projeId
        );
    }

    @Override
    public String toString() {
        return "HakedisCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (tarih != null ? "tarih=" + tarih + ", " : "") +
                (seriNo != null ? "seriNo=" + seriNo + ", " : "") +
                (defterNo != null ? "defterNo=" + defterNo + ", " : "") +
                (cizimNo != null ? "cizimNo=" + cizimNo + ", " : "") +
                (onemDerecesi != null ? "onemDerecesi=" + onemDerecesi + ", " : "") +
                (isDurumu != null ? "isDurumu=" + isDurumu + ", " : "") +
                (odemeDurumu != null ? "odemeDurumu=" + odemeDurumu + ", " : "") +
                (odemeNo != null ? "odemeNo=" + odemeNo + ", " : "") +
                (aciklama != null ? "aciklama=" + aciklama + ", " : "") +
                (hakedisDetayId != null ? "hakedisDetayId=" + hakedisDetayId + ", " : "") +
                (santralId != null ? "santralId=" + santralId + ", " : "") +
                (turuId != null ? "turuId=" + turuId + ", " : "") +
                (ekipId != null ? "ekipId=" + ekipId + ", " : "") +
                (projeId != null ? "projeId=" + projeId + ", " : "") +
            "}";
    }

}
