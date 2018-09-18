import { Injectable } from '@angular/core';
import { Command } from './../shared/model/command.model';
import { CommandItem } from './../shared/model/command-item.model';

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private cart: Command;

    static fillFromJSON<T>(json: string, objectToFill: T): T {
        const jsonObj = JSON.parse(json);
        for (const propName in jsonObj) {
            objectToFill[propName] = jsonObj[propName];
        }

        return objectToFill;
    }

    constructor() {
        this.cart = { carts: [] };
        const oldCarts: CommandItem[] = JSON.parse(localStorage.getItem('cart'));
        if (oldCarts) {
            /*for (const oldCart in oldCarts){
                this.cart.carts.push(CartService.fillFromJSON(oldCart, new CommandItem()));
            }*/
            this.cart.carts = oldCarts;
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
            commandItem.setQuantity(commandItemIfExists.quantity + commandItem.quantity);
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
