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
 * Criteria class for the HakedisDetay entity. This class is used in HakedisDetayResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /hakedis-detays?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HakedisDetayCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter miktar;

    private LongFilter hakedisId;

    private LongFilter pozId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getMiktar() {
        return miktar;
    }

    public void setMiktar(DoubleFilter miktar) {
        this.miktar = miktar;
    }

    public LongFilter getHakedisId() {
        return hakedisId;
    }

    public void setHakedisId(LongFilter hakedisId) {
        this.hakedisId = hakedisId;
    }

    public LongFilter getPozId() {
        return pozId;
    }

    public void setPozId(LongFilter pozId) {
        this.pozId = pozId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HakedisDetayCriteria that = (HakedisDetayCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(miktar, that.miktar) &&
            Objects.equals(hakedisId, that.hakedisId) &&
            Objects.equals(pozId, that.pozId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        miktar,
        hakedisId,
        pozId
        );
    }

    @Override
    public String toString() {
        return "HakedisDetayCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (miktar != null ? "miktar=" + miktar + ", " : "") +
                (hakedisId != null ? "hakedisId=" + hakedisId + ", " : "") +
                (pozId != null ? "pozId=" + pozId + ", " : "") +
            "}";
    }

}
