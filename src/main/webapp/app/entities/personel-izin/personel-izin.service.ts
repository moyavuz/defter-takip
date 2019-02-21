import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonelIzin } from 'app/shared/model/personel-izin.model';

type EntityResponseType = HttpResponse<IPersonelIzin>;
type EntityArrayResponseType = HttpResponse<IPersonelIzin[]>;

@Injectable({ providedIn: 'root' })
export class PersonelIzinService {
    public resourceUrl = SERVER_API_URL + 'api/personel-izins';

    constructor(protected http: HttpClient) {}

    create(personelIzin: IPersonelIzin): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personelIzin);
        return this.http
            .post<IPersonelIzin>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(personelIzin: IPersonelIzin): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(personelIzin);
        return this.http
            .put<IPersonelIzin>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPersonelIzin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPersonelIzin[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(personelIzin: IPersonelIzin): IPersonelIzin {
        const copy: IPersonelIzin = Object.assign({}, personelIzin, {
            tarih: personelIzin.tarih != null && personelIzin.tarih.isValid() ? personelIzin.tarih.format(DATE_FORMAT) : null
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
            res.body.forEach((personelIzin: IPersonelIzin) => {
                personelIzin.tarih = personelIzin.tarih != null ? moment(personelIzin.tarih) : null;
            });
        }
        return res;
    }
}
