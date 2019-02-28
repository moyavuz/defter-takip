package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.StokHareketTuru;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the StokTakip entity. This class is used in StokTakipResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /stok-takips?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StokTakipCriteria implements Serializable {
    /**
     * Class for filtering StokHareketTuru
     */
    public static class StokHareketTuruFilter extends Filter<StokHareketTuru> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter miktar;

    private LocalDateFilter tarih;

    private StokHareketTuruFilter hareketTuru;

    private LongFilter ekipId;

    private LongFilter malzemeId;

    private LongFilter depoId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getMiktar() {
        return miktar;
    }

    public void setMiktar(LongFilter miktar) {
        this.miktar = miktar;
    }

    public LocalDateFilter getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateFilter tarih) {
        this.tarih = tarih;
    }

    public StokHareketTuruFilter getHareketTuru() {
        return hareketTuru;
    }

    public void setHareketTuru(StokHareketTuruFilter hareketTuru) {
        this.hareketTuru = hareketTuru;
    }

    public LongFilter getEkipId() {
        return ekipId;
    }

    public void setEkipId(LongFilter ekipId) {
        this.ekipId = ekipId;
    }

    public LongFilter getMalzemeId() {
        return malzemeId;
    }

    public void setMalzemeId(LongFilter malzemeId) {
        this.malzemeId = malzemeId;
    }

    public LongFilter getDepoId() {
        return depoId;
    }

    public void setDepoId(LongFilter depoId) {
        this.depoId = depoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StokTakipCriteria that = (StokTakipCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(miktar, that.miktar) &&
            Objects.equals(tarih, that.tarih) &&
            Objects.equals(hareketTuru, that.hareketTuru) &&
            Objects.equals(ekipId, that.ekipId) &&
            Objects.equals(malzemeId, that.malzemeId) &&
            Objects.equals(depoId, that.depoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        miktar,
        tarih,
        hareketTuru,
        ekipId,
        malzemeId,
        depoId
        );
    }

    @Override
    public String toString() {
        return "StokTakipCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (miktar != null ? "miktar=" + miktar + ", " : "") +
                (tarih != null ? "tarih=" + tarih + ", " : "") +
                (hareketTuru != null ? "hareketTuru=" + hareketTuru + ", " : "") +
                (ekipId != null ? "ekipId=" + ekipId + ", " : "") +
                (malzemeId != null ? "malzemeId=" + malzemeId + ", " : "") +
                (depoId != null ? "depoId=" + depoId + ", " : "") +
            "}";
    }

}
