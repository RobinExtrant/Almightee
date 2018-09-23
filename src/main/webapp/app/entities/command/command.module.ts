import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlmighteeSharedModule } from 'app/shared';
import { AlmighteeAdminModule } from 'app/admin/admin.module';
import {
    CommandComponent,
    CommandDetailComponent,
    CommandUpdateComponent,
    CommandDeletePopupComponent,
    CommandDeleteDialogComponent,
    commandRoute,
    commandPopupRoute
} from './';

const ENTITY_STATES = [...commandRoute, ...commandPopupRoute];

@NgModule({
    imports: [AlmighteeSharedModule, AlmighteeAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommandComponent,
        CommandDetailComponent,
        CommandUpdateComponent,
        CommandDeleteDialogComponent,
        CommandDeletePopupComponent
    ],
    entryComponents: [CommandComponent, CommandUpdateComponent, CommandDeleteDialogComponent, CommandDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlmighteeCommandModule {}
