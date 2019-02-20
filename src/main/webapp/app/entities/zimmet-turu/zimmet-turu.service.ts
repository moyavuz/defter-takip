import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IZimmetTuru } from 'app/shared/model/zimmet-turu.model';

type EntityResponseType = HttpResponse<IZimmetTuru>;
type EntityArrayResponseType = HttpResponse<IZimmetTuru[]>;

@Injectable({ providedIn: 'root' })
export class ZimmetTuruService {
    public resourceUrl = SERVER_API_URL + 'api/zimmet-turus';

    constructor(protected http: HttpClient) {}

    create(zimmetTuru: IZimmetTuru): Observable<EntityResponseType> {
        return this.http.post<IZimmetTuru>(this.resourceUrl, zimmetTuru, { observe: 'response' });
    }

    update(zimmetTuru: IZimmetTuru): Observable<EntityResponseType> {
        return this.http.put<IZimmetTuru>(this.resourceUrl, zimmetTuru, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IZimmetTuru>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IZimmetTuru[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
