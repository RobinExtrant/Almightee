import { IPattern } from 'app/shared/model//pattern.model';
import { ICommand } from 'app/shared/model//command.model';

export enum Color {
    WHITE = 'WHITE',
    BLACK = 'BLACK',
    BLUE = 'BLUE',
    YELLOW = 'YELLOW',
    GREEN = 'GREEN',
    RED = 'RED'
}

export enum Size {
    S = 'S',
    M = 'M',
    L = 'L',
    XL = 'XL',
    XXL = 'XXL'
}

export interface ICommandItem {
    id?: number;
    quantity?: number;
    price?: number;
    color?: Color;
    size?: Size;
    pattern?: IPattern;
    command?: ICommand;

    setQuantity(newQuantity: number): void;

    updatePrice(): void;
}

export class CommandItem implements ICommandItem {
    constructor(
        public id?: number,
        public quantity?: number,
        public price?: number,
        public color?: Color,
        public size?: Size,
        public pattern?: IPattern,
        public command?: ICommand
    ) {}

    setQuantity(newQuantity: number): void {
        this.quantity = newQuantity;
        this.updatePrice();
    }

    updatePrice() {
        this.price = this.quantity * this.pattern.price;
    }
}
