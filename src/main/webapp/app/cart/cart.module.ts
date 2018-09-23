import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlmighteeSharedModule } from 'app/shared';
import { cartRoute } from './cart.route';
import { CartListComponent } from 'app/cart/cart-list/cart-list.component';
import { CartItemComponent } from 'app/cart/cart-item/cart-item.component';
import { CartConfirmComponent } from 'app/cart/cart-confirm/cart-confirm.component';

@NgModule({
    imports: [AlmighteeSharedModule, RouterModule.forChild(cartRoute)],
    declarations: [CartListComponent, CartItemComponent, CartConfirmComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlmighteeCartModule {}
