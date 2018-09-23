import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AlmighteePatternModule } from './pattern/pattern.module';
import { AlmighteeCommandItemModule } from './command-item/command-item.module';
import { AlmighteeCommandModule } from './command/command.module';
import { AlmighteeCustomerModule } from './customer/customer.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AlmighteePatternModule,
        AlmighteeCommandItemModule,
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
