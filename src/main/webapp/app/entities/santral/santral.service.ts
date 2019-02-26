import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISantral } from 'app/shared/model/santral.model';

type EntityResponseType = HttpResponse<ISantral>;
type EntityArrayResponseType = HttpResponse<ISantral[]>;

@Injectable({ providedIn: 'root' })
export class SantralService {
    public resourceUrl = SERVER_API_URL + 'api/santrals';

    constructor(protected http: HttpClient) {}

    create(santral: ISantral): Observable<EntityResponseType> {
        return this.http.post<ISantral>(this.resourceUrl, santral, { observe: 'response' });
    }

    update(santral: ISantral): Observable<EntityResponseType> {
        return this.http.put<ISantral>(this.resourceUrl, santral, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISantral>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISantral[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
