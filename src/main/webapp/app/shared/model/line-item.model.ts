import { IProduct } from 'app/shared/model//product.model';
import { ICart } from 'app/shared/model//cart.model';

export interface ILineItem {
    id?: number;
    quantity?: number;
    product?: IProduct;
    cart?: ICart;
}

export class LineItem implements ILineItem {
    constructor(public id?: number, public quantity?: number, public product?: IProduct, public cart?: ICart) {}
}
