import { Injectable } from '@angular/core';
import { Command } from './../shared/model/command.model';
import { CommandItem } from './../shared/model/command-item.model';
import { CommandService } from '../entities/command/command.service';
import { Principal } from '../../core/auth/principal.service';

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

    constructor(private commandService: CommandService, private principal: Principal) {
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

    clear() {
        localStorage.clear();
        this.cart.carts.length = 0;
    }

    order() {
        if (this.cart.carts.length != 0) {
            /*this.principal.identity().then(id => {
                this.cart.id = id;
            });
            console.log("ID user :" + this.cart.id);*/
            this.commandService.create(this.cart).subscribe(commandRes => console.log('Commande confirmée : ' + commandRes));
        }
    }
}
