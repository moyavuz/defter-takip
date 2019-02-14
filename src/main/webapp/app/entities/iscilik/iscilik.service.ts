import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIscilik } from 'app/shared/model/iscilik.model';

type EntityResponseType = HttpResponse<IIscilik>;
type EntityArrayResponseType = HttpResponse<IIscilik[]>;

@Injectable({ providedIn: 'root' })
export class IscilikService {
    public resourceUrl = SERVER_API_URL + 'api/isciliks';

    constructor(protected http: HttpClient) {}

    create(iscilik: IIscilik): Observable<EntityResponseType> {
        return this.http.post<IIscilik>(this.resourceUrl, iscilik, { observe: 'response' });
    }

    update(iscilik: IIscilik): Observable<EntityResponseType> {
        return this.http.put<IIscilik>(this.resourceUrl, iscilik, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIscilik>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIscilik[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
