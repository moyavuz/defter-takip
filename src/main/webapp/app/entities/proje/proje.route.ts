import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Proje } from 'app/shared/model/proje.model';
import { ProjeService } from './proje.service';
import { ProjeComponent } from './proje.component';
import { ProjeDetailComponent } from './proje-detail.component';
import { ProjeUpdateComponent } from './proje-update.component';
import { ProjeDeletePopupComponent } from './proje-delete-dialog.component';
import { IProje } from 'app/shared/model/proje.model';

@Injectable({ providedIn: 'root' })
export class ProjeResolve implements Resolve<IProje> {
    constructor(private service: ProjeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProje> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Proje>) => response.ok),
                map((proje: HttpResponse<Proje>) => proje.body)
            );
        }
        return of(new Proje());
    }
}

export const projeRoute: Routes = [
    {
        path: '',
        component: ProjeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.proje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProjeDetailComponent,
        resolve: {
            proje: ProjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.proje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProjeUpdateComponent,
        resolve: {
            proje: ProjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.proje.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProjeUpdateComponent,
        resolve: {
            proje: ProjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.proje.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProjeDeletePopupComponent,
        resolve: {
            proje: ProjeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.proje.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
