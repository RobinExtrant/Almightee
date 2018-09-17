import { Component, OnInit } from '@angular/core';
import { CommandItem } from 'app/shared/model/command-item.model';
import { CartService } from 'app/cart/cart.service';

@Component({
    selector: 'jhi-cart-list',
    templateUrl: './cart-list.component.html',
    styles: []
})
export class CartListComponent implements OnInit {
    items: CommandItem[];

    constructor(private cartService: CartService) {}

    ngOnInit() {
        this.items = this.cartService.all();
    }
}
