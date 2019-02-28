import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjeTuru } from 'app/shared/model/proje-turu.model';
import { ProjeTuruService } from './proje-turu.service';
import { ProjeTuruComponent } from './proje-turu.component';
import { ProjeTuruDetailComponent } from './proje-turu-detail.component';
import { ProjeTuruUpdateComponent } from './proje-turu-update.component';
import { ProjeTuruDeletePopupComponent } from './proje-turu-delete-dialog.component';
import { IProjeTuru } from 'app/shared/model/proje-turu.model';

@Injectable({ providedIn: 'root' })
export class ProjeTuruResolve implements Resolve<IProjeTuru> {
    constructor(private service: ProjeTuruService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjeTuru> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProjeTuru>) => response.ok),
                map((projeTuru: HttpResponse<ProjeTuru>) => projeTuru.body)
            );
        }
        return of(new ProjeTuru());
    }
}

export const projeTuruRoute: Routes = [
    {
        path: '',
        component: ProjeTuruComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'defterTakipApp.projeTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProjeTuruDetailComponent,
        resolve: {
            projeTuru: ProjeTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.projeTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProjeTuruUpdateComponent,
        resolve: {
            projeTuru: ProjeTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.projeTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProjeTuruUpdateComponent,
        resolve: {
            projeTuru: ProjeTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.projeTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projeTuruPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProjeTuruDeletePopupComponent,
        resolve: {
            projeTuru: ProjeTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.projeTuru.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
