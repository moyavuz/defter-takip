import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IModel } from 'app/shared/model/model.model';

type EntityResponseType = HttpResponse<IModel>;
type EntityArrayResponseType = HttpResponse<IModel[]>;

@Injectable({ providedIn: 'root' })
export class ModelService {
    public resourceUrl = SERVER_API_URL + 'api/models';

    constructor(protected http: HttpClient) {}

    create(model: IModel): Observable<EntityResponseType> {
        return this.http.post<IModel>(this.resourceUrl, model, { observe: 'response' });
    }

    update(model: IModel): Observable<EntityResponseType> {
        return this.http.put<IModel>(this.resourceUrl, model, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IModel[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
