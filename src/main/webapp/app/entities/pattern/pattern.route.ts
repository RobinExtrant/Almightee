import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pattern } from 'app/shared/model/pattern.model';
import { PatternService } from './pattern.service';
import { PatternComponent } from './pattern.component';
import { PatternDetailComponent } from './pattern-detail.component';
import { PatternUpdateComponent } from './pattern-update.component';
import { PatternDeletePopupComponent } from './pattern-delete-dialog.component';
import { IPattern } from 'app/shared/model/pattern.model';

@Injectable({ providedIn: 'root' })
export class PatternResolve implements Resolve<IPattern> {
    constructor(private service: PatternService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pattern: HttpResponse<Pattern>) => pattern.body));
        }
        return of(new Pattern());
    }
}

export const patternRoute: Routes = [
    {
        path: 'pattern',
        component: PatternComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pattern/:id/view',
        component: PatternDetailComponent,
        resolve: {
            pattern: PatternResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pattern/new',
        component: PatternUpdateComponent,
        resolve: {
            pattern: PatternResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pattern/:id/edit',
        component: PatternUpdateComponent,
        resolve: {
            pattern: PatternResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const patternPopupRoute: Routes = [
    {
        path: 'pattern/:id/delete',
        component: PatternDeletePopupComponent,
        resolve: {
            pattern: PatternResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
