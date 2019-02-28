import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Poz } from 'app/shared/model/poz.model';
import { PozService } from './poz.service';
import { PozComponent } from './poz.component';
import { PozDetailComponent } from './poz-detail.component';
import { PozUpdateComponent } from './poz-update.component';
import { PozDeletePopupComponent } from './poz-delete-dialog.component';
import { IPoz } from 'app/shared/model/poz.model';

@Injectable({ providedIn: 'root' })
export class PozResolve implements Resolve<IPoz> {
    constructor(private service: PozService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPoz> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Poz>) => response.ok),
                map((poz: HttpResponse<Poz>) => poz.body)
            );
        }
        return of(new Poz());
    }
}

export const pozRoute: Routes = [
    {
        path: '',
        component: PozComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.poz.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PozDetailComponent,
        resolve: {
            poz: PozResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.poz.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PozUpdateComponent,
        resolve: {
            poz: PozResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.poz.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PozUpdateComponent,
        resolve: {
            poz: PozResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.poz.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pozPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PozDeletePopupComponent,
        resolve: {
            poz: PozResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.poz.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
