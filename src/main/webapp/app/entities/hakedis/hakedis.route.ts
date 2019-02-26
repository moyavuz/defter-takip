import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Hakedis } from 'app/shared/model/hakedis.model';
import { HakedisService } from './hakedis.service';
import { HakedisComponent } from './hakedis.component';
import { HakedisDetailComponent } from './hakedis-detail.component';
import { HakedisUpdateComponent } from './hakedis-update.component';
import { HakedisDeletePopupComponent } from './hakedis-delete-dialog.component';
import { IHakedis } from 'app/shared/model/hakedis.model';

@Injectable({ providedIn: 'root' })
export class HakedisResolve implements Resolve<IHakedis> {
    constructor(private service: HakedisService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHakedis> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Hakedis>) => response.ok),
                map((hakedis: HttpResponse<Hakedis>) => hakedis.body)
            );
        }
        return of(new Hakedis());
    }
}

export const hakedisRoute: Routes = [
    {
        path: '',
        component: HakedisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HakedisDetailComponent,
        resolve: {
            hakedis: HakedisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HakedisUpdateComponent,
        resolve: {
            hakedis: HakedisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HakedisUpdateComponent,
        resolve: {
            hakedis: HakedisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hakedisPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HakedisDeletePopupComponent,
        resolve: {
            hakedis: HakedisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
