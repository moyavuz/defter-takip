import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonelArac } from 'app/shared/model/personel-arac.model';
import { PersonelAracService } from './personel-arac.service';
import { PersonelAracComponent } from './personel-arac.component';
import { PersonelAracDetailComponent } from './personel-arac-detail.component';
import { PersonelAracUpdateComponent } from './personel-arac-update.component';
import { PersonelAracDeletePopupComponent } from './personel-arac-delete-dialog.component';
import { IPersonelArac } from 'app/shared/model/personel-arac.model';

@Injectable({ providedIn: 'root' })
export class PersonelAracResolve implements Resolve<IPersonelArac> {
    constructor(private service: PersonelAracService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonelArac> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PersonelArac>) => response.ok),
                map((personelArac: HttpResponse<PersonelArac>) => personelArac.body)
            );
        }
        return of(new PersonelArac());
    }
}

export const personelAracRoute: Routes = [
    {
        path: '',
        component: PersonelAracComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.personelArac.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PersonelAracDetailComponent,
        resolve: {
            personelArac: PersonelAracResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelArac.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PersonelAracUpdateComponent,
        resolve: {
            personelArac: PersonelAracResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelArac.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PersonelAracUpdateComponent,
        resolve: {
            personelArac: PersonelAracResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelArac.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personelAracPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PersonelAracDeletePopupComponent,
        resolve: {
            personelArac: PersonelAracResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelArac.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
