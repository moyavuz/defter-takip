import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HakedisDetay } from 'app/shared/model/hakedis-detay.model';
import { HakedisDetayService } from './hakedis-detay.service';
import { HakedisDetayComponent } from './hakedis-detay.component';
import { HakedisDetayDetailComponent } from './hakedis-detay-detail.component';
import { HakedisDetayUpdateComponent } from './hakedis-detay-update.component';
import { HakedisDetayDeletePopupComponent } from './hakedis-detay-delete-dialog.component';
import { IHakedisDetay } from 'app/shared/model/hakedis-detay.model';

@Injectable({ providedIn: 'root' })
export class HakedisDetayResolve implements Resolve<IHakedisDetay> {
    constructor(private service: HakedisDetayService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHakedisDetay> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HakedisDetay>) => response.ok),
                map((hakedisDetay: HttpResponse<HakedisDetay>) => hakedisDetay.body)
            );
        }
        return of(new HakedisDetay());
    }
}

export const hakedisDetayRoute: Routes = [
    {
        path: '',
        component: HakedisDetayComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.hakedisDetay.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HakedisDetayDetailComponent,
        resolve: {
            hakedisDetay: HakedisDetayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedisDetay.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HakedisDetayUpdateComponent,
        resolve: {
            hakedisDetay: HakedisDetayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedisDetay.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HakedisDetayUpdateComponent,
        resolve: {
            hakedisDetay: HakedisDetayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedisDetay.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hakedisDetayPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HakedisDetayDeletePopupComponent,
        resolve: {
            hakedisDetay: HakedisDetayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedisDetay.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
