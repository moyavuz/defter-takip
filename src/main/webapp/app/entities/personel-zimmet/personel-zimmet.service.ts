import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonelZimmet } from 'app/shared/model/personel-zimmet.model';

type EntityResponseType = HttpResponse<IPersonelZimmet>;
type EntityArrayResponseType = HttpResponse<IPersonelZimmet[]>;

@Injectable({ providedIn: 'root' })
export class PersonelZimmetService {
    public resourceUrl = SERVER_API_URL + 'api/personel-zimmets';

    constructor(protected http: HttpClient) {}

    create(personelZimmet: IPersonelZimmet): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personelZimmet);
        return this.http
            .post<IPersonelZimmet>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(personelZimmet: IPersonelZimmet): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personelZimmet);
        return this.http
            .put<IPersonelZimmet>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPersonelZimmet>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPersonelZimmet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(personelZimmet: IPersonelZimmet): IPersonelZimmet {
        const copy: IPersonelZimmet = Object.assign({}, personelZimmet, {
            tarih: personelZimmet.tarih != null && personelZimmet.tarih.isValid() ? personelZimmet.tarih.format(DATE_FORMAT) : null
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
            res.body.forEach((personelZimmet: IPersonelZimmet) => {
                personelZimmet.tarih = personelZimmet.tarih != null ? moment(personelZimmet.tarih) : null;
            });
        }
        return res;
    }
}
