import { Component, Input } from '@angular/core';
import { CommandItem } from 'app/shared/model/command-item.model';
import { CartService } from 'app/cart/cart.service';

@Component({
    selector: 'jhi-cart-item',
    templateUrl: './cart-item.component.html',
    styles: []
})
export class CartItemComponent {
    @Input() public item: CommandItem;

    constructor(private cartService: CartService) {}

    quantityChange(newValue) {
        this.item.updatePrice();
        this.cartService.save();
    }
}
