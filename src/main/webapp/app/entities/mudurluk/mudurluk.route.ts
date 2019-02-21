import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Mudurluk } from 'app/shared/model/mudurluk.model';
import { MudurlukService } from './mudurluk.service';
import { MudurlukComponent } from './mudurluk.component';
import { MudurlukDetailComponent } from './mudurluk-detail.component';
import { MudurlukUpdateComponent } from './mudurluk-update.component';
import { MudurlukDeletePopupComponent } from './mudurluk-delete-dialog.component';
import { IMudurluk } from 'app/shared/model/mudurluk.model';

@Injectable({ providedIn: 'root' })
export class MudurlukResolve implements Resolve<IMudurluk> {
    constructor(private service: MudurlukService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMudurluk> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Mudurluk>) => response.ok),
                map((mudurluk: HttpResponse<Mudurluk>) => mudurluk.body)
            );
        }
        return of(new Mudurluk());
    }
}

export const mudurlukRoute: Routes = [
    {
        path: '',
        component: MudurlukComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.mudurluk.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MudurlukDetailComponent,
        resolve: {
            mudurluk: MudurlukResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.mudurluk.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MudurlukUpdateComponent,
        resolve: {
            mudurluk: MudurlukResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.mudurluk.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MudurlukUpdateComponent,
        resolve: {
            mudurluk: MudurlukResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.mudurluk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mudurlukPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MudurlukDeletePopupComponent,
        resolve: {
            mudurluk: MudurlukResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.mudurluk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
