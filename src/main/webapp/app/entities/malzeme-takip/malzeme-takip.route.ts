import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MalzemeTakip } from 'app/shared/model/malzeme-takip.model';
import { MalzemeTakipService } from './malzeme-takip.service';
import { MalzemeTakipComponent } from './malzeme-takip.component';
import { MalzemeTakipDetailComponent } from './malzeme-takip-detail.component';
import { MalzemeTakipUpdateComponent } from './malzeme-takip-update.component';
import { MalzemeTakipDeletePopupComponent } from './malzeme-takip-delete-dialog.component';
import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';

@Injectable({ providedIn: 'root' })
export class MalzemeTakipResolve implements Resolve<IMalzemeTakip> {
    constructor(private service: MalzemeTakipService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMalzemeTakip> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MalzemeTakip>) => response.ok),
                map((malzemeTakip: HttpResponse<MalzemeTakip>) => malzemeTakip.body)
            );
        }
        return of(new MalzemeTakip());
    }
}

export const malzemeTakipRoute: Routes = [
    {
        path: '',
        component: MalzemeTakipComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.malzemeTakip.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MalzemeTakipDetailComponent,
        resolve: {
            malzemeTakip: MalzemeTakipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzemeTakip.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MalzemeTakipUpdateComponent,
        resolve: {
            malzemeTakip: MalzemeTakipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzemeTakip.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MalzemeTakipUpdateComponent,
        resolve: {
            malzemeTakip: MalzemeTakipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzemeTakip.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const malzemeTakipPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MalzemeTakipDeletePopupComponent,
        resolve: {
            malzemeTakip: MalzemeTakipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzemeTakip.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
