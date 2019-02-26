import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonel } from 'app/shared/model/personel.model';

type EntityResponseType = HttpResponse<IPersonel>;
type EntityArrayResponseType = HttpResponse<IPersonel[]>;

@Injectable({ providedIn: 'root' })
export class PersonelService {
    public resourceUrl = SERVER_API_URL + 'api/personels';

    constructor(protected http: HttpClient) {}

    create(personel: IPersonel): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personel);
        return this.http
            .post<IPersonel>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(personel: IPersonel): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personel);
        return this.http
            .put<IPersonel>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPersonel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPersonel[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(personel: IPersonel): IPersonel {
        const copy: IPersonel = Object.assign({}, personel, {
            dogumTarihi: personel.dogumTarihi != null && personel.dogumTarihi.isValid() ? personel.dogumTarihi.format(DATE_FORMAT) : null,
            girisTarihi: personel.girisTarihi != null && personel.girisTarihi.isValid() ? personel.girisTarihi.format(DATE_FORMAT) : null,
            cikisTarihi: personel.cikisTarihi != null && personel.cikisTarihi.isValid() ? personel.cikisTarihi.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dogumTarihi = res.body.dogumTarihi != null ? moment(res.body.dogumTarihi) : null;
            res.body.girisTarihi = res.body.girisTarihi != null ? moment(res.body.girisTarihi) : null;
            res.body.cikisTarihi = res.body.cikisTarihi != null ? moment(res.body.cikisTarihi) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((personel: IPersonel) => {
                personel.dogumTarihi = personel.dogumTarihi != null ? moment(personel.dogumTarihi) : null;
                personel.girisTarihi = personel.girisTarihi != null ? moment(personel.girisTarihi) : null;
                personel.cikisTarihi = personel.cikisTarihi != null ? moment(personel.cikisTarihi) : null;
            });
        }
        return res;
    }
}
