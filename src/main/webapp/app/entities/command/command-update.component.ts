import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICommand } from 'app/shared/model/command.model';
import { CommandService } from './command.service';
import { ICart } from 'app/shared/model/cart.model';
import { CartService } from 'app/entities/cart';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
    selector: 'jhi-command-update',
    templateUrl: './command-update.component.html'
})
export class CommandUpdateComponent implements OnInit {
    private _command: ICommand;
    isSaving: boolean;

    carts: ICart[];

    customers: ICustomer[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private commandService: CommandService,
        private cartService: CartService,
        private customerService: CustomerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ command }) => {
            this.command = command;
        });
        this.cartService.query({ filter: 'command-is-null' }).subscribe(
            (res: HttpResponse<ICart[]>) => {
                if (!this.command.cart || !this.command.cart.id) {
                    this.carts = res.body;
                } else {
                    this.cartService.find(this.command.cart.id).subscribe(
                        (subRes: HttpResponse<ICart>) => {
                            this.carts = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.customerService.query().subscribe(
            (res: HttpResponse<ICustomer[]>) => {
                this.customers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.command.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.command.id !== undefined) {
            this.subscribeToSaveResponse(this.commandService.update(this.command));
        } else {
            this.subscribeToSaveResponse(this.commandService.create(this.command));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICommand>>) {
        result.subscribe((res: HttpResponse<ICommand>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCartById(index: number, item: ICart) {
        return item.id;
    }

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }
    get command() {
        return this._command;
    }

    set command(command: ICommand) {
        this._command = command;
        this.date = moment(command.date).format(DATE_TIME_FORMAT);
    }
}
