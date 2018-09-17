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
