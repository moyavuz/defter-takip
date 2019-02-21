import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDovizKur } from 'app/shared/model/doviz-kur.model';

type EntityResponseType = HttpResponse<IDovizKur>;
type EntityArrayResponseType = HttpResponse<IDovizKur[]>;

@Injectable({ providedIn: 'root' })
export class DovizKurService {
    public resourceUrl = SERVER_API_URL + 'api/doviz-kurs';

    constructor(protected http: HttpClient) {}

    create(dovizKur: IDovizKur): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dovizKur);
        return this.http
            .post<IDovizKur>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dovizKur: IDovizKur): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dovizKur);
        return this.http
            .put<IDovizKur>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDovizKur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDovizKur[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(dovizKur: IDovizKur): IDovizKur {
        const copy: IDovizKur = Object.assign({}, dovizKur, {
            tarih: dovizKur.tarih != null && dovizKur.tarih.isValid() ? dovizKur.tarih.format(DATE_FORMAT) : null
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
            res.body.forEach((dovizKur: IDovizKur) => {
                dovizKur.tarih = dovizKur.tarih != null ? moment(dovizKur.tarih) : null;
            });
        }
        return res;
    }
}
