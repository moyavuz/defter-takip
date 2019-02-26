import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonelArac } from 'app/shared/model/personel-arac.model';

type EntityResponseType = HttpResponse<IPersonelArac>;
type EntityArrayResponseType = HttpResponse<IPersonelArac[]>;

@Injectable({ providedIn: 'root' })
export class PersonelAracService {
    public resourceUrl = SERVER_API_URL + 'api/personel-aracs';

    constructor(protected http: HttpClient) {}

    create(personelArac: IPersonelArac): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personelArac);
        return this.http
            .post<IPersonelArac>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(personelArac: IPersonelArac): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personelArac);
        return this.http
            .put<IPersonelArac>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPersonelArac>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPersonelArac[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(personelArac: IPersonelArac): IPersonelArac {
        const copy: IPersonelArac = Object.assign({}, personelArac, {
            tarih: personelArac.tarih != null && personelArac.tarih.isValid() ? personelArac.tarih.format(DATE_FORMAT) : null
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
            res.body.forEach((personelArac: IPersonelArac) => {
                personelArac.tarih = personelArac.tarih != null ? moment(personelArac.tarih) : null;
            });
        }
        return res;
    }
}
