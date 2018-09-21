import { Route } from '@angular/router';
import { CatalogComponent } from './catalog.component';
import { ItemEditComponent } from './item-edit/item-edit.component';
import { PatternComponent } from './../entities/pattern/pattern.component';

export const CATALOG_ROUTE: Route = {
    path: 'catalog',
    component: CatalogComponent,
    data: {
        authorities: [],
        pageTitle: 'Catalogue'
    }
};
