import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPoz } from 'app/shared/model/poz.model';

type EntityResponseType = HttpResponse<IPoz>;
type EntityArrayResponseType = HttpResponse<IPoz[]>;

@Injectable({ providedIn: 'root' })
export class PozService {
    public resourceUrl = SERVER_API_URL + 'api/pozs';

    constructor(protected http: HttpClient) {}

    create(poz: IPoz): Observable<EntityResponseType> {
        return this.http.post<IPoz>(this.resourceUrl, poz, { observe: 'response' });
    }

    update(poz: IPoz): Observable<EntityResponseType> {
        return this.http.put<IPoz>(this.resourceUrl, poz, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPoz>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPoz[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
