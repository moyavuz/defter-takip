import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDefterTuru } from 'app/shared/model/defter-turu.model';

type EntityResponseType = HttpResponse<IDefterTuru>;
type EntityArrayResponseType = HttpResponse<IDefterTuru[]>;

@Injectable({ providedIn: 'root' })
export class DefterTuruService {
    public resourceUrl = SERVER_API_URL + 'api/defter-turus';

    constructor(protected http: HttpClient) {}

    create(defterTuru: IDefterTuru): Observable<EntityResponseType> {
        return this.http.post<IDefterTuru>(this.resourceUrl, defterTuru, { observe: 'response' });
    }

    update(defterTuru: IDefterTuru): Observable<EntityResponseType> {
        return this.http.put<IDefterTuru>(this.resourceUrl, defterTuru, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDefterTuru>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDefterTuru[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
