import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICommandItem } from 'app/shared/model/command-item.model';
import { Principal } from 'app/core';
import { CommandItemService } from './command-item.service';

@Component({
    selector: 'jhi-command-item',
    templateUrl: './command-item.component.html'
})
export class CommandItemComponent implements OnInit, OnDestroy {
    commandItems: ICommandItem[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private commandItemService: CommandItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.commandItemService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICommandItem[]>) => (this.commandItems = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.commandItemService.query().subscribe(
            (res: HttpResponse<ICommandItem[]>) => {
                this.commandItems = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCommandItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICommandItem) {
        return item.id;
    }

    registerChangeInCommandItems() {
        this.eventSubscriber = this.eventManager.subscribe('commandItemListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
