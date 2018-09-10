import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AlmighteeCategoryModule } from './category/category.module';
import { AlmighteeProductModule } from './product/product.module';
import { AlmighteePatternModule } from './pattern/pattern.module';
import { AlmighteeLineItemModule } from './line-item/line-item.module';
import { AlmighteeCartModule } from './cart/cart.module';
import { AlmighteeCommandModule } from './command/command.module';
import { AlmighteeCustomerModule } from './customer/customer.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AlmighteeCategoryModule,
        AlmighteeProductModule,
        AlmighteePatternModule,
        AlmighteeLineItemModule,
        AlmighteeCartModule,
        AlmighteeCommandModule,
        AlmighteeCustomerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlmighteeEntityModule {}
