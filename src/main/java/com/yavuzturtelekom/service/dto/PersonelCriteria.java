package com.yavuzturtelekom.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.yavuzturtelekom.domain.enumeration.Cinsiyet;
import com.yavuzturtelekom.domain.enumeration.EgitimDurumu;
import com.yavuzturtelekom.domain.enumeration.KanGrubu;
import com.yavuzturtelekom.domain.enumeration.PersonelTuru;
import com.yavuzturtelekom.domain.enumeration.MedeniHali;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Personel entity. This class is used in PersonelResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /personels?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonelCriteria implements Serializable {
    /**
     * Class for filtering Cinsiyet
     */
    public static class CinsiyetFilter extends Filter<Cinsiyet> {
    }
    /**
     * Class for filtering EgitimDurumu
     */
    public static class EgitimDurumuFilter extends Filter<EgitimDurumu> {
    }
    /**
     * Class for filtering KanGrubu
     */
    public static class KanGrubuFilter extends Filter<KanGrubu> {
    }
    /**
     * Class for filtering PersonelTuru
     */
    public static class PersonelTuruFilter extends Filter<PersonelTuru> {
    }
    /**
     * Class for filtering MedeniHali
     */
    public static class MedeniHaliFilter extends Filter<MedeniHali> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter tckimlikno;

    private StringFilter ad;

    private StringFilter cepTelefon;

    private StringFilter sabitTelefon;

    private StringFilter eposta;

    private CinsiyetFilter cinsiyet;

    private EgitimDurumuFilter egitimDurumu;

    private KanGrubuFilter kanGrubu;

    private PersonelTuruFilter personelTuru;

    private DoubleFilter ucret;

    private StringFilter iban;

    private MedeniHaliFilter medeniHali;

    private LocalDateFilter dogumTarihi;

    private LocalDateFilter girisTarihi;

    private DoubleFilter izinHakedis;

    private LocalDateFilter cikisTarihi;

    private LongFilter yoneticiId;

    private LongFilter unvanId;

    private LongFilter ekipId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTckimlikno() {
        return tckimlikno;
    }

    public void setTckimlikno(LongFilter tckimlikno) {
        this.tckimlikno = tckimlikno;
    }

    public StringFilter getAd() {
        return ad;
    }

    public void setAd(StringFilter ad) {
        this.ad = ad;
    }

    public StringFilter getCepTelefon() {
        return cepTelefon;
    }

    public void setCepTelefon(StringFilter cepTelefon) {
        this.cepTelefon = cepTelefon;
    }

    public StringFilter getSabitTelefon() {
        return sabitTelefon;
    }

    public void setSabitTelefon(StringFilter sabitTelefon) {
        this.sabitTelefon = sabitTelefon;
    }

    public StringFilter getEposta() {
        return eposta;
    }

    public void setEposta(StringFilter eposta) {
        this.eposta = eposta;
    }

    public CinsiyetFilter getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(CinsiyetFilter cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public EgitimDurumuFilter getEgitimDurumu() {
        return egitimDurumu;
    }

    public void setEgitimDurumu(EgitimDurumuFilter egitimDurumu) {
        this.egitimDurumu = egitimDurumu;
    }

    public KanGrubuFilter getKanGrubu() {
        return kanGrubu;
    }

    public void setKanGrubu(KanGrubuFilter kanGrubu) {
        this.kanGrubu = kanGrubu;
    }

    public PersonelTuruFilter getPersonelTuru() {
        return personelTuru;
    }

    public void setPersonelTuru(PersonelTuruFilter personelTuru) {
        this.personelTuru = personelTuru;
    }

    public DoubleFilter getUcret() {
        return ucret;
    }

    public void setUcret(DoubleFilter ucret) {
        this.ucret = ucret;
    }

    public StringFilter getIban() {
        return iban;
    }

    public void setIban(StringFilter iban) {
        this.iban = iban;
    }

    public MedeniHaliFilter getMedeniHali() {
        return medeniHali;
    }

    public void setMedeniHali(MedeniHaliFilter medeniHali) {
        this.medeniHali = medeniHali;
    }

    public LocalDateFilter getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(LocalDateFilter dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public LocalDateFilter getGirisTarihi() {
        return girisTarihi;
    }

    public void setGirisTarihi(LocalDateFilter girisTarihi) {
        this.girisTarihi = girisTarihi;
    }

    public DoubleFilter getIzinHakedis() {
        return izinHakedis;
    }

    public void setIzinHakedis(DoubleFilter izinHakedis) {
        this.izinHakedis = izinHakedis;
    }

    public LocalDateFilter getCikisTarihi() {
        return cikisTarihi;
    }

    public void setCikisTarihi(LocalDateFilter cikisTarihi) {
        this.cikisTarihi = cikisTarihi;
    }

    public LongFilter getYoneticiId() {
        return yoneticiId;
    }

    public void setYoneticiId(LongFilter yoneticiId) {
        this.yoneticiId = yoneticiId;
    }

    public LongFilter getUnvanId() {
        return unvanId;
    }

    public void setUnvanId(LongFilter unvanId) {
        this.unvanId = unvanId;
    }

    public LongFilter getEkipId() {
        return ekipId;
    }

    public void setEkipId(LongFilter ekipId) {
        this.ekipId = ekipId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonelCriteria that = (PersonelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tckimlikno, that.tckimlikno) &&
            Objects.equals(ad, that.ad) &&
            Objects.equals(cepTelefon, that.cepTelefon) &&
            Objects.equals(sabitTelefon, that.sabitTelefon) &&
            Objects.equals(eposta, that.eposta) &&
            Objects.equals(cinsiyet, that.cinsiyet) &&
            Objects.equals(egitimDurumu, that.egitimDurumu) &&
            Objects.equals(kanGrubu, that.kanGrubu) &&
            Objects.equals(personelTuru, that.personelTuru) &&
            Objects.equals(ucret, that.ucret) &&
            Objects.equals(iban, that.iban) &&
            Objects.equals(medeniHali, that.medeniHali) &&
            Objects.equals(dogumTarihi, that.dogumTarihi) &&
            Objects.equals(girisTarihi, that.girisTarihi) &&
            Objects.equals(izinHakedis, that.izinHakedis) &&
            Objects.equals(cikisTarihi, that.cikisTarihi) &&
            Objects.equals(yoneticiId, that.yoneticiId) &&
            Objects.equals(unvanId, that.unvanId) &&
            Objects.equals(ekipId, that.ekipId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tckimlikno,
        ad,
        cepTelefon,
        sabitTelefon,
        eposta,
        cinsiyet,
        egitimDurumu,
        kanGrubu,
        personelTuru,
        ucret,
        iban,
        medeniHali,
        dogumTarihi,
        girisTarihi,
        izinHakedis,
        cikisTarihi,
        yoneticiId,
        unvanId,
        ekipId
        );
    }

    @Override
    public String toString() {
        return "PersonelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tckimlikno != null ? "tckimlikno=" + tckimlikno + ", " : "") +
                (ad != null ? "ad=" + ad + ", " : "") +
                (cepTelefon != null ? "cepTelefon=" + cepTelefon + ", " : "") +
                (sabitTelefon != null ? "sabitTelefon=" + sabitTelefon + ", " : "") +
                (eposta != null ? "eposta=" + eposta + ", " : "") +
                (cinsiyet != null ? "cinsiyet=" + cinsiyet + ", " : "") +
                (egitimDurumu != null ? "egitimDurumu=" + egitimDurumu + ", " : "") +
                (kanGrubu != null ? "kanGrubu=" + kanGrubu + ", " : "") +
                (personelTuru != null ? "personelTuru=" + personelTuru + ", " : "") +
                (ucret != null ? "ucret=" + ucret + ", " : "") +
                (iban != null ? "iban=" + iban + ", " : "") +
                (medeniHali != null ? "medeniHali=" + medeniHali + ", " : "") +
                (dogumTarihi != null ? "dogumTarihi=" + dogumTarihi + ", " : "") +
                (girisTarihi != null ? "girisTarihi=" + girisTarihi + ", " : "") +
                (izinHakedis != null ? "izinHakedis=" + izinHakedis + ", " : "") +
                (cikisTarihi != null ? "cikisTarihi=" + cikisTarihi + ", " : "") +
                (yoneticiId != null ? "yoneticiId=" + yoneticiId + ", " : "") +
                (unvanId != null ? "unvanId=" + unvanId + ", " : "") +
                (ekipId != null ? "ekipId=" + ekipId + ", " : "") +
            "}";
    }

}
