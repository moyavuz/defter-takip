import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Birim } from 'app/shared/model/birim.model';
import { BirimService } from './birim.service';
import { BirimComponent } from './birim.component';
import { BirimDetailComponent } from './birim-detail.component';
import { BirimUpdateComponent } from './birim-update.component';
import { BirimDeletePopupComponent } from './birim-delete-dialog.component';
import { IBirim } from 'app/shared/model/birim.model';

@Injectable({ providedIn: 'root' })
export class BirimResolve implements Resolve<IBirim> {
    constructor(private service: BirimService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBirim> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Birim>) => response.ok),
                map((birim: HttpResponse<Birim>) => birim.body)
            );
        }
        return of(new Birim());
    }
}

export const birimRoute: Routes = [
    {
        path: '',
        component: BirimComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.birim.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BirimDetailComponent,
        resolve: {
            birim: BirimResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.birim.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BirimUpdateComponent,
        resolve: {
            birim: BirimResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.birim.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BirimUpdateComponent,
        resolve: {
            birim: BirimResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.birim.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const birimPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BirimDeletePopupComponent,
        resolve: {
            birim: BirimResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.birim.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
