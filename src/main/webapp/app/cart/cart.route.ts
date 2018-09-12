import { Route } from '@angular/router';

import { CartComponent } from './cart.component';

export const cartRoute: Route = {
    path: 'cart',
    component: CartComponent,
    data: {
        authorities: [],
        pageTitle: 'Panier'
    }
};
