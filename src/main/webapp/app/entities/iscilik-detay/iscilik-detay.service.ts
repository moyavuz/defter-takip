import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIscilikDetay } from 'app/shared/model/iscilik-detay.model';

type EntityResponseType = HttpResponse<IIscilikDetay>;
type EntityArrayResponseType = HttpResponse<IIscilikDetay[]>;

@Injectable({ providedIn: 'root' })
export class IscilikDetayService {
    public resourceUrl = SERVER_API_URL + 'api/iscilik-detays';

    constructor(protected http: HttpClient) {}

    create(iscilikDetay: IIscilikDetay): Observable<EntityResponseType> {
        return this.http.post<IIscilikDetay>(this.resourceUrl, iscilikDetay, { observe: 'response' });
    }

    update(iscilikDetay: IIscilikDetay): Observable<EntityResponseType> {
        return this.http.put<IIscilikDetay>(this.resourceUrl, iscilikDetay, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIscilikDetay>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIscilikDetay[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
