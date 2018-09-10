import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPattern } from 'app/shared/model/pattern.model';

type EntityResponseType = HttpResponse<IPattern>;
type EntityArrayResponseType = HttpResponse<IPattern[]>;

@Injectable({ providedIn: 'root' })
export class PatternService {
    private resourceUrl = SERVER_API_URL + 'api/patterns';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/patterns';

    constructor(private http: HttpClient) {}

    create(pattern: IPattern): Observable<EntityResponseType> {
        return this.http.post<IPattern>(this.resourceUrl, pattern, { observe: 'response' });
    }

    update(pattern: IPattern): Observable<EntityResponseType> {
        return this.http.put<IPattern>(this.resourceUrl, pattern, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPattern>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPattern[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPattern[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
