import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBirim } from 'app/shared/model/birim.model';

type EntityResponseType = HttpResponse<IBirim>;
type EntityArrayResponseType = HttpResponse<IBirim[]>;

@Injectable({ providedIn: 'root' })
export class BirimService {
    public resourceUrl = SERVER_API_URL + 'api/birims';

    constructor(protected http: HttpClient) {}

    create(birim: IBirim): Observable<EntityResponseType> {
        return this.http.post<IBirim>(this.resourceUrl, birim, { observe: 'response' });
    }

    update(birim: IBirim): Observable<EntityResponseType> {
        return this.http.put<IBirim>(this.resourceUrl, birim, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBirim>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBirim[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
