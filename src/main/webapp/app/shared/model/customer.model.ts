import { ICart } from 'app/shared/model//cart.model';
import { ICommand } from 'app/shared/model//command.model';

export interface ICustomer {
    id?: number;
    username?: string;
    mail?: string;
    password?: string;
    currentCart?: ICart;
    commands?: ICommand[];
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public username?: string,
        public mail?: string,
        public password?: string,
        public currentCart?: ICart,
        public commands?: ICommand[]
    ) {}
}
