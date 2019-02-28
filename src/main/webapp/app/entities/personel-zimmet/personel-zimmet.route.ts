import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonelZimmet } from 'app/shared/model/personel-zimmet.model';
import { PersonelZimmetService } from './personel-zimmet.service';
import { PersonelZimmetComponent } from './personel-zimmet.component';
import { PersonelZimmetDetailComponent } from './personel-zimmet-detail.component';
import { PersonelZimmetUpdateComponent } from './personel-zimmet-update.component';
import { PersonelZimmetDeletePopupComponent } from './personel-zimmet-delete-dialog.component';
import { IPersonelZimmet } from 'app/shared/model/personel-zimmet.model';

@Injectable({ providedIn: 'root' })
export class PersonelZimmetResolve implements Resolve<IPersonelZimmet> {
    constructor(private service: PersonelZimmetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonelZimmet> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PersonelZimmet>) => response.ok),
                map((personelZimmet: HttpResponse<PersonelZimmet>) => personelZimmet.body)
            );
        }
        return of(new PersonelZimmet());
    }
}

export const personelZimmetRoute: Routes = [
    {
        path: '',
        component: PersonelZimmetComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.personelZimmet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PersonelZimmetDetailComponent,
        resolve: {
            personelZimmet: PersonelZimmetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelZimmet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PersonelZimmetUpdateComponent,
        resolve: {
            personelZimmet: PersonelZimmetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelZimmet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PersonelZimmetUpdateComponent,
        resolve: {
            personelZimmet: PersonelZimmetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelZimmet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personelZimmetPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PersonelZimmetDeletePopupComponent,
        resolve: {
            personelZimmet: PersonelZimmetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelZimmet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
