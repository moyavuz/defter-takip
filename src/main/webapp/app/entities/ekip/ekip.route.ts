import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ekip } from 'app/shared/model/ekip.model';
import { EkipService } from './ekip.service';
import { EkipComponent } from './ekip.component';
import { EkipDetailComponent } from './ekip-detail.component';
import { EkipUpdateComponent } from './ekip-update.component';
import { EkipDeletePopupComponent } from './ekip-delete-dialog.component';
import { IEkip } from 'app/shared/model/ekip.model';

@Injectable({ providedIn: 'root' })
export class EkipResolve implements Resolve<IEkip> {
    constructor(private service: EkipService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEkip> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Ekip>) => response.ok),
                map((ekip: HttpResponse<Ekip>) => ekip.body)
            );
        }
        return of(new Ekip());
    }
}

export const ekipRoute: Routes = [
    {
        path: '',
        component: EkipComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.ekip.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EkipDetailComponent,
        resolve: {
            ekip: EkipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.ekip.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EkipUpdateComponent,
        resolve: {
            ekip: EkipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.ekip.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EkipUpdateComponent,
        resolve: {
            ekip: EkipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.ekip.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ekipPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EkipDeletePopupComponent,
        resolve: {
            ekip: EkipResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.ekip.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
