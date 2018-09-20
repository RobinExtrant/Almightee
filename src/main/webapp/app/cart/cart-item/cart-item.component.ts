import { Component, Input } from '@angular/core';
import { CommandItem } from 'app/shared/model/command-item.model';

@Component({
    selector: 'jhi-cart-item',
    templateUrl: './cart-item.component.html',
    styles: []
})
export class CartItemComponent {
    @Input() public item: CommandItem;

    constructor() {}
}
