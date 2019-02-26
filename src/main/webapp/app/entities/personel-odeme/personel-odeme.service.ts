import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonelOdeme } from 'app/shared/model/personel-odeme.model';

type EntityResponseType = HttpResponse<IPersonelOdeme>;
type EntityArrayResponseType = HttpResponse<IPersonelOdeme[]>;

@Injectable({ providedIn: 'root' })
export class PersonelOdemeService {
    public resourceUrl = SERVER_API_URL + 'api/personel-odemes';

    constructor(protected http: HttpClient) {}

    create(personelOdeme: IPersonelOdeme): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personelOdeme);
        return this.http
            .post<IPersonelOdeme>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(personelOdeme: IPersonelOdeme): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personelOdeme);
        return this.http
            .put<IPersonelOdeme>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPersonelOdeme>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPersonelOdeme[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(personelOdeme: IPersonelOdeme): IPersonelOdeme {
        const copy: IPersonelOdeme = Object.assign({}, personelOdeme, {
            tarih: personelOdeme.tarih != null && personelOdeme.tarih.isValid() ? personelOdeme.tarih.format(DATE_FORMAT) : null
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
            res.body.forEach((personelOdeme: IPersonelOdeme) => {
                personelOdeme.tarih = personelOdeme.tarih != null ? moment(personelOdeme.tarih) : null;
            });
        }
        return res;
    }
}
