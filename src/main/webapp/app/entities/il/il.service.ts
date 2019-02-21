import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIl } from 'app/shared/model/il.model';

type EntityResponseType = HttpResponse<IIl>;
type EntityArrayResponseType = HttpResponse<IIl[]>;

@Injectable({ providedIn: 'root' })
export class IlService {
    public resourceUrl = SERVER_API_URL + 'api/ils';

    constructor(protected http: HttpClient) {}

    create(il: IIl): Observable<EntityResponseType> {
        return this.http.post<IIl>(this.resourceUrl, il, { observe: 'response' });
    }

    update(il: IIl): Observable<EntityResponseType> {
        return this.http.put<IIl>(this.resourceUrl, il, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIl>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIl[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
