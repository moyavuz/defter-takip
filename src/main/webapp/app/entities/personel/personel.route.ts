import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Personel } from 'app/shared/model/personel.model';
import { PersonelService } from './personel.service';
import { PersonelComponent } from './personel.component';
import { PersonelDetailComponent } from './personel-detail.component';
import { PersonelUpdateComponent } from './personel-update.component';
import { PersonelDeletePopupComponent } from './personel-delete-dialog.component';
import { IPersonel } from 'app/shared/model/personel.model';

@Injectable({ providedIn: 'root' })
export class PersonelResolve implements Resolve<IPersonel> {
    constructor(private service: PersonelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonel> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Personel>) => response.ok),
                map((personel: HttpResponse<Personel>) => personel.body)
            );
        }
        return of(new Personel());
    }
}

export const personelRoute: Routes = [
    {
        path: '',
        component: PersonelComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PersonelDetailComponent,
        resolve: {
            personel: PersonelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PersonelUpdateComponent,
        resolve: {
            personel: PersonelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PersonelUpdateComponent,
        resolve: {
            personel: PersonelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personelPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PersonelDeletePopupComponent,
        resolve: {
            personel: PersonelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
