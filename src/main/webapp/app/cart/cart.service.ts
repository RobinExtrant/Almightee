import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Command, CommandStatus } from './../shared/model/command.model';
import { CommandItem } from './../shared/model/command-item.model';
import { CommandService } from '../entities/command/command.service';
import { Principal } from '../core/auth/principal.service';
import { Pattern } from 'app/shared/model/pattern.model';
import * as moment from 'moment';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private cart: Command;
    private patternSelected: Pattern;
    private patternAddedToCart: Pattern;
    private popupToClose: NgbModalRef;

    constructor(private commandService: CommandService, private principal: Principal, private router: Router) {
        this.cart = { carts: [] };
        this.patternAddedToCart = null;
        this.patternSelected = null;
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
        return <CommandItem[]>this.cart.carts;
    }

    add(commandItem: CommandItem): boolean {
        let commandItemIfExists: CommandItem;
        commandItemIfExists = <CommandItem>this.cart.carts.find(
            x => x.color === commandItem.color && x.size === commandItem.size && x.pattern.id === commandItem.pattern.id
        );
        if (commandItemIfExists) {
            commandItemIfExists.setQuantity(commandItemIfExists.quantity + commandItem.quantity);
        } else {
            this.cart.carts.push(commandItem);
        }
        this.save();
        this.patternAddedToCart = this.patternSelected;
        this.patternSelected = null;
        this.router.navigate(['catalog/']);
        if (this.popupToClose) {
            this.popupToClose.close();
        }
        return true;
    }

    remove(commandItem: CommandItem): boolean {
        const commandItemIndex: number = this.cart.carts.findIndex(
            x => x.color === commandItem.color && x.size === commandItem.size && x.pattern.id === commandItem.pattern.id
        );
        const itemRemoved = this.cart.carts.splice(commandItemIndex, 1).length === 1;
        this.save();
        return itemRemoved;
    }

    save() {
        localStorage.setItem('cart', JSON.stringify(this.cart.carts));
    }

    clear() {
        localStorage.clear();
        this.cart.carts.length = 0;
    }

    order() {
        if (this.cart.carts.length !== 0) {
            if (this.principal.isAuthenticated()) {
                this.principal.identity().then(user => (this.cart.user = user));
                this.cart.date = moment();
                this.cart.status = CommandStatus.IN_CART;
                this.cart.total = this.total();
                this.commandService.create(this.cart).subscribe(commandRes => this.router.navigate(['/cart', commandRes.body.id]));
            }
        }
    }

    total(): number {
        let total = 0;
        for (const item of this.cart.carts) {
            total += item.price;
        }
        return total;
    }

    setPatternSelected(patternSelected: Pattern) {
        this.patternSelected = patternSelected;
    }

    hasPatternSelected(): boolean {
        return this.patternSelected != null;
    }

    getPatternSelected(): Pattern {
        return this.patternSelected;
    }

    setPopupToClose(popupToClose: NgbModalRef) {
        this.popupToClose = popupToClose;
    }

    hasPatternAddedToCart(): boolean {
        return this.patternAddedToCart != null;
    }

    resetPatternAddedToCart(): boolean {
        return (this.patternAddedToCart = null);
    }

    getPatternAddedToCart(): Pattern {
        return this.patternAddedToCart;
    }
}
