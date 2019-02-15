import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Iscilik } from 'app/shared/model/iscilik.model';
import { IscilikService } from './iscilik.service';
import { IscilikComponent } from './iscilik.component';
import { IscilikDetailComponent } from './iscilik-detail.component';
import { IscilikUpdateComponent } from './iscilik-update.component';
import { IscilikDeletePopupComponent } from './iscilik-delete-dialog.component';
import { IIscilik } from 'app/shared/model/iscilik.model';

@Injectable({ providedIn: 'root' })
export class IscilikResolve implements Resolve<IIscilik> {
    constructor(private service: IscilikService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIscilik> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Iscilik>) => response.ok),
                map((iscilik: HttpResponse<Iscilik>) => iscilik.body)
            );
        }
        return of(new Iscilik());
    }
}

export const iscilikRoute: Routes = [
    {
        path: '',
        component: IscilikComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilik.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IscilikDetailComponent,
        resolve: {
            iscilik: IscilikResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilik.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IscilikUpdateComponent,
        resolve: {
            iscilik: IscilikResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilik.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IscilikUpdateComponent,
        resolve: {
            iscilik: IscilikResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilik.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iscilikPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IscilikDeletePopupComponent,
        resolve: {
            iscilik: IscilikResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.iscilik.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
