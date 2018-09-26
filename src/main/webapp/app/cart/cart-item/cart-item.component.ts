import { Component, Input, Directive, ElementRef, OnInit, Renderer } from '@angular/core';
import { CommandItem } from 'app/shared/model/command-item.model';
import { CartService } from 'app/cart/cart.service';

@Component({
    selector: 'jhi-cart-item',
    templateUrl: './cart-item.component.html',
    styles: []
})
export class CartItemComponent {
    @Input() public item: CommandItem;
    private alreadyDeleted: boolean;

    constructor(public cartService: CartService) {
        this.alreadyDeleted = false;
    }

    quantityChange(newValue) {
        if (!this.alreadyDeleted) {
            if (this.item.quantity === 0) {
                this.cartService.remove(this.item);
                this.alreadyDeleted = true;
            } else {
                this.item.updatePrice();
            }
            this.cartService.updateTotalPrice();
            this.cartService.save();
        }
    }

    quantityFocusedOut() {
        if (this.item.quantity == null || this.item.quantity <= 0) {
            this.item.quantity = 1;
        }
        this.quantityChange(1);
    }
}
