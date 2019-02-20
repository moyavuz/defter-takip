import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMarka } from 'app/shared/model/marka.model';

type EntityResponseType = HttpResponse<IMarka>;
type EntityArrayResponseType = HttpResponse<IMarka[]>;

@Injectable({ providedIn: 'root' })
export class MarkaService {
    public resourceUrl = SERVER_API_URL + 'api/markas';

    constructor(protected http: HttpClient) {}

    create(marka: IMarka): Observable<EntityResponseType> {
        return this.http.post<IMarka>(this.resourceUrl, marka, { observe: 'response' });
    }

    update(marka: IMarka): Observable<EntityResponseType> {
        return this.http.put<IMarka>(this.resourceUrl, marka, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMarka>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMarka[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
