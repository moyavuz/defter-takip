import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HakedisTuru } from 'app/shared/model/hakedis-turu.model';
import { HakedisTuruService } from './hakedis-turu.service';
import { HakedisTuruComponent } from './hakedis-turu.component';
import { HakedisTuruDetailComponent } from './hakedis-turu-detail.component';
import { HakedisTuruUpdateComponent } from './hakedis-turu-update.component';
import { HakedisTuruDeletePopupComponent } from './hakedis-turu-delete-dialog.component';
import { IHakedisTuru } from 'app/shared/model/hakedis-turu.model';

@Injectable({ providedIn: 'root' })
export class HakedisTuruResolve implements Resolve<IHakedisTuru> {
    constructor(private service: HakedisTuruService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHakedisTuru> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HakedisTuru>) => response.ok),
                map((hakedisTuru: HttpResponse<HakedisTuru>) => hakedisTuru.body)
            );
        }
        return of(new HakedisTuru());
    }
}

export const hakedisTuruRoute: Routes = [
    {
        path: '',
        component: HakedisTuruComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.hakedisTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HakedisTuruDetailComponent,
        resolve: {
            hakedisTuru: HakedisTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedisTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HakedisTuruUpdateComponent,
        resolve: {
            hakedisTuru: HakedisTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedisTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HakedisTuruUpdateComponent,
        resolve: {
            hakedisTuru: HakedisTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedisTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hakedisTuruPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HakedisTuruDeletePopupComponent,
        resolve: {
            hakedisTuru: HakedisTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.hakedisTuru.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
