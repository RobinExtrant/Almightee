import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CommandItem } from 'app/shared/model/command-item.model';
import { CommandItemService } from './command-item.service';
import { CommandItemComponent } from './command-item.component';
import { CommandItemDetailComponent } from './command-item-detail.component';
import { CommandItemUpdateComponent } from './command-item-update.component';
import { CommandItemDeletePopupComponent } from './command-item-delete-dialog.component';
import { ICommandItem } from 'app/shared/model/command-item.model';

@Injectable({ providedIn: 'root' })
export class CommandItemResolve implements Resolve<ICommandItem> {
    constructor(private service: CommandItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((commandItem: HttpResponse<CommandItem>) => commandItem.body));
        }
        return of(new CommandItem());
    }
}

export const commandItemRoute: Routes = [
    {
        path: 'command-item',
        component: CommandItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommandItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'command-item/:id/view',
        component: CommandItemDetailComponent,
        resolve: {
            commandItem: CommandItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommandItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'command-item/new',
        component: CommandItemUpdateComponent,
        resolve: {
            commandItem: CommandItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommandItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'command-item/:id/edit',
        component: CommandItemUpdateComponent,
        resolve: {
            commandItem: CommandItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommandItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commandItemPopupRoute: Routes = [
    {
        path: 'command-item/:id/delete',
        component: CommandItemDeletePopupComponent,
        resolve: {
            commandItem: CommandItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommandItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
