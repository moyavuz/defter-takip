import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Malzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from './malzeme.service';
import { MalzemeComponent } from './malzeme.component';
import { MalzemeDetailComponent } from './malzeme-detail.component';
import { MalzemeUpdateComponent } from './malzeme-update.component';
import { MalzemeDeletePopupComponent } from './malzeme-delete-dialog.component';
import { IMalzeme } from 'app/shared/model/malzeme.model';

@Injectable({ providedIn: 'root' })
export class MalzemeResolve implements Resolve<IMalzeme> {
    constructor(private service: MalzemeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMalzeme> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Malzeme>) => response.ok),
                map((malzeme: HttpResponse<Malzeme>) => malzeme.body)
            );
        }
        return of(new Malzeme());
    }
}

export const malzemeRoute: Routes = [
    {
        path: '',
        component: MalzemeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzeme.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MalzemeDetailComponent,
        resolve: {
            malzeme: MalzemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzeme.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MalzemeUpdateComponent,
        resolve: {
            malzeme: MalzemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzeme.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MalzemeUpdateComponent,
        resolve: {
            malzeme: MalzemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzeme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const malzemePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MalzemeDeletePopupComponent,
        resolve: {
            malzeme: MalzemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.malzeme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
