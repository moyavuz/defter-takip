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
 * Criteria class for the Birim entity. This class is used in BirimResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /birims?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BirimCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ad;

    private StringFilter aciklama;

    private StringFilter kisaltma;

    private DoubleFilter carpan;

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

    public StringFilter getAciklama() {
        return aciklama;
    }

    public void setAciklama(StringFilter aciklama) {
        this.aciklama = aciklama;
    }

    public StringFilter getKisaltma() {
        return kisaltma;
    }

    public void setKisaltma(StringFilter kisaltma) {
        this.kisaltma = kisaltma;
    }

    public DoubleFilter getCarpan() {
        return carpan;
    }

    public void setCarpan(DoubleFilter carpan) {
        this.carpan = carpan;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BirimCriteria that = (BirimCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(aciklama, that.aciklama) &&
            Objects.equals(kisaltma, that.kisaltma) &&
            Objects.equals(carpan, that.carpan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ad,
        aciklama,
        kisaltma,
        carpan
        );
    }

    @Override
    public String toString() {
        return "BirimCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (aciklama != null ? "aciklama=" + aciklama + ", " : "") +
                (kisaltma != null ? "kisaltma=" + kisaltma + ", " : "") +
                (carpan != null ? "carpan=" + carpan + ", " : "") +
            "}";
    }

}
