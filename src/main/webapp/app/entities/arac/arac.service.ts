import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IArac } from 'app/shared/model/arac.model';

type EntityResponseType = HttpResponse<IArac>;
type EntityArrayResponseType = HttpResponse<IArac[]>;

@Injectable({ providedIn: 'root' })
export class AracService {
    public resourceUrl = SERVER_API_URL + 'api/aracs';

    constructor(protected http: HttpClient) {}

    create(arac: IArac): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(arac);
        return this.http
            .post<IArac>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(arac: IArac): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(arac);
        return this.http
            .put<IArac>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IArac>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IArac[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(arac: IArac): IArac {
        const copy: IArac = Object.assign({}, arac, {
            tarih: arac.tarih != null && arac.tarih.isValid() ? arac.tarih.format(DATE_FORMAT) : null,
            muayeneTarih: arac.muayeneTarih != null && arac.muayeneTarih.isValid() ? arac.muayeneTarih.format(DATE_FORMAT) : null,
            kaskoTarih: arac.kaskoTarih != null && arac.kaskoTarih.isValid() ? arac.kaskoTarih.format(DATE_FORMAT) : null,
            sigortaTarih: arac.sigortaTarih != null && arac.sigortaTarih.isValid() ? arac.sigortaTarih.format(DATE_FORMAT) : null,
            bakimTarih: arac.bakimTarih != null && arac.bakimTarih.isValid() ? arac.bakimTarih.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.tarih = res.body.tarih != null ? moment(res.body.tarih) : null;
            res.body.muayeneTarih = res.body.muayeneTarih != null ? moment(res.body.muayeneTarih) : null;
            res.body.kaskoTarih = res.body.kaskoTarih != null ? moment(res.body.kaskoTarih) : null;
            res.body.sigortaTarih = res.body.sigortaTarih != null ? moment(res.body.sigortaTarih) : null;
            res.body.bakimTarih = res.body.bakimTarih != null ? moment(res.body.bakimTarih) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((arac: IArac) => {
                arac.tarih = arac.tarih != null ? moment(arac.tarih) : null;
                arac.muayeneTarih = arac.muayeneTarih != null ? moment(arac.muayeneTarih) : null;
                arac.kaskoTarih = arac.kaskoTarih != null ? moment(arac.kaskoTarih) : null;
                arac.sigortaTarih = arac.sigortaTarih != null ? moment(arac.sigortaTarih) : null;
                arac.bakimTarih = arac.bakimTarih != null ? moment(arac.bakimTarih) : null;
            });
        }
        return res;
    }
}
