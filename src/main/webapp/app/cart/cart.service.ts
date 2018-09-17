import { Injectable } from '@angular/core';
import { ICommand } from './../shared/model/command.model';
import { ICommandItem, CommandItem } from './../shared/model/command-item.model';

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private cart: ICommand;

    constructor() {
        this.cart = { carts: [] };
    }

    add(commandItem: CommandItem) {
        let commandItemIfExists = this.cart.carts.find();
        if (commandItemIfExists) {
            this.cart.carts.some;
        } else {
            this.cart.carts.push(commandItem);
        }
    }

    remove(commandItemId: number) {}

    order() {}
}
