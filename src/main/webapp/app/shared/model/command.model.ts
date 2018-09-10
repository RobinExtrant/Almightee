import { Moment } from 'moment';
import { ICart } from 'app/shared/model//cart.model';
import { ICustomer } from 'app/shared/model//customer.model';

export const enum CommandStatus {
    IN_PREPARATION = 'IN_PREPARATION',
    SHIPPED = 'SHIPPED',
    DELIVERED = 'DELIVERED',
    CANCELED = 'CANCELED'
}

export interface ICommand {
    id?: number;
    date?: Moment;
    status?: CommandStatus;
    cart?: ICart;
    customer?: ICustomer;
}

export class Command implements ICommand {
    constructor(
        public id?: number,
        public date?: Moment,
        public status?: CommandStatus,
        public cart?: ICart,
        public customer?: ICustomer
    ) {}
}
