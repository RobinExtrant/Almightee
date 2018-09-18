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
        const oldCart = JSON.parse(localStorage.getItem('cart'));
        if (oldCart) {
            for (const commandItem of oldCart) {
                const newCommandItem: CommandItem = new CommandItem();
                Object.assign(newCommandItem, commandItem);
                this.cart.carts.push(newCommandItem);
            }
        }
    }

    all(): CommandItem[] {
        return this.cart.carts;
    }

    add(commandItem: CommandItem): boolean {
        let commandItemIfExists: CommandItem;
        commandItemIfExists = this.cart.carts.find(
            x => x.color === commandItem.color && x.size === commandItem.size && x.pattern.id === commandItem.pattern.id
        );
        if (commandItemIfExists) {
            commandItemIfExists.setQuantity(commandItemIfExists.quantity + commandItem.quantity);
        } else {
            this.cart.carts.push(commandItem);
        }
        localStorage.setItem('cart', JSON.stringify(this.cart.carts));
        return true;
    }

    remove(commandItemIndex: number): boolean {
        return this.cart.carts.splice(commandItemIndex, 1).length === 1;
    }

    order() {}
}
