import { NgModule } from '@angular/core';

import { AlmighteeSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [AlmighteeSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [AlmighteeSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AlmighteeSharedCommonModule {}
