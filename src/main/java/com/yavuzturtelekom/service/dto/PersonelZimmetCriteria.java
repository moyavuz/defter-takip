package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.ZimmetDurumu;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the PersonelZimmet entity. This class is used in PersonelZimmetResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /personel-zimmets?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonelZimmetCriteria implements Serializable {
    /**
     * Class for filtering ZimmetDurumu
     */
    public static class ZimmetDurumuFilter extends Filter<ZimmetDurumu> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter tarih;

    private ZimmetDurumuFilter durumu;

    private LongFilter personelId;

    private LongFilter zimmetId;

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

    public ZimmetDurumuFilter getDurumu() {
        return durumu;
    }

    public void setDurumu(ZimmetDurumuFilter durumu) {
        this.durumu = durumu;
    }

    public LongFilter getPersonelId() {
        return personelId;
    }

    public void setPersonelId(LongFilter personelId) {
        this.personelId = personelId;
    }

    public LongFilter getZimmetId() {
        return zimmetId;
    }

    public void setZimmetId(LongFilter zimmetId) {
        this.zimmetId = zimmetId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonelZimmetCriteria that = (PersonelZimmetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tarih, that.tarih) &&
            Objects.equals(durumu, that.durumu) &&
            Objects.equals(personelId, that.personelId) &&
            Objects.equals(zimmetId, that.zimmetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tarih,
        durumu,
        personelId,
        zimmetId
        );
    }

    @Override
    public String toString() {
        return "PersonelZimmetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tarih != null ? "tarih=" + tarih + ", " : "") +
                (durumu != null ? "durumu=" + durumu + ", " : "") +
                (personelId != null ? "personelId=" + personelId + ", " : "") +
                (zimmetId != null ? "zimmetId=" + zimmetId + ", " : "") +
            "}";
    }

}
