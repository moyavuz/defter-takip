import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICalisan } from 'app/shared/model/calisan.model';

type EntityResponseType = HttpResponse<ICalisan>;
type EntityArrayResponseType = HttpResponse<ICalisan[]>;

@Injectable({ providedIn: 'root' })
export class CalisanService {
    public resourceUrl = SERVER_API_URL + 'api/calisans';

    constructor(protected http: HttpClient) {}

    create(calisan: ICalisan): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(calisan);
        return this.http
            .post<ICalisan>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(calisan: ICalisan): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(calisan);
        return this.http
            .put<ICalisan>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICalisan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICalisan[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(calisan: ICalisan): ICalisan {
        const copy: ICalisan = Object.assign({}, calisan, {
            girisTarihi: calisan.girisTarihi != null && calisan.girisTarihi.isValid() ? calisan.girisTarihi.toJSON() : null,
            cikisTarihi: calisan.cikisTarihi != null && calisan.cikisTarihi.isValid() ? calisan.cikisTarihi.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.girisTarihi = res.body.girisTarihi != null ? moment(res.body.girisTarihi) : null;
            res.body.cikisTarihi = res.body.cikisTarihi != null ? moment(res.body.cikisTarihi) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((calisan: ICalisan) => {
                calisan.girisTarihi = calisan.girisTarihi != null ? moment(calisan.girisTarihi) : null;
                calisan.cikisTarihi = calisan.cikisTarihi != null ? moment(calisan.cikisTarihi) : null;
            });
        }
        return res;
    }
}
