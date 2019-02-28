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
 * Criteria class for the Santral entity. This class is used in SantralResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /santrals?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SantralCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private LongFilter mudurlukId;

    private LongFilter santralSorumluId;

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

    public LongFilter getMudurlukId() {
        return mudurlukId;
    }

    public void setMudurlukId(LongFilter mudurlukId) {
        this.mudurlukId = mudurlukId;
    }

    public LongFilter getSantralSorumluId() {
        return santralSorumluId;
    }

    public void setSantralSorumluId(LongFilter santralSorumluId) {
        this.santralSorumluId = santralSorumluId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SantralCriteria that = (SantralCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(mudurlukId, that.mudurlukId) &&
            Objects.equals(santralSorumluId, that.santralSorumluId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        mudurlukId,
        santralSorumluId
        );
    }

    @Override
    public String toString() {
        return "SantralCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (mudurlukId != null ? "mudurlukId=" + mudurlukId + ", " : "") +
                (santralSorumluId != null ? "santralSorumluId=" + santralSorumluId + ", " : "") +
            "}";
    }

}
