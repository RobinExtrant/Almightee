import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPattern } from 'app/shared/model/pattern.model';

@Component({
    selector: 'jhi-pattern-detail',
    templateUrl: './pattern-detail.component.html'
})
export class PatternDetailComponent implements OnInit {
    pattern: IPattern;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pattern }) => {
            this.pattern = pattern;
        });
    }

    previousState() {
        window.history.back();
    }
}
