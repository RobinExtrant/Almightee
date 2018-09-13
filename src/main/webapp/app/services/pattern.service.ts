import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { map, catchError, retry } from 'rxjs/operators';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { environment as env } from '../../environments/environment';
import { Pattern } from './../shared/model/pattern.model';

@Injectable({
    providedIn: 'root'
})
export class PatternService {
    constructor(private http: HttpClient) {}

    public all(): Observable<PatternsResponse> {
        return this.http.get(`${env.serverUrl}patterns`).pipe(
            map((body: any) => {
                console.log('body=', JSON.stringify(body, null, 2));
                return { err: null, patterns: body };
            }),
            catchError(this.handleErrorPatterns)
        );
    }

    private handleErrorPatterns(error: HttpErrorResponse): Observable<PatternsResponse> {
        console.log('Server error to find todos: ' + JSON.stringify(error, null, 2));
        return of({ err: error, patterns: null });
    }

    private handleErrorPattern(error: HttpErrorResponse): Observable<PatternResponse> {
        console.log('Server error to find todo: ' + JSON.stringify(error, null, 2));
        return of({ err: error, pattern: null });
    }
}
export interface PatternsResponse {
    err: any;
    patterns: Pattern[];
}
export interface PatternResponse {
    err: any;
    pattern: Pattern;
}
