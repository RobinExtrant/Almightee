import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILineItem } from 'app/shared/model/line-item.model';

type EntityResponseType = HttpResponse<ILineItem>;
type EntityArrayResponseType = HttpResponse<ILineItem[]>;

@Injectable({ providedIn: 'root' })
export class LineItemService {
    private resourceUrl = SERVER_API_URL + 'api/line-items';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/line-items';

    constructor(private http: HttpClient) {}

    create(lineItem: ILineItem): Observable<EntityResponseType> {
        return this.http.post<ILineItem>(this.resourceUrl, lineItem, { observe: 'response' });
    }

    update(lineItem: ILineItem): Observable<EntityResponseType> {
        return this.http.put<ILineItem>(this.resourceUrl, lineItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILineItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILineItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILineItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
