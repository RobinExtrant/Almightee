import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlmighteeSharedModule } from 'app/shared';
import {
    PatternComponent,
    PatternDetailComponent,
    PatternUpdateComponent,
    PatternDeletePopupComponent,
    PatternDeleteDialogComponent,
    patternRoute,
    patternPopupRoute
} from './';

const ENTITY_STATES = [...patternRoute, ...patternPopupRoute];

@NgModule({
    imports: [AlmighteeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PatternComponent,
        PatternDetailComponent,
        PatternUpdateComponent,
        PatternDeleteDialogComponent,
        PatternDeletePopupComponent
    ],
    entryComponents: [PatternComponent, PatternUpdateComponent, PatternDeleteDialogComponent, PatternDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlmighteePatternModule {}
