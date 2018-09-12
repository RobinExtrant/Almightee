import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { ICart } from 'app/shared/model/cart.model';
import { CartService } from 'app/entities/cart';

@Component({
    selector: 'jhi-customer-update',
    templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
    private _customer: ICustomer;
    isSaving: boolean;

    currentcarts: ICart[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private customerService: CustomerService,
        private cartService: CartService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customer }) => {
            this.customer = customer;
        });
        this.cartService.query({ filter: 'customer-is-null' }).subscribe(
            (res: HttpResponse<ICart[]>) => {
                if (!this.customer.currentCart || !this.customer.currentCart.id) {
                    this.currentcarts = res.body;
                } else {
                    this.cartService.find(this.customer.currentCart.id).subscribe(
                        (subRes: HttpResponse<ICart>) => {
                            this.currentcarts = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customer.id !== undefined) {
            this.subscribeToSaveResponse(this.customerService.update(this.customer));
        } else {
            this.subscribeToSaveResponse(this.customerService.create(this.customer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
        result.subscribe((res: HttpResponse<ICustomer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get customer() {
        return this._customer;
    }

    set customer(customer: ICustomer) {
        this._customer = customer;
    }
}
