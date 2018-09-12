import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlmighteeSharedModule } from 'app/shared';
import { cartRoute } from './cart.route';
import { CartComponent } from './cart.component';

@NgModule({
    imports: [AlmighteeSharedModule, RouterModule.forChild([cartRoute])],
    declarations: [CartComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlmighteeCartModule {}
