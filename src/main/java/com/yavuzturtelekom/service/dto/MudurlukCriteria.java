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
 * Criteria class for the Mudurluk entity. This class is used in MudurlukResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mudurluks?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MudurlukCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private StringFilter adres;

    private LongFilter mudurlukSorumluId;

    private LongFilter ilId;

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

    public LongFilter getMudurlukSorumluId() {
        return mudurlukSorumluId;
    }

    public void setMudurlukSorumluId(LongFilter mudurlukSorumluId) {
        this.mudurlukSorumluId = mudurlukSorumluId;
    }

    public LongFilter getIlId() {
        return ilId;
    }

    public void setIlId(LongFilter ilId) {
        this.ilId = ilId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MudurlukCriteria that = (MudurlukCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(adres, that.adres) &&
            Objects.equals(mudurlukSorumluId, that.mudurlukSorumluId) &&
            Objects.equals(ilId, that.ilId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        adres,
        mudurlukSorumluId,
        ilId
        );
    }

    @Override
    public String toString() {
        return "MudurlukCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (adres != null ? "adres=" + adres + ", " : "") +
                (mudurlukSorumluId != null ? "mudurlukSorumluId=" + mudurlukSorumluId + ", " : "") +
                (ilId != null ? "ilId=" + ilId + ", " : "") +
            "}";
    }

}
