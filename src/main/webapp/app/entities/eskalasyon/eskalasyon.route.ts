import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Eskalasyon } from 'app/shared/model/eskalasyon.model';
import { EskalasyonService } from './eskalasyon.service';
import { EskalasyonComponent } from './eskalasyon.component';
import { EskalasyonDetailComponent } from './eskalasyon-detail.component';
import { EskalasyonUpdateComponent } from './eskalasyon-update.component';
import { EskalasyonDeletePopupComponent } from './eskalasyon-delete-dialog.component';
import { IEskalasyon } from 'app/shared/model/eskalasyon.model';

@Injectable({ providedIn: 'root' })
export class EskalasyonResolve implements Resolve<IEskalasyon> {
    constructor(private service: EskalasyonService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEskalasyon> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Eskalasyon>) => response.ok),
                map((eskalasyon: HttpResponse<Eskalasyon>) => eskalasyon.body)
            );
        }
        return of(new Eskalasyon());
    }
}

export const eskalasyonRoute: Routes = [
    {
        path: '',
        component: EskalasyonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.eskalasyon.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EskalasyonDetailComponent,
        resolve: {
            eskalasyon: EskalasyonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.eskalasyon.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EskalasyonUpdateComponent,
        resolve: {
            eskalasyon: EskalasyonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.eskalasyon.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EskalasyonUpdateComponent,
        resolve: {
            eskalasyon: EskalasyonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.eskalasyon.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eskalasyonPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EskalasyonDeletePopupComponent,
        resolve: {
            eskalasyon: EskalasyonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.eskalasyon.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
