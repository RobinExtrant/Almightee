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

    constructor(private cartService: CartService, private principal: Principal) {}

    ngOnInit() {
        this.items = this.cartService.all();
    }

    order() {
        if (this.principal.isAuthenticated()) {
            this.cartService.order();
        } else {
            console.log('URDUR PAS CONNECTE');
        }
    }

    clear() {
        this.cartService.clear();
    }
}
