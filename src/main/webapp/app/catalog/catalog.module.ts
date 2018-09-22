import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CATALOG_ROUTE } from './catalog.route';
import { CatalogComponent } from './catalog.component';
import { ItemEditComponent } from './item-edit/item-edit.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PatternItemComponent } from './pattern-item/pattern-item.component';

@NgModule({
    imports: [FormsModule, CommonModule, RouterModule.forChild([CATALOG_ROUTE]), MatGridListModule, NgbModule],
    declarations: [CatalogComponent, ItemEditComponent, PatternItemComponent],
    entryComponents: [ItemEditComponent]
})
export class CatalogModule {}
