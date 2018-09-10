import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILineItem } from 'app/shared/model/line-item.model';

@Component({
    selector: 'jhi-line-item-detail',
    templateUrl: './line-item-detail.component.html'
})
export class LineItemDetailComponent implements OnInit {
    lineItem: ILineItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lineItem }) => {
            this.lineItem = lineItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
