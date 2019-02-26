import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHakedisTuru } from 'app/shared/model/hakedis-turu.model';

type EntityResponseType = HttpResponse<IHakedisTuru>;
type EntityArrayResponseType = HttpResponse<IHakedisTuru[]>;

@Injectable({ providedIn: 'root' })
export class HakedisTuruService {
    public resourceUrl = SERVER_API_URL + 'api/hakedis-turus';

    constructor(protected http: HttpClient) {}

    create(hakedisTuru: IHakedisTuru): Observable<EntityResponseType> {
        return this.http.post<IHakedisTuru>(this.resourceUrl, hakedisTuru, { observe: 'response' });
    }

    update(hakedisTuru: IHakedisTuru): Observable<EntityResponseType> {
        return this.http.put<IHakedisTuru>(this.resourceUrl, hakedisTuru, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHakedisTuru>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHakedisTuru[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
