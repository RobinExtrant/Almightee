import { Component, OnInit } from '@angular/core';
import { CommandItem } from 'app/shared/model/command-item.model';
import { CartService } from 'app/cart/cart.service';
import { Principal } from '../../core/auth/principal.service';

@Component({
    selector: 'jhi-cart-list',
    templateUrl: './cart-list.component.html',
    styles: []
})
export class CartListComponent implements OnInit {
    items: CommandItem[];

    constructor(public cartService: CartService, public principal: Principal) {}

    ngOnInit() {
        this.items = this.cartService.all();
    }

    order() {
        this.cartService.order();
    }

    clear() {
        this.cartService.clear();
        this.items.length = 0;
    }
}
