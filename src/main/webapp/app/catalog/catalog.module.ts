import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CATALOG_ROUTE } from './catalog.route';

@NgModule({
    imports: [CommonModule, RouterModule.forChild([CATALOG_ROUTE])],
    declarations: []
})
export class CatalogModule {}
