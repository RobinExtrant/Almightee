import { ICommand } from 'app/shared/model//command.model';

export interface ICustomer {
    id?: number;
    username?: string;
    mail?: string;
    password?: string;
    commands?: ICommand[];
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public username?: string,
        public mail?: string,
        public password?: string,
        public commands?: ICommand[]
    ) {}
}
