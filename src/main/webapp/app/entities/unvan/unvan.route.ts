import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Unvan } from 'app/shared/model/unvan.model';
import { UnvanService } from './unvan.service';
import { UnvanComponent } from './unvan.component';
import { UnvanDetailComponent } from './unvan-detail.component';
import { UnvanUpdateComponent } from './unvan-update.component';
import { UnvanDeletePopupComponent } from './unvan-delete-dialog.component';
import { IUnvan } from 'app/shared/model/unvan.model';

@Injectable({ providedIn: 'root' })
export class UnvanResolve implements Resolve<IUnvan> {
    constructor(private service: UnvanService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUnvan> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Unvan>) => response.ok),
                map((unvan: HttpResponse<Unvan>) => unvan.body)
            );
        }
        return of(new Unvan());
    }
}

export const unvanRoute: Routes = [
    {
        path: '',
        component: UnvanComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.unvan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: UnvanDetailComponent,
        resolve: {
            unvan: UnvanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.unvan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: UnvanUpdateComponent,
        resolve: {
            unvan: UnvanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.unvan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: UnvanUpdateComponent,
        resolve: {
            unvan: UnvanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.unvan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const unvanPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: UnvanDeletePopupComponent,
        resolve: {
            unvan: UnvanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.unvan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
