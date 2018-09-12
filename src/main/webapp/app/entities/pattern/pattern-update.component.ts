import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPattern } from 'app/shared/model/pattern.model';
import { PatternService } from './pattern.service';

@Component({
    selector: 'jhi-pattern-update',
    templateUrl: './pattern-update.component.html'
})
export class PatternUpdateComponent implements OnInit {
    private _pattern: IPattern;
    isSaving: boolean;

    constructor(private patternService: PatternService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pattern }) => {
            this.pattern = pattern;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pattern.id !== undefined) {
            this.subscribeToSaveResponse(this.patternService.update(this.pattern));
        } else {
            this.subscribeToSaveResponse(this.patternService.create(this.pattern));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPattern>>) {
        result.subscribe((res: HttpResponse<IPattern>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get pattern() {
        return this._pattern;
    }

    set pattern(pattern: IPattern) {
        this._pattern = pattern;
    }
}
