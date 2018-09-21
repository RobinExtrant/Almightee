import { Route } from '@angular/router';

import { CartListComponent } from 'app/cart/cart-list/cart-list.component';

export const cartListRoute: Route = {
    path: 'cart',
    component: CartListComponent,
    data: {
        authorities: [],
        pageTitle: 'Panier'
    }
};
