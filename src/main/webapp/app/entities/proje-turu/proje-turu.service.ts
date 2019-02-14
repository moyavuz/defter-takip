import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjeTuru } from 'app/shared/model/proje-turu.model';

type EntityResponseType = HttpResponse<IProjeTuru>;
type EntityArrayResponseType = HttpResponse<IProjeTuru[]>;

@Injectable({ providedIn: 'root' })
export class ProjeTuruService {
    public resourceUrl = SERVER_API_URL + 'api/proje-turus';

    constructor(protected http: HttpClient) {}

    create(projeTuru: IProjeTuru): Observable<EntityResponseType> {
        return this.http.post<IProjeTuru>(this.resourceUrl, projeTuru, { observe: 'response' });
    }

    update(projeTuru: IProjeTuru): Observable<EntityResponseType> {
        return this.http.put<IProjeTuru>(this.resourceUrl, projeTuru, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProjeTuru>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProjeTuru[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
