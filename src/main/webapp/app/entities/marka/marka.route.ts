import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Marka } from 'app/shared/model/marka.model';
import { MarkaService } from './marka.service';
import { MarkaComponent } from './marka.component';
import { MarkaDetailComponent } from './marka-detail.component';
import { MarkaUpdateComponent } from './marka-update.component';
import { MarkaDeletePopupComponent } from './marka-delete-dialog.component';
import { IMarka } from 'app/shared/model/marka.model';

@Injectable({ providedIn: 'root' })
export class MarkaResolve implements Resolve<IMarka> {
    constructor(private service: MarkaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMarka> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Marka>) => response.ok),
                map((marka: HttpResponse<Marka>) => marka.body)
            );
        }
        return of(new Marka());
    }
}

export const markaRoute: Routes = [
    {
        path: '',
        component: MarkaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.marka.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MarkaDetailComponent,
        resolve: {
            marka: MarkaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.marka.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MarkaUpdateComponent,
        resolve: {
            marka: MarkaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.marka.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MarkaUpdateComponent,
        resolve: {
            marka: MarkaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.marka.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const markaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MarkaDeletePopupComponent,
        resolve: {
            marka: MarkaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.marka.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
