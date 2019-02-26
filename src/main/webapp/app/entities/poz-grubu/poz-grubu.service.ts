import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPozGrubu } from 'app/shared/model/poz-grubu.model';

type EntityResponseType = HttpResponse<IPozGrubu>;
type EntityArrayResponseType = HttpResponse<IPozGrubu[]>;

@Injectable({ providedIn: 'root' })
export class PozGrubuService {
    public resourceUrl = SERVER_API_URL + 'api/poz-grubus';

    constructor(protected http: HttpClient) {}

    create(pozGrubu: IPozGrubu): Observable<EntityResponseType> {
        return this.http.post<IPozGrubu>(this.resourceUrl, pozGrubu, { observe: 'response' });
    }

    update(pozGrubu: IPozGrubu): Observable<EntityResponseType> {
        return this.http.put<IPozGrubu>(this.resourceUrl, pozGrubu, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPozGrubu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPozGrubu[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
