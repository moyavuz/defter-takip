import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';
import { MalzemeGrubuService } from './malzeme-grubu.service';
import { MalzemeGrubuComponent } from './malzeme-grubu.component';
import { MalzemeGrubuDetailComponent } from './malzeme-grubu-detail.component';
import { MalzemeGrubuUpdateComponent } from './malzeme-grubu-update.component';
import { MalzemeGrubuDeletePopupComponent } from './malzeme-grubu-delete-dialog.component';
import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

@Injectable({ providedIn: 'root' })
export class MalzemeGrubuResolve implements Resolve<IMalzemeGrubu> {
    constructor(private service: MalzemeGrubuService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMalzemeGrubu> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MalzemeGrubu>) => response.ok),
                map((malzemeGrubu: HttpResponse<MalzemeGrubu>) => malzemeGrubu.body)
            );
        }
        return of(new MalzemeGrubu());
    }
}

export const malzemeGrubuRoute: Routes = [
    {
        path: '',
        component: MalzemeGrubuComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.malzemeGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MalzemeGrubuDetailComponent,
        resolve: {
            malzemeGrubu: MalzemeGrubuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzemeGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MalzemeGrubuUpdateComponent,
        resolve: {
            malzemeGrubu: MalzemeGrubuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzemeGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MalzemeGrubuUpdateComponent,
        resolve: {
            malzemeGrubu: MalzemeGrubuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzemeGrubu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const malzemeGrubuPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MalzemeGrubuDeletePopupComponent,
        resolve: {
            malzemeGrubu: MalzemeGrubuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzemeGrubu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
