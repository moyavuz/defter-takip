import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DefterTuru } from 'app/shared/model/defter-turu.model';
import { DefterTuruService } from './defter-turu.service';
import { DefterTuruComponent } from './defter-turu.component';
import { DefterTuruDetailComponent } from './defter-turu-detail.component';
import { DefterTuruUpdateComponent } from './defter-turu-update.component';
import { DefterTuruDeletePopupComponent } from './defter-turu-delete-dialog.component';
import { IDefterTuru } from 'app/shared/model/defter-turu.model';

@Injectable({ providedIn: 'root' })
export class DefterTuruResolve implements Resolve<IDefterTuru> {
    constructor(private service: DefterTuruService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDefterTuru> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DefterTuru>) => response.ok),
                map((defterTuru: HttpResponse<DefterTuru>) => defterTuru.body)
            );
        }
        return of(new DefterTuru());
    }
}

export const defterTuruRoute: Routes = [
    {
        path: '',
        component: DefterTuruComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.defterTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DefterTuruDetailComponent,
        resolve: {
            defterTuru: DefterTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.defterTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DefterTuruUpdateComponent,
        resolve: {
            defterTuru: DefterTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.defterTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DefterTuruUpdateComponent,
        resolve: {
            defterTuru: DefterTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.defterTuru.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const defterTuruPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DefterTuruDeletePopupComponent,
        resolve: {
            defterTuru: DefterTuruResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.defterTuru.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
