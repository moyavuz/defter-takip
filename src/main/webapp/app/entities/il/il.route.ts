import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Il } from 'app/shared/model/il.model';
import { IlService } from './il.service';
import { IlComponent } from './il.component';
import { IlDetailComponent } from './il-detail.component';
import { IlUpdateComponent } from './il-update.component';
import { IlDeletePopupComponent } from './il-delete-dialog.component';
import { IIl } from 'app/shared/model/il.model';

@Injectable({ providedIn: 'root' })
export class IlResolve implements Resolve<IIl> {
    constructor(private service: IlService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIl> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Il>) => response.ok),
                map((il: HttpResponse<Il>) => il.body)
            );
        }
        return of(new Il());
    }
}

export const ilRoute: Routes = [
    {
        path: '',
        component: IlComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.il.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IlDetailComponent,
        resolve: {
            il: IlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.il.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IlUpdateComponent,
        resolve: {
            il: IlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.il.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IlUpdateComponent,
        resolve: {
            il: IlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.il.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ilPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IlDeletePopupComponent,
        resolve: {
            il: IlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.il.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
