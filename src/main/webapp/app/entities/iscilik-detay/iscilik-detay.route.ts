import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IscilikDetay } from 'app/shared/model/iscilik-detay.model';
import { IscilikDetayService } from './iscilik-detay.service';
import { IscilikDetayComponent } from './iscilik-detay.component';
import { IscilikDetayDetailComponent } from './iscilik-detay-detail.component';
import { IscilikDetayUpdateComponent } from './iscilik-detay-update.component';
import { IscilikDetayDeletePopupComponent } from './iscilik-detay-delete-dialog.component';
import { IIscilikDetay } from 'app/shared/model/iscilik-detay.model';

@Injectable({ providedIn: 'root' })
export class IscilikDetayResolve implements Resolve<IIscilikDetay> {
    constructor(private service: IscilikDetayService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIscilikDetay> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IscilikDetay>) => response.ok),
                map((iscilikDetay: HttpResponse<IscilikDetay>) => iscilikDetay.body)
            );
        }
        return of(new IscilikDetay());
    }
}

export const iscilikDetayRoute: Routes = [
    {
        path: '',
        component: IscilikDetayComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilikDetay.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IscilikDetayDetailComponent,
        resolve: {
            iscilikDetay: IscilikDetayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilikDetay.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IscilikDetayUpdateComponent,
        resolve: {
            iscilikDetay: IscilikDetayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilikDetay.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IscilikDetayUpdateComponent,
        resolve: {
            iscilikDetay: IscilikDetayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilikDetay.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iscilikDetayPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IscilikDetayDeletePopupComponent,
        resolve: {
            iscilikDetay: IscilikDetayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilikDetay.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
