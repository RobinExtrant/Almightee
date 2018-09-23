import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICommandItem } from 'app/shared/model/command-item.model';
import { CommandItemService } from './command-item.service';
import { IPattern } from 'app/shared/model/pattern.model';
import { PatternService } from 'app/entities/pattern';
import { ICommand } from 'app/shared/model/command.model';
import { CommandService } from 'app/entities/command';

@Component({
    selector: 'jhi-command-item-update',
    templateUrl: './command-item-update.component.html'
})
export class CommandItemUpdateComponent implements OnInit {
    private _commandItem: ICommandItem;
    isSaving: boolean;

    patterns: IPattern[];

    commands: ICommand[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private commandItemService: CommandItemService,
        private patternService: PatternService,
        private commandService: CommandService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commandItem }) => {
            this.commandItem = commandItem;
        });
        this.patternService.query().subscribe(
            (res: HttpResponse<IPattern[]>) => {
                this.patterns = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.commandService.query().subscribe(
            (res: HttpResponse<ICommand[]>) => {
                this.commands = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commandItem.id !== undefined) {
            this.subscribeToSaveResponse(this.commandItemService.update(this.commandItem));
        } else {
            this.subscribeToSaveResponse(this.commandItemService.create(this.commandItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICommandItem>>) {
        result.subscribe((res: HttpResponse<ICommandItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPatternById(index: number, item: IPattern) {
        return item.id;
    }

    trackCommandById(index: number, item: ICommand) {
        return item.id;
    }
    get commandItem() {
        return this._commandItem;
    }

    set commandItem(commandItem: ICommandItem) {
        this._commandItem = commandItem;
    }
}
