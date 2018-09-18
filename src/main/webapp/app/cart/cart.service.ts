import { Injectable } from '@angular/core';
import { Command } from './../shared/model/command.model';
import { CommandItem } from './../shared/model/command-item.model';
import { CommandService } from 'app/entities/command';

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private cart: Command;

    constructor(private commandService: CommandService) {
        this.cart = { carts: [] };
    }

    all(): CommandItem[] {
        return JSON.parse(localStorage.getItem('cart'));
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
            this.cart.carts.push(commandItem);
        }
        localStorage.setItem('cart', JSON.stringify(this.cart.carts));
        return true;
    }

    remove(commandItemIndex: number): boolean {
        return this.cart.carts.splice(commandItemIndex, 1).length === 1;
    }

    order() {
        this.commandService.create(this.cart).subscribe(orderRes => console.log(orderRes));
    }
}
