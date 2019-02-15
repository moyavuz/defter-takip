import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Calisan } from 'app/shared/model/calisan.model';
import { CalisanService } from './calisan.service';
import { CalisanComponent } from './calisan.component';
import { CalisanDetailComponent } from './calisan-detail.component';
import { CalisanUpdateComponent } from './calisan-update.component';
import { CalisanDeletePopupComponent } from './calisan-delete-dialog.component';
import { ICalisan } from 'app/shared/model/calisan.model';

@Injectable({ providedIn: 'root' })
export class CalisanResolve implements Resolve<ICalisan> {
    constructor(private service: CalisanService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICalisan> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Calisan>) => response.ok),
                map((calisan: HttpResponse<Calisan>) => calisan.body)
            );
        }
        return of(new Calisan());
    }
}

export const calisanRoute: Routes = [
    {
        path: '',
        component: CalisanComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.calisan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CalisanDetailComponent,
        resolve: {
            calisan: CalisanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.calisan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CalisanUpdateComponent,
        resolve: {
            calisan: CalisanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.calisan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CalisanUpdateComponent,
        resolve: {
            calisan: CalisanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.calisan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const calisanPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CalisanDeletePopupComponent,
        resolve: {
            calisan: CalisanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.calisan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
