package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.PersonelTuru;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Ekip entity. This class is used in EkipResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /ekips?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EkipCriteria implements Serializable {
    /**
     * Class for filtering PersonelTuru
     */
    public static class PersonelTuruFilter extends Filter<PersonelTuru> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private StringFilter telefon;

    private StringFilter eposta;

    private PersonelTuruFilter turu;

    private DoubleFilter tenzilatOran;

    private LongFilter hakedisId;

    private LongFilter stokTakipId;

    private LongFilter ekipSorumluId;

    private LongFilter mudurlukId;

    private LongFilter ekipPersonelId;

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

    public StringFilter getTelefon() {
        return telefon;
    }

    public void setTelefon(StringFilter telefon) {
        this.telefon = telefon;
    }

    public StringFilter getEposta() {
        return eposta;
    }

    public void setEposta(StringFilter eposta) {
        this.eposta = eposta;
    }

    public PersonelTuruFilter getTuru() {
        return turu;
    }

    public void setTuru(PersonelTuruFilter turu) {
        this.turu = turu;
    }

    public DoubleFilter getTenzilatOran() {
        return tenzilatOran;
    }

    public void setTenzilatOran(DoubleFilter tenzilatOran) {
        this.tenzilatOran = tenzilatOran;
    }

    public LongFilter getHakedisId() {
        return hakedisId;
    }

    public void setHakedisId(LongFilter hakedisId) {
        this.hakedisId = hakedisId;
    }

    public LongFilter getStokTakipId() {
        return stokTakipId;
    }

    public void setStokTakipId(LongFilter stokTakipId) {
        this.stokTakipId = stokTakipId;
    }

    public LongFilter getEkipSorumluId() {
        return ekipSorumluId;
    }

    public void setEkipSorumluId(LongFilter ekipSorumluId) {
        this.ekipSorumluId = ekipSorumluId;
    }

    public LongFilter getMudurlukId() {
        return mudurlukId;
    }

    public void setMudurlukId(LongFilter mudurlukId) {
        this.mudurlukId = mudurlukId;
    }

    public LongFilter getEkipPersonelId() {
        return ekipPersonelId;
    }

    public void setEkipPersonelId(LongFilter ekipPersonelId) {
        this.ekipPersonelId = ekipPersonelId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EkipCriteria that = (EkipCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(telefon, that.telefon) &&
            Objects.equals(eposta, that.eposta) &&
            Objects.equals(turu, that.turu) &&
            Objects.equals(tenzilatOran, that.tenzilatOran) &&
            Objects.equals(hakedisId, that.hakedisId) &&
            Objects.equals(stokTakipId, that.stokTakipId) &&
            Objects.equals(ekipSorumluId, that.ekipSorumluId) &&
            Objects.equals(mudurlukId, that.mudurlukId) &&
            Objects.equals(ekipPersonelId, that.ekipPersonelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        telefon,
        eposta,
        turu,
        tenzilatOran,
        hakedisId,
        stokTakipId,
        ekipSorumluId,
        mudurlukId,
        ekipPersonelId
        );
    }

    @Override
    public String toString() {
        return "EkipCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (telefon != null ? "telefon=" + telefon + ", " : "") +
                (eposta != null ? "eposta=" + eposta + ", " : "") +
                (turu != null ? "turu=" + turu + ", " : "") +
                (tenzilatOran != null ? "tenzilatOran=" + tenzilatOran + ", " : "") +
                (hakedisId != null ? "hakedisId=" + hakedisId + ", " : "") +
                (stokTakipId != null ? "stokTakipId=" + stokTakipId + ", " : "") +
                (ekipSorumluId != null ? "ekipSorumluId=" + ekipSorumluId + ", " : "") +
                (mudurlukId != null ? "mudurlukId=" + mudurlukId + ", " : "") +
                (ekipPersonelId != null ? "ekipPersonelId=" + ekipPersonelId + ", " : "") +
            "}";
    }

}
