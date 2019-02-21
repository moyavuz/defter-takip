import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ZimmetTuru } from 'app/shared/model/zimmet-turu.model';
import { ZimmetTuruService } from './zimmet-turu.service';
import { ZimmetTuruComponent } from './zimmet-turu.component';
import { ZimmetTuruDetailComponent } from './zimmet-turu-detail.component';
import { ZimmetTuruUpdateComponent } from './zimmet-turu-update.component';
import { ZimmetTuruDeletePopupComponent } from './zimmet-turu-delete-dialog.component';
import { IZimmetTuru } from 'app/shared/model/zimmet-turu.model';

@Injectable({ providedIn: 'root' })
export class ZimmetTuruResolve implements Resolve<IZimmetTuru> {
    constructor(private service: ZimmetTuruService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IZimmetTuru> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ZimmetTuru>) => response.ok),
                map((zimmetTuru: HttpResponse<ZimmetTuru>) => zimmetTuru.body)
            );
        }
        return of(new ZimmetTuru());
    }
}

export const zimmetTuruRoute: Routes = [
    {
        path: '',
        component: ZimmetTuruComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.zimmetTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ZimmetTuruDetailComponent,
        resolve: {
            zimmetTuru: ZimmetTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.zimmetTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ZimmetTuruUpdateComponent,
        resolve: {
            zimmetTuru: ZimmetTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.zimmetTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ZimmetTuruUpdateComponent,
        resolve: {
            zimmetTuru: ZimmetTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.zimmetTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const zimmetTuruPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ZimmetTuruDeletePopupComponent,
        resolve: {
            zimmetTuru: ZimmetTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.zimmetTuru.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
