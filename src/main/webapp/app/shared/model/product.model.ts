import { ICategory } from 'app/shared/model//category.model';
import { IPattern } from 'app/shared/model//pattern.model';

export const enum Color {
    WHITE = 'WHITE',
    BLACK = 'BLACK',
    BLUE = 'BLUE',
    YELLOW = 'YELLOW',
    GREEN = 'GREEN',
    RED = 'RED'
}

export const enum Size {
    S = 'S',
    M = 'M',
    L = 'L',
    XL = 'XL',
    XXL = 'XXL'
}

export interface IProduct {
    id?: number;
    name?: string;
    price?: number;
    color?: Color;
    size?: Size;
    category?: ICategory;
    pattern?: IPattern;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public name?: string,
        public price?: number,
        public color?: Color,
        public size?: Size,
        public category?: ICategory,
        public pattern?: IPattern
    ) {}
}
