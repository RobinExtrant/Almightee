import { Routes } from '@angular/router';

import { cartListRoute } from './cart-list/cart-list.route';

const CART_ROUTES = [cartListRoute];

export const cartRoute: Routes = [
    {
        path: '',
        children: CART_ROUTES
    }
];
