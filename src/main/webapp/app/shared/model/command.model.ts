import { Moment } from 'moment';
import { ICommandItem } from 'app/shared/model//command-item.model';
import { ICustomer } from 'app/shared/model//customer.model';
import { IUser } from 'app/core/user/user.model';

export const enum CommandStatus {
    IN_CART = 'IN_CART',
    IN_PREPARATION = 'IN_PREPARATION',
    SHIPPED = 'SHIPPED',
    DELIVERED = 'DELIVERED',
    CANCELED = 'CANCELED'
}

export interface ICommand {
    id?: number;
    date?: Moment;
    status?: CommandStatus;
    total?: number;
    carts?: ICommandItem[];
    customer?: ICustomer;
    user?: IUser;
}

export class Command implements ICommand {
    constructor(
        public id?: number,
        public date?: Moment,
        public status?: CommandStatus,
        public total?: number,
        public carts?: ICommandItem[],
        public customer?: ICustomer,
        public user?: IUser
    ) {}
}
