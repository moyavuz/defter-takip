import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStokTakip } from 'app/shared/model/stok-takip.model';

type EntityResponseType = HttpResponse<IStokTakip>;
type EntityArrayResponseType = HttpResponse<IStokTakip[]>;

@Injectable({ providedIn: 'root' })
export class StokTakipService {
    public resourceUrl = SERVER_API_URL + 'api/stok-takips';

    constructor(protected http: HttpClient) {}

    create(stokTakip: IStokTakip): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stokTakip);
        return this.http
            .post<IStokTakip>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stokTakip: IStokTakip): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stokTakip);
        return this.http
            .put<IStokTakip>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStokTakip>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStokTakip[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(stokTakip: IStokTakip): IStokTakip {
        const copy: IStokTakip = Object.assign({}, stokTakip, {
            tarih: stokTakip.tarih != null && stokTakip.tarih.isValid() ? stokTakip.tarih.format(DATE_FORMAT) : null
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
            res.body.forEach((stokTakip: IStokTakip) => {
                stokTakip.tarih = stokTakip.tarih != null ? moment(stokTakip.tarih) : null;
            });
        }
        return res;
    }
}
