import { Component, OnInit } from '@angular/core';
import { Color, Size } from 'app/shared/model/command-item.model';
import { Command } from 'app/shared/model/command.model';

@Component({
    selector: 'jhi-cart-list',
    templateUrl: './cart-list.component.html',
    styles: []
})
export class CartListComponent implements OnInit {
    items: Command;

    constructor() {}

    ngOnInit() {
        this.items = {
            carts: [
                { id: 0, quantity: 1, price: 12, color: Color.RED, size: Size.L, pattern: null, command: null },
                { id: 1, quantity: 3, price: 30, color: Color.BLACK, size: Size.M, pattern: null, command: null },
                { id: 2, quantity: 2, price: 24, color: Color.YELLOW, size: Size.S, pattern: null, command: null }
            ]
        };
    }
}
