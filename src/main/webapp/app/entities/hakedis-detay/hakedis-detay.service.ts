import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHakedisDetay } from 'app/shared/model/hakedis-detay.model';

type EntityResponseType = HttpResponse<IHakedisDetay>;
type EntityArrayResponseType = HttpResponse<IHakedisDetay[]>;

@Injectable({ providedIn: 'root' })
export class HakedisDetayService {
    public resourceUrl = SERVER_API_URL + 'api/hakedis-detays';

    constructor(protected http: HttpClient) {}

    create(hakedisDetay: IHakedisDetay): Observable<EntityResponseType> {
        return this.http.post<IHakedisDetay>(this.resourceUrl, hakedisDetay, { observe: 'response' });
    }

    update(hakedisDetay: IHakedisDetay): Observable<EntityResponseType> {
        return this.http.put<IHakedisDetay>(this.resourceUrl, hakedisDetay, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHakedisDetay>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHakedisDetay[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
