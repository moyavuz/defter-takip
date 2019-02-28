import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Depo } from 'app/shared/model/depo.model';
import { DepoService } from './depo.service';
import { DepoComponent } from './depo.component';
import { DepoDetailComponent } from './depo-detail.component';
import { DepoUpdateComponent } from './depo-update.component';
import { DepoDeletePopupComponent } from './depo-delete-dialog.component';
import { IDepo } from 'app/shared/model/depo.model';

@Injectable({ providedIn: 'root' })
export class DepoResolve implements Resolve<IDepo> {
    constructor(private service: DepoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Depo>) => response.ok),
                map((depo: HttpResponse<Depo>) => depo.body)
            );
        }
        return of(new Depo());
    }
}

export const depoRoute: Routes = [
    {
        path: '',
        component: DepoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.depo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DepoDetailComponent,
        resolve: {
            depo: DepoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.depo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepoUpdateComponent,
        resolve: {
            depo: DepoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.depo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepoUpdateComponent,
        resolve: {
            depo: DepoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.depo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepoDeletePopupComponent,
        resolve: {
            depo: DepoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.depo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
