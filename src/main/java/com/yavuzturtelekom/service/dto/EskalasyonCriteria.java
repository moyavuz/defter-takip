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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Eskalasyon entity. This class is used in EskalasyonResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /eskalasyons?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EskalasyonCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter deger;

    private LocalDateFilter tarih;

    private LongFilter turuId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getDeger() {
        return deger;
    }

    public void setDeger(DoubleFilter deger) {
        this.deger = deger;
    }

    public LocalDateFilter getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateFilter tarih) {
        this.tarih = tarih;
    }

    public LongFilter getTuruId() {
        return turuId;
    }

    public void setTuruId(LongFilter turuId) {
        this.turuId = turuId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EskalasyonCriteria that = (EskalasyonCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(deger, that.deger) &&
            Objects.equals(tarih, that.tarih) &&
            Objects.equals(turuId, that.turuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        deger,
        tarih,
        turuId
        );
    }

    @Override
    public String toString() {
        return "EskalasyonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (deger != null ? "deger=" + deger + ", " : "") +
                (tarih != null ? "tarih=" + tarih + ", " : "") +
                (turuId != null ? "turuId=" + turuId + ", " : "") +
            "}";
    }

}
