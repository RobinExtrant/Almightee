import { Injectable } from '@angular/core';
import { Command } from './../shared/model/command.model';
import { CommandItem } from './../shared/model/command-item.model';

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private cart: Command;

    constructor() {
        this.cart = { carts: [] };
    }

    all(): CommandItem[] {
        return this.cart.carts;
    }

    add(commandItem: CommandItem): boolean {
        let commandItemIfExists: CommandItem = this.cart.carts.find(
            x => x.color == commandItem.color && x.size == commandItem.size && x.pattern == commandItem.pattern
        );
        if (commandItemIfExists) {
            commandItemIfExists.setQuantity(commandItemIfExists.quantity + commandItem.quantity);
        } else {
            this.cart.carts.push(commandItem);
        }

        return true;
    }

    remove(commandItemIndex: number): boolean {
        return this.cart.carts.splice(commandItemIndex, 1).length == 1;
    }

    order() {}
}
