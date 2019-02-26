import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEskalasyon } from 'app/shared/model/eskalasyon.model';

type EntityResponseType = HttpResponse<IEskalasyon>;
type EntityArrayResponseType = HttpResponse<IEskalasyon[]>;

@Injectable({ providedIn: 'root' })
export class EskalasyonService {
    public resourceUrl = SERVER_API_URL + 'api/eskalasyons';

    constructor(protected http: HttpClient) {}

    create(eskalasyon: IEskalasyon): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(eskalasyon);
        return this.http
            .post<IEskalasyon>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(eskalasyon: IEskalasyon): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(eskalasyon);
        return this.http
            .put<IEskalasyon>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEskalasyon>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEskalasyon[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(eskalasyon: IEskalasyon): IEskalasyon {
        const copy: IEskalasyon = Object.assign({}, eskalasyon, {
            tarih: eskalasyon.tarih != null && eskalasyon.tarih.isValid() ? eskalasyon.tarih.format(DATE_FORMAT) : null
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
            res.body.forEach((eskalasyon: IEskalasyon) => {
                eskalasyon.tarih = eskalasyon.tarih != null ? moment(eskalasyon.tarih) : null;
            });
        }
        return res;
    }
}
