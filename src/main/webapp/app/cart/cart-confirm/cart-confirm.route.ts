import { Route } from '@angular/router';

import { CartConfirmComponent } from 'app/cart/cart-list/cart-confirm.component';

export const cartConfirmRoute: Route = {
    path: 'cart/:id',
    component: CartConfirmComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Confirmation de commande'
    }
};
