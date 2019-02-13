import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bolge } from 'app/shared/model/bolge.model';
import { BolgeService } from './bolge.service';
import { BolgeComponent } from './bolge.component';
import { BolgeDetailComponent } from './bolge-detail.component';
import { BolgeUpdateComponent } from './bolge-update.component';
import { BolgeDeletePopupComponent } from './bolge-delete-dialog.component';
import { IBolge } from 'app/shared/model/bolge.model';

@Injectable({ providedIn: 'root' })
export class BolgeResolve implements Resolve<IBolge> {
    constructor(private service: BolgeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBolge> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Bolge>) => response.ok),
                map((bolge: HttpResponse<Bolge>) => bolge.body)
            );
        }
        return of(new Bolge());
    }
}

export const bolgeRoute: Routes = [
    {
        path: '',
        component: BolgeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BolgeDetailComponent,
        resolve: {
            bolge: BolgeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BolgeUpdateComponent,
        resolve: {
            bolge: BolgeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BolgeUpdateComponent,
        resolve: {
            bolge: BolgeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bolgePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BolgeDeletePopupComponent,
        resolve: {
            bolge: BolgeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.bolge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
