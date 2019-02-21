import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';

type EntityResponseType = HttpResponse<IEskalasyonTuru>;
type EntityArrayResponseType = HttpResponse<IEskalasyonTuru[]>;

@Injectable({ providedIn: 'root' })
export class EskalasyonTuruService {
    public resourceUrl = SERVER_API_URL + 'api/eskalasyon-turus';

    constructor(protected http: HttpClient) {}

    create(eskalasyonTuru: IEskalasyonTuru): Observable<EntityResponseType> {
        return this.http.post<IEskalasyonTuru>(this.resourceUrl, eskalasyonTuru, { observe: 'response' });
    }

    update(eskalasyonTuru: IEskalasyonTuru): Observable<EntityResponseType> {
        return this.http.put<IEskalasyonTuru>(this.resourceUrl, eskalasyonTuru, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEskalasyonTuru>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEskalasyonTuru[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
