import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Departman } from 'app/shared/model/departman.model';
import { DepartmanService } from './departman.service';
import { DepartmanComponent } from './departman.component';
import { DepartmanDetailComponent } from './departman-detail.component';
import { DepartmanUpdateComponent } from './departman-update.component';
import { DepartmanDeletePopupComponent } from './departman-delete-dialog.component';
import { IDepartman } from 'app/shared/model/departman.model';

@Injectable({ providedIn: 'root' })
export class DepartmanResolve implements Resolve<IDepartman> {
    constructor(private service: DepartmanService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepartman> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Departman>) => response.ok),
                map((departman: HttpResponse<Departman>) => departman.body)
            );
        }
        return of(new Departman());
    }
}

export const departmanRoute: Routes = [
    {
        path: '',
        component: DepartmanComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.departman.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DepartmanDetailComponent,
        resolve: {
            departman: DepartmanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.departman.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepartmanUpdateComponent,
        resolve: {
            departman: DepartmanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.departman.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepartmanUpdateComponent,
        resolve: {
            departman: DepartmanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.departman.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const departmanPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepartmanDeletePopupComponent,
        resolve: {
            departman: DepartmanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.departman.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
