package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.YakitTuru;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Arac entity. This class is used in AracResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /aracs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AracCriteria implements Serializable {
    /**
     * Class for filtering YakitTuru
     */
    public static class YakitTuruFilter extends Filter<YakitTuru> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private StringFilter aciklama;

    private LongFilter modelYili;

    private YakitTuruFilter yakitTuru;

    private LocalDateFilter tarih;

    private LocalDateFilter muayeneTarih;

    private LocalDateFilter kaskoTarih;

    private LocalDateFilter sigortaTarih;

    private LocalDateFilter bakimTarih;

    private LongFilter modelId;

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

    public LongFilter getModelYili() {
        return modelYili;
    }

    public void setModelYili(LongFilter modelYili) {
        this.modelYili = modelYili;
    }

    public YakitTuruFilter getYakitTuru() {
        return yakitTuru;
    }

    public void setYakitTuru(YakitTuruFilter yakitTuru) {
        this.yakitTuru = yakitTuru;
    }

    public LocalDateFilter getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateFilter tarih) {
        this.tarih = tarih;
    }

    public LocalDateFilter getMuayeneTarih() {
        return muayeneTarih;
    }

    public void setMuayeneTarih(LocalDateFilter muayeneTarih) {
        this.muayeneTarih = muayeneTarih;
    }

    public LocalDateFilter getKaskoTarih() {
        return kaskoTarih;
    }

    public void setKaskoTarih(LocalDateFilter kaskoTarih) {
        this.kaskoTarih = kaskoTarih;
    }

    public LocalDateFilter getSigortaTarih() {
        return sigortaTarih;
    }

    public void setSigortaTarih(LocalDateFilter sigortaTarih) {
        this.sigortaTarih = sigortaTarih;
    }

    public LocalDateFilter getBakimTarih() {
        return bakimTarih;
    }

    public void setBakimTarih(LocalDateFilter bakimTarih) {
        this.bakimTarih = bakimTarih;
    }

    public LongFilter getModelId() {
        return modelId;
    }

    public void setModelId(LongFilter modelId) {
        this.modelId = modelId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AracCriteria that = (AracCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(aciklama, that.aciklama) &&
            Objects.equals(modelYili, that.modelYili) &&
            Objects.equals(yakitTuru, that.yakitTuru) &&
            Objects.equals(tarih, that.tarih) &&
            Objects.equals(muayeneTarih, that.muayeneTarih) &&
            Objects.equals(kaskoTarih, that.kaskoTarih) &&
            Objects.equals(sigortaTarih, that.sigortaTarih) &&
            Objects.equals(bakimTarih, that.bakimTarih) &&
            Objects.equals(modelId, that.modelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        aciklama,
        modelYili,
        yakitTuru,
        tarih,
        muayeneTarih,
        kaskoTarih,
        sigortaTarih,
        bakimTarih,
        modelId
        );
    }

    @Override
    public String toString() {
        return "AracCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (aciklama != null ? "aciklama=" + aciklama + ", " : "") +
                (modelYili != null ? "modelYili=" + modelYili + ", " : "") +
                (yakitTuru != null ? "yakitTuru=" + yakitTuru + ", " : "") +
                (tarih != null ? "tarih=" + tarih + ", " : "") +
                (muayeneTarih != null ? "muayeneTarih=" + muayeneTarih + ", " : "") +
                (kaskoTarih != null ? "kaskoTarih=" + kaskoTarih + ", " : "") +
                (sigortaTarih != null ? "sigortaTarih=" + sigortaTarih + ", " : "") +
                (bakimTarih != null ? "bakimTarih=" + bakimTarih + ", " : "") +
                (modelId != null ? "modelId=" + modelId + ", " : "") +
            "}";
    }

}
