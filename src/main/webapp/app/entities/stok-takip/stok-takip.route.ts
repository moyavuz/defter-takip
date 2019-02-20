import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StokTakip } from 'app/shared/model/stok-takip.model';
import { StokTakipService } from './stok-takip.service';
import { StokTakipComponent } from './stok-takip.component';
import { StokTakipDetailComponent } from './stok-takip-detail.component';
import { StokTakipUpdateComponent } from './stok-takip-update.component';
import { StokTakipDeletePopupComponent } from './stok-takip-delete-dialog.component';
import { IStokTakip } from 'app/shared/model/stok-takip.model';

@Injectable({ providedIn: 'root' })
export class StokTakipResolve implements Resolve<IStokTakip> {
    constructor(private service: StokTakipService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStokTakip> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StokTakip>) => response.ok),
                map((stokTakip: HttpResponse<StokTakip>) => stokTakip.body)
            );
        }
        return of(new StokTakip());
    }
}

export const stokTakipRoute: Routes = [
    {
        path: '',
        component: StokTakipComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.stokTakip.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StokTakipDetailComponent,
        resolve: {
            stokTakip: StokTakipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.stokTakip.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StokTakipUpdateComponent,
        resolve: {
            stokTakip: StokTakipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.stokTakip.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StokTakipUpdateComponent,
        resolve: {
            stokTakip: StokTakipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.stokTakip.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stokTakipPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StokTakipDeletePopupComponent,
        resolve: {
            stokTakip: StokTakipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.stokTakip.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
