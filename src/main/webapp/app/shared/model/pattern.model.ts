export const enum Theme {
    NATURE = 'NATURE',
    ANIME = 'ANIME',
    DESIGN = 'DESIGN',
    LOVE = 'LOVE',
    ANIMAL = 'ANIMAL'
}

export interface IPattern {
    id?: number;
    name?: string;
    author?: string;
    imageURL?: string;
    price?: number;
    theme?: Theme;
}

export class Pattern implements IPattern {
    constructor(
        public id?: number,
        public name?: string,
        public author?: string,
        public imageURL?: string,
        public price?: number,
        public theme?: Theme
    ) {}
}
