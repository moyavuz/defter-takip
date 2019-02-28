import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';
import { EskalasyonTuruService } from './eskalasyon-turu.service';
import { EskalasyonTuruComponent } from './eskalasyon-turu.component';
import { EskalasyonTuruDetailComponent } from './eskalasyon-turu-detail.component';
import { EskalasyonTuruUpdateComponent } from './eskalasyon-turu-update.component';
import { EskalasyonTuruDeletePopupComponent } from './eskalasyon-turu-delete-dialog.component';
import { IEskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';

@Injectable({ providedIn: 'root' })
export class EskalasyonTuruResolve implements Resolve<IEskalasyonTuru> {
    constructor(private service: EskalasyonTuruService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEskalasyonTuru> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EskalasyonTuru>) => response.ok),
                map((eskalasyonTuru: HttpResponse<EskalasyonTuru>) => eskalasyonTuru.body)
            );
        }
        return of(new EskalasyonTuru());
    }
}

export const eskalasyonTuruRoute: Routes = [
    {
        path: '',
        component: EskalasyonTuruComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.eskalasyonTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EskalasyonTuruDetailComponent,
        resolve: {
            eskalasyonTuru: EskalasyonTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.eskalasyonTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EskalasyonTuruUpdateComponent,
        resolve: {
            eskalasyonTuru: EskalasyonTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.eskalasyonTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EskalasyonTuruUpdateComponent,
        resolve: {
            eskalasyonTuru: EskalasyonTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.eskalasyonTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eskalasyonTuruPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EskalasyonTuruDeletePopupComponent,
        resolve: {
            eskalasyonTuru: EskalasyonTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.eskalasyonTuru.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
