import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlmighteeSharedModule } from 'app/shared';
import {
    CommandItemComponent,
    CommandItemDetailComponent,
    CommandItemUpdateComponent,
    CommandItemDeletePopupComponent,
    CommandItemDeleteDialogComponent,
    commandItemRoute,
    commandItemPopupRoute
} from './';

const ENTITY_STATES = [...commandItemRoute, ...commandItemPopupRoute];

@NgModule({
    imports: [AlmighteeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommandItemComponent,
        CommandItemDetailComponent,
        CommandItemUpdateComponent,
        CommandItemDeleteDialogComponent,
        CommandItemDeletePopupComponent
    ],
    entryComponents: [CommandItemComponent, CommandItemUpdateComponent, CommandItemDeleteDialogComponent, CommandItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlmighteeCommandItemModule {}
