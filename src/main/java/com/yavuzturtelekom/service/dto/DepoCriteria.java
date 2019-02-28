package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.DepoTuru;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Depo entity. This class is used in DepoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /depos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepoCriteria implements Serializable {
    /**
     * Class for filtering DepoTuru
     */
    public static class DepoTuruFilter extends Filter<DepoTuru> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private StringFilter adres;

    private DepoTuruFilter turu;

    private LongFilter stokTakipId;

    private LongFilter sorumluId;

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

    public StringFilter getAdres() {
        return adres;
    }

    public void setAdres(StringFilter adres) {
        this.adres = adres;
    }

    public DepoTuruFilter getTuru() {
        return turu;
    }

    public void setTuru(DepoTuruFilter turu) {
        this.turu = turu;
    }

    public LongFilter getStokTakipId() {
        return stokTakipId;
    }

    public void setStokTakipId(LongFilter stokTakipId) {
        this.stokTakipId = stokTakipId;
    }

    public LongFilter getSorumluId() {
        return sorumluId;
    }

    public void setSorumluId(LongFilter sorumluId) {
        this.sorumluId = sorumluId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepoCriteria that = (DepoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(adres, that.adres) &&
            Objects.equals(turu, that.turu) &&
            Objects.equals(stokTakipId, that.stokTakipId) &&
            Objects.equals(sorumluId, that.sorumluId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        adres,
        turu,
        stokTakipId,
        sorumluId
        );
    }

    @Override
    public String toString() {
        return "DepoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (adres != null ? "adres=" + adres + ", " : "") +
                (turu != null ? "turu=" + turu + ", " : "") +
                (stokTakipId != null ? "stokTakipId=" + stokTakipId + ", " : "") +
                (sorumluId != null ? "sorumluId=" + sorumluId + ", " : "") +
            "}";
    }

}
