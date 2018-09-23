import { Routes } from '@angular/router';

import { cartListRoute } from './cart-list/cart-list.route';
import { cartConfirmRoute } from './cart-confirm/cart-confirm.route';

const CART_ROUTES = [cartListRoute, cartConfirmRoute];

export const cartRoute: Routes = [
    {
        path: '',
        children: CART_ROUTES
    }
];
