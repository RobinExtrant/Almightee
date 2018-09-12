import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlmighteeSharedModule } from 'app/shared';
import {
    LineItemComponent,
    LineItemDetailComponent,
    LineItemUpdateComponent,
    LineItemDeletePopupComponent,
    LineItemDeleteDialogComponent,
    lineItemRoute,
    lineItemPopupRoute
} from './';

const ENTITY_STATES = [...lineItemRoute, ...lineItemPopupRoute];

@NgModule({
    imports: [AlmighteeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LineItemComponent,
        LineItemDetailComponent,
        LineItemUpdateComponent,
        LineItemDeleteDialogComponent,
        LineItemDeletePopupComponent
    ],
    entryComponents: [LineItemComponent, LineItemUpdateComponent, LineItemDeleteDialogComponent, LineItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlmighteeLineItemModule {}
