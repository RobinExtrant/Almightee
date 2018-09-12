export interface ICategory {
    id?: number;
    name?: string;
    price?: number;
}

export class Category implements ICategory {
    constructor(public id?: number, public name?: string, public price?: number) {}
}
