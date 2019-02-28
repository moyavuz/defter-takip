import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PozGrubu } from 'app/shared/model/poz-grubu.model';
import { PozGrubuService } from './poz-grubu.service';
import { PozGrubuComponent } from './poz-grubu.component';
import { PozGrubuDetailComponent } from './poz-grubu-detail.component';
import { PozGrubuUpdateComponent } from './poz-grubu-update.component';
import { PozGrubuDeletePopupComponent } from './poz-grubu-delete-dialog.component';
import { IPozGrubu } from 'app/shared/model/poz-grubu.model';

@Injectable({ providedIn: 'root' })
export class PozGrubuResolve implements Resolve<IPozGrubu> {
    constructor(private service: PozGrubuService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPozGrubu> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PozGrubu>) => response.ok),
                map((pozGrubu: HttpResponse<PozGrubu>) => pozGrubu.body)
            );
        }
        return of(new PozGrubu());
    }
}

export const pozGrubuRoute: Routes = [
    {
        path: '',
        component: PozGrubuComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.pozGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PozGrubuDetailComponent,
        resolve: {
            pozGrubu: PozGrubuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.pozGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PozGrubuUpdateComponent,
        resolve: {
            pozGrubu: PozGrubuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.pozGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PozGrubuUpdateComponent,
        resolve: {
            pozGrubu: PozGrubuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.pozGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pozGrubuPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PozGrubuDeletePopupComponent,
        resolve: {
            pozGrubu: PozGrubuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.pozGrubu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
