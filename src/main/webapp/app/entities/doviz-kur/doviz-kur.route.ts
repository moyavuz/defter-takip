import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DovizKur } from 'app/shared/model/doviz-kur.model';
import { DovizKurService } from './doviz-kur.service';
import { DovizKurComponent } from './doviz-kur.component';
import { DovizKurDetailComponent } from './doviz-kur-detail.component';
import { DovizKurUpdateComponent } from './doviz-kur-update.component';
import { DovizKurDeletePopupComponent } from './doviz-kur-delete-dialog.component';
import { IDovizKur } from 'app/shared/model/doviz-kur.model';

@Injectable({ providedIn: 'root' })
export class DovizKurResolve implements Resolve<IDovizKur> {
    constructor(private service: DovizKurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDovizKur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DovizKur>) => response.ok),
                map((dovizKur: HttpResponse<DovizKur>) => dovizKur.body)
            );
        }
        return of(new DovizKur());
    }
}

export const dovizKurRoute: Routes = [
    {
        path: '',
        component: DovizKurComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.dovizKur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DovizKurDetailComponent,
        resolve: {
            dovizKur: DovizKurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.dovizKur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DovizKurUpdateComponent,
        resolve: {
            dovizKur: DovizKurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.dovizKur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DovizKurUpdateComponent,
        resolve: {
            dovizKur: DovizKurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.dovizKur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dovizKurPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DovizKurDeletePopupComponent,
        resolve: {
            dovizKur: DovizKurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.dovizKur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
