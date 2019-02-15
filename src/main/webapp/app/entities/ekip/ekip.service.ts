import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEkip } from 'app/shared/model/ekip.model';

type EntityResponseType = HttpResponse<IEkip>;
type EntityArrayResponseType = HttpResponse<IEkip[]>;

@Injectable({ providedIn: 'root' })
export class EkipService {
    public resourceUrl = SERVER_API_URL + 'api/ekips';

    constructor(protected http: HttpClient) {}

    create(ekip: IEkip): Observable<EntityResponseType> {
        return this.http.post<IEkip>(this.resourceUrl, ekip, { observe: 'response' });
    }

    update(ekip: IEkip): Observable<EntityResponseType> {
        return this.http.put<IEkip>(this.resourceUrl, ekip, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEkip>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEkip[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
