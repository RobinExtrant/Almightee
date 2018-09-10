import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LineItem } from 'app/shared/model/line-item.model';
import { LineItemService } from './line-item.service';
import { LineItemComponent } from './line-item.component';
import { LineItemDetailComponent } from './line-item-detail.component';
import { LineItemUpdateComponent } from './line-item-update.component';
import { LineItemDeletePopupComponent } from './line-item-delete-dialog.component';
import { ILineItem } from 'app/shared/model/line-item.model';

@Injectable({ providedIn: 'root' })
export class LineItemResolve implements Resolve<ILineItem> {
    constructor(private service: LineItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((lineItem: HttpResponse<LineItem>) => lineItem.body));
        }
        return of(new LineItem());
    }
}

export const lineItemRoute: Routes = [
    {
        path: 'line-item',
        component: LineItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LineItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'line-item/:id/view',
        component: LineItemDetailComponent,
        resolve: {
            lineItem: LineItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LineItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'line-item/new',
        component: LineItemUpdateComponent,
        resolve: {
            lineItem: LineItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LineItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'line-item/:id/edit',
        component: LineItemUpdateComponent,
        resolve: {
            lineItem: LineItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LineItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lineItemPopupRoute: Routes = [
    {
        path: 'line-item/:id/delete',
        component: LineItemDeletePopupComponent,
        resolve: {
            lineItem: LineItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LineItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
