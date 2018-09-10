import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILineItem } from 'app/shared/model/line-item.model';
import { LineItemService } from './line-item.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { ICart } from 'app/shared/model/cart.model';
import { CartService } from 'app/entities/cart';

@Component({
    selector: 'jhi-line-item-update',
    templateUrl: './line-item-update.component.html'
})
export class LineItemUpdateComponent implements OnInit {
    private _lineItem: ILineItem;
    isSaving: boolean;

    products: IProduct[];

    carts: ICart[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private lineItemService: LineItemService,
        private productService: ProductService,
        private cartService: CartService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lineItem }) => {
            this.lineItem = lineItem;
        });
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cartService.query().subscribe(
            (res: HttpResponse<ICart[]>) => {
                this.carts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lineItem.id !== undefined) {
            this.subscribeToSaveResponse(this.lineItemService.update(this.lineItem));
        } else {
            this.subscribeToSaveResponse(this.lineItemService.create(this.lineItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILineItem>>) {
        result.subscribe((res: HttpResponse<ILineItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }

    trackCartById(index: number, item: ICart) {
        return item.id;
    }
    get lineItem() {
        return this._lineItem;
    }

    set lineItem(lineItem: ILineItem) {
        this._lineItem = lineItem;
    }
}
