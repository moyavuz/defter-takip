package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.IzinTuru;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the PersonelIzin entity. This class is used in PersonelIzinResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /personel-izins?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonelIzinCriteria implements Serializable {
    /**
     * Class for filtering IzinTuru
     */
    public static class IzinTuruFilter extends Filter<IzinTuru> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter tarih;

    private DoubleFilter miktar;

    private IzinTuruFilter turu;

    private LongFilter personelId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateFilter tarih) {
        this.tarih = tarih;
    }

    public DoubleFilter getMiktar() {
        return miktar;
    }

    public void setMiktar(DoubleFilter miktar) {
        this.miktar = miktar;
    }

    public IzinTuruFilter getTuru() {
        return turu;
    }

    public void setTuru(IzinTuruFilter turu) {
        this.turu = turu;
    }

    public LongFilter getPersonelId() {
        return personelId;
    }

    public void setPersonelId(LongFilter personelId) {
        this.personelId = personelId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonelIzinCriteria that = (PersonelIzinCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tarih, that.tarih) &&
            Objects.equals(miktar, that.miktar) &&
            Objects.equals(turu, that.turu) &&
            Objects.equals(personelId, that.personelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tarih,
        miktar,
        turu,
        personelId
        );
    }

    @Override
    public String toString() {
        return "PersonelIzinCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tarih != null ? "tarih=" + tarih + ", " : "") +
                (miktar != null ? "miktar=" + miktar + ", " : "") +
                (turu != null ? "turu=" + turu + ", " : "") +
                (personelId != null ? "personelId=" + personelId + ", " : "") +
            "}";
    }

}
