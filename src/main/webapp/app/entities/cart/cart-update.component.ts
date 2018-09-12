import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICart } from 'app/shared/model/cart.model';
import { CartService } from './cart.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
    selector: 'jhi-cart-update',
    templateUrl: './cart-update.component.html'
})
export class CartUpdateComponent implements OnInit {
    private _cart: ICart;
    isSaving: boolean;

    customers: ICustomer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private cartService: CartService,
        private customerService: CustomerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cart }) => {
            this.cart = cart;
        });
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
        if (this.cart.id !== undefined) {
            this.subscribeToSaveResponse(this.cartService.update(this.cart));
        } else {
            this.subscribeToSaveResponse(this.cartService.create(this.cart));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>) {
        result.subscribe((res: HttpResponse<ICart>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }
    get cart() {
        return this._cart;
    }

    set cart(cart: ICart) {
        this._cart = cart;
    }
}
