import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Santral } from 'app/shared/model/santral.model';
import { SantralService } from './santral.service';
import { SantralComponent } from './santral.component';
import { SantralDetailComponent } from './santral-detail.component';
import { SantralUpdateComponent } from './santral-update.component';
import { SantralDeletePopupComponent } from './santral-delete-dialog.component';
import { ISantral } from 'app/shared/model/santral.model';

@Injectable({ providedIn: 'root' })
export class SantralResolve implements Resolve<ISantral> {
    constructor(private service: SantralService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISantral> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Santral>) => response.ok),
                map((santral: HttpResponse<Santral>) => santral.body)
            );
        }
        return of(new Santral());
    }
}

export const santralRoute: Routes = [
    {
        path: '',
        component: SantralComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.santral.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SantralDetailComponent,
        resolve: {
            santral: SantralResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.santral.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SantralUpdateComponent,
        resolve: {
            santral: SantralResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.santral.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SantralUpdateComponent,
        resolve: {
            santral: SantralResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.santral.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const santralPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SantralDeletePopupComponent,
        resolve: {
            santral: SantralResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.santral.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
