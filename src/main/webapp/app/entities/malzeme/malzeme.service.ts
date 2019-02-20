import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMalzeme } from 'app/shared/model/malzeme.model';

type EntityResponseType = HttpResponse<IMalzeme>;
type EntityArrayResponseType = HttpResponse<IMalzeme[]>;

@Injectable({ providedIn: 'root' })
export class MalzemeService {
    public resourceUrl = SERVER_API_URL + 'api/malzemes';

    constructor(protected http: HttpClient) {}

    create(malzeme: IMalzeme): Observable<EntityResponseType> {
        return this.http.post<IMalzeme>(this.resourceUrl, malzeme, { observe: 'response' });
    }

    update(malzeme: IMalzeme): Observable<EntityResponseType> {
        return this.http.put<IMalzeme>(this.resourceUrl, malzeme, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMalzeme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMalzeme[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
