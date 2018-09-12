import { ILineItem } from 'app/shared/model//line-item.model';
import { ICustomer } from 'app/shared/model//customer.model';

export interface ICart {
    id?: number;
    lineItems?: ILineItem[];
    customer?: ICustomer;
}

export class Cart implements ICart {
    constructor(public id?: number, public lineItems?: ILineItem[], public customer?: ICustomer) {}
}
