import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProje } from 'app/shared/model/proje.model';

type EntityResponseType = HttpResponse<IProje>;
type EntityArrayResponseType = HttpResponse<IProje[]>;

@Injectable({ providedIn: 'root' })
export class ProjeService {
    public resourceUrl = SERVER_API_URL + 'api/projes';

    constructor(protected http: HttpClient) {}

    create(proje: IProje): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(proje);
        return this.http
            .post<IProje>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(proje: IProje): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(proje);
        return this.http
            .put<IProje>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProje>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProje[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(proje: IProje): IProje {
        const copy: IProje = Object.assign({}, proje, {
            tarih: proje.tarih != null && proje.tarih.isValid() ? proje.tarih.format(DATE_FORMAT) : null,
            baslamaTarihi: proje.baslamaTarihi != null && proje.baslamaTarihi.isValid() ? proje.baslamaTarihi.format(DATE_FORMAT) : null,
            bitisTarihi: proje.bitisTarihi != null && proje.bitisTarihi.isValid() ? proje.bitisTarihi.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.tarih = res.body.tarih != null ? moment(res.body.tarih) : null;
            res.body.baslamaTarihi = res.body.baslamaTarihi != null ? moment(res.body.baslamaTarihi) : null;
            res.body.bitisTarihi = res.body.bitisTarihi != null ? moment(res.body.bitisTarihi) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((proje: IProje) => {
                proje.tarih = proje.tarih != null ? moment(proje.tarih) : null;
                proje.baslamaTarihi = proje.baslamaTarihi != null ? moment(proje.baslamaTarihi) : null;
                proje.bitisTarihi = proje.bitisTarihi != null ? moment(proje.bitisTarihi) : null;
            });
        }
        return res;
    }
}
