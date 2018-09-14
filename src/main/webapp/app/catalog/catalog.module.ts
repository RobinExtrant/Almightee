import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CATALOG_ROUTE } from './catalog.route';
import { CatalogComponent } from './catalog.component';
import { MatGridListModule } from '@angular/material/grid-list';

@NgModule({
    imports: [CommonModule, RouterModule.forChild([CATALOG_ROUTE]), MatGridListModule],
    declarations: [CatalogComponent]
})
export class CatalogModule {}
