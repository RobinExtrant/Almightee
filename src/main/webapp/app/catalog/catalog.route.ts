import { Route } from '@angular/router';
import { CatalogComponent } from './catalog.component';
import { PatternComponent } from './../entities/pattern/pattern.component';

export const CATALOG_ROUTE: Route = {
    path: 'catalog',
    component: CatalogComponent,
    data: {
        authorities: [],
        pageTitle: 'Catalogue'
    }
};
