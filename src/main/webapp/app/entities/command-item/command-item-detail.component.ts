import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandItem } from 'app/shared/model/command-item.model';

@Component({
    selector: 'jhi-command-item-detail',
    templateUrl: './command-item-detail.component.html'
})
export class CommandItemDetailComponent implements OnInit {
    commandItem: ICommandItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commandItem }) => {
            this.commandItem = commandItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
