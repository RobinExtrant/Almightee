import { Component, OnInit } from '@angular/core';
import { CommandItem, Color, Size } from 'app/shared/model/command-item.model';
import { CartService } from 'app/cart/cart.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-item-edit',
    templateUrl: './item-edit.component.html',
    styleUrls: ['item-edit.scss']
})
export class ItemEditComponent implements OnInit {
    commandItem: CommandItem;
    colors = { values: Color, keys: Object.keys(Color) };
    colorItem: Color;
    sizes = { values: Size, keys: Object.keys(Size) };
    quantity: string;

    constructor(private modalService: NgbModal, private cartService: CartService) {}

    ngOnInit() {
        this.commandItem = new CommandItem();
        this.commandItem.color = Color.WHITE;
        this.commandItem.size = Size.L;
        this.commandItem.quantity = 1;
    }

    addToCart(): void {
        this.commandItem.pattern = this.cartService.getPatternSelected();
        this.commandItem.updatePrice();
        console.log('commandItem : ' + this.commandItem);
        this.cartService.add(this.commandItem);
    }

    onChange(newValue) {
        console.log(newValue);
    }
}
