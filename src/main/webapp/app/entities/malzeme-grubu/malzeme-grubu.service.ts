import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

type EntityResponseType = HttpResponse<IMalzemeGrubu>;
type EntityArrayResponseType = HttpResponse<IMalzemeGrubu[]>;

@Injectable({ providedIn: 'root' })
export class MalzemeGrubuService {
    public resourceUrl = SERVER_API_URL + 'api/malzeme-grubus';

    constructor(protected http: HttpClient) {}

    create(malzemeGrubu: IMalzemeGrubu): Observable<EntityResponseType> {
        return this.http.post<IMalzemeGrubu>(this.resourceUrl, malzemeGrubu, { observe: 'response' });
    }

    update(malzemeGrubu: IMalzemeGrubu): Observable<EntityResponseType> {
        return this.http.put<IMalzemeGrubu>(this.resourceUrl, malzemeGrubu, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMalzemeGrubu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMalzemeGrubu[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
