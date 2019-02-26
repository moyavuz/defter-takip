import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMudurluk } from 'app/shared/model/mudurluk.model';

type EntityResponseType = HttpResponse<IMudurluk>;
type EntityArrayResponseType = HttpResponse<IMudurluk[]>;

@Injectable({ providedIn: 'root' })
export class MudurlukService {
    public resourceUrl = SERVER_API_URL + 'api/mudurluks';

    constructor(protected http: HttpClient) {}

    create(mudurluk: IMudurluk): Observable<EntityResponseType> {
        return this.http.post<IMudurluk>(this.resourceUrl, mudurluk, { observe: 'response' });
    }

    update(mudurluk: IMudurluk): Observable<EntityResponseType> {
        return this.http.put<IMudurluk>(this.resourceUrl, mudurluk, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMudurluk>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMudurluk[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
