import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommandItem } from 'app/shared/model/command-item.model';

type EntityResponseType = HttpResponse<ICommandItem>;
type EntityArrayResponseType = HttpResponse<ICommandItem[]>;

@Injectable({ providedIn: 'root' })
export class CommandItemService {
    private resourceUrl = SERVER_API_URL + 'api/command-items';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/command-items';

    constructor(private http: HttpClient) {}

    create(commandItem: ICommandItem): Observable<EntityResponseType> {
        return this.http.post<ICommandItem>(this.resourceUrl, commandItem, { observe: 'response' });
    }

    update(commandItem: ICommandItem): Observable<EntityResponseType> {
        return this.http.put<ICommandItem>(this.resourceUrl, commandItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICommandItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICommandItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICommandItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
