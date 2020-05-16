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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the DovizKur entity. This class is used in DovizKurResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /doviz-kurs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DovizKurCriteria implements Serializable {
    /**
     * Class for filtering ParaBirimi
     */
    public static class ParaBirimiFilter extends Filter<ParaBirimi> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ParaBirimiFilter paraBirimi;

    private DoubleFilter deger;

    private LocalDateFilter tarih;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ParaBirimiFilter getParaBirimi() {
        return paraBirimi;
    }

    public void setParaBirimi(ParaBirimiFilter paraBirimi) {
        this.paraBirimi = paraBirimi;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DovizKurCriteria that = (DovizKurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(paraBirimi, that.paraBirimi) &&
            Objects.equals(deger, that.deger) &&
            Objects.equals(tarih, that.tarih);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        paraBirimi,
        deger,
        tarih
        );
    }

    @Override
    public String toString() {
        return "DovizKurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (paraBirimi != null ? "paraBirimi=" + paraBirimi + ", " : "") +
                (deger != null ? "deger=" + deger + ", " : "") +
                (tarih != null ? "tarih=" + tarih + ", " : "") +
            "}";
    }

}
