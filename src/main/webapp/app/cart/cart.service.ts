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
        console.log('nombre : ' + this.cart.carts.length);
        return this.cart.carts;
    }

    add(commandItem: CommandItem): boolean {
        let commandItemIfExists: CommandItem;
        commandItemIfExists = this.cart.carts.find(
            x =>
                x.color == commandItem.color &&
                x.size == commandItem.size &&
                JSON.stringify(x.pattern).toLowerCase() == JSON.stringify(commandItem.pattern).toLowerCase()
        );
        if (commandItemIfExists) {
            commandItemIfExists.setQuantity(commandItemIfExists.quantity + commandItem.quantity);
        } else {
            console.log('Motif push');
            this.cart.carts.push(commandItem);
        }

        return true;
    }

    remove(commandItemIndex: number): boolean {
        return this.cart.carts.splice(commandItemIndex, 1).length === 1;
    }

    order() {}
}
