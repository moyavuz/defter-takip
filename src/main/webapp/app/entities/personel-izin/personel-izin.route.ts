import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonelIzin } from 'app/shared/model/personel-izin.model';
import { PersonelIzinService } from './personel-izin.service';
import { PersonelIzinComponent } from './personel-izin.component';
import { PersonelIzinDetailComponent } from './personel-izin-detail.component';
import { PersonelIzinUpdateComponent } from './personel-izin-update.component';
import { PersonelIzinDeletePopupComponent } from './personel-izin-delete-dialog.component';
import { IPersonelIzin } from 'app/shared/model/personel-izin.model';

@Injectable({ providedIn: 'root' })
export class PersonelIzinResolve implements Resolve<IPersonelIzin> {
    constructor(private service: PersonelIzinService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonelIzin> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PersonelIzin>) => response.ok),
                map((personelIzin: HttpResponse<PersonelIzin>) => personelIzin.body)
            );
        }
        return of(new PersonelIzin());
    }
}

export const personelIzinRoute: Routes = [
    {
        path: '',
        component: PersonelIzinComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelIzin.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PersonelIzinDetailComponent,
        resolve: {
            personelIzin: PersonelIzinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelIzin.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PersonelIzinUpdateComponent,
        resolve: {
            personelIzin: PersonelIzinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelIzin.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PersonelIzinUpdateComponent,
        resolve: {
            personelIzin: PersonelIzinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelIzin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personelIzinPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PersonelIzinDeletePopupComponent,
        resolve: {
            personelIzin: PersonelIzinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelIzin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
