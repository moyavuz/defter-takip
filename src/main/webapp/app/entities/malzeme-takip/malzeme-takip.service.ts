import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';

type EntityResponseType = HttpResponse<IMalzemeTakip>;
type EntityArrayResponseType = HttpResponse<IMalzemeTakip[]>;

@Injectable({ providedIn: 'root' })
export class MalzemeTakipService {
    public resourceUrl = SERVER_API_URL + 'api/malzeme-takips';

    constructor(protected http: HttpClient) {}

    create(malzemeTakip: IMalzemeTakip): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(malzemeTakip);
        return this.http
            .post<IMalzemeTakip>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(malzemeTakip: IMalzemeTakip): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(malzemeTakip);
        return this.http
            .put<IMalzemeTakip>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMalzemeTakip>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMalzemeTakip[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(malzemeTakip: IMalzemeTakip): IMalzemeTakip {
        const copy: IMalzemeTakip = Object.assign({}, malzemeTakip, {
            tarih: malzemeTakip.tarih != null && malzemeTakip.tarih.isValid() ? malzemeTakip.tarih.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.tarih = res.body.tarih != null ? moment(res.body.tarih) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((malzemeTakip: IMalzemeTakip) => {
                malzemeTakip.tarih = malzemeTakip.tarih != null ? moment(malzemeTakip.tarih) : null;
            });
        }
        return res;
    }
}
