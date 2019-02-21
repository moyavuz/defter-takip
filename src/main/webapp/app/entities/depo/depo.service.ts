import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepo } from 'app/shared/model/depo.model';

type EntityResponseType = HttpResponse<IDepo>;
type EntityArrayResponseType = HttpResponse<IDepo[]>;

@Injectable({ providedIn: 'root' })
export class DepoService {
    public resourceUrl = SERVER_API_URL + 'api/depos';

    constructor(protected http: HttpClient) {}

    create(depo: IDepo): Observable<EntityResponseType> {
        return this.http.post<IDepo>(this.resourceUrl, depo, { observe: 'response' });
    }

    update(depo: IDepo): Observable<EntityResponseType> {
        return this.http.put<IDepo>(this.resourceUrl, depo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDepo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDepo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
