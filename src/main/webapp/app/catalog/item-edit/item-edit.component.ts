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
    sizes = { values: Size, keys: Object.keys(Size) };

    constructor(private modalService: NgbModal, private cartService: CartService) {}

    ngOnInit() {
        this.commandItem = new CommandItem();
        this.commandItem.color = Color.WHITE;
        this.commandItem.size = Size.L;
        this.commandItem.quantity = 1;
        this.commandItem.pattern = this.cartService.getPatternSelected();
        this.commandItem.updatePrice();
    }

    addToCart(): void {
        this.commandItem.updatePrice();
        this.cartService.add(this.commandItem);
    }

    quantityFocusedOut() {
        if (this.commandItem.quantity == null || this.commandItem.quantity <= 0) {
            this.commandItem.setQuantity(1);
        }
    }
}
