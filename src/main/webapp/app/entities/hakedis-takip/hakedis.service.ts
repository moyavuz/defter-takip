import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHakedis } from 'app/shared/model/hakedis.model';

type EntityResponseType = HttpResponse<IHakedis>;
type EntityArrayResponseType = HttpResponse<IHakedis[]>;

@Injectable({ providedIn: 'root' })
export class HakedisService {
    public resourceUrl = SERVER_API_URL + 'api/hakedis';

    constructor(protected http: HttpClient) {}

    create(hakedis: IHakedis): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(hakedis);
        return this.http
            .post<IHakedis>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(hakedis: IHakedis): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(hakedis);
        return this.http
            .put<IHakedis>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHakedis>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHakedis[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(hakedis: IHakedis): IHakedis {
        const copy: IHakedis = Object.assign({}, hakedis, {
            tarih: hakedis.tarih != null && hakedis.tarih.isValid() ? hakedis.tarih.format(DATE_FORMAT) : null
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
            res.body.forEach((hakedis: IHakedis) => {
                hakedis.tarih = hakedis.tarih != null ? moment(hakedis.tarih) : null;
            });
        }
        return res;
    }
}
