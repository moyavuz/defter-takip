import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonelOdeme } from 'app/shared/model/personel-odeme.model';
import { PersonelOdemeService } from './personel-odeme.service';
import { PersonelOdemeComponent } from './personel-odeme.component';
import { PersonelOdemeDetailComponent } from './personel-odeme-detail.component';
import { PersonelOdemeUpdateComponent } from './personel-odeme-update.component';
import { PersonelOdemeDeletePopupComponent } from './personel-odeme-delete-dialog.component';
import { IPersonelOdeme } from 'app/shared/model/personel-odeme.model';

@Injectable({ providedIn: 'root' })
export class PersonelOdemeResolve implements Resolve<IPersonelOdeme> {
    constructor(private service: PersonelOdemeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonelOdeme> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PersonelOdeme>) => response.ok),
                map((personelOdeme: HttpResponse<PersonelOdeme>) => personelOdeme.body)
            );
        }
        return of(new PersonelOdeme());
    }
}

export const personelOdemeRoute: Routes = [
    {
        path: '',
        component: PersonelOdemeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelOdeme.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PersonelOdemeDetailComponent,
        resolve: {
            personelOdeme: PersonelOdemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelOdeme.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PersonelOdemeUpdateComponent,
        resolve: {
            personelOdeme: PersonelOdemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelOdeme.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PersonelOdemeUpdateComponent,
        resolve: {
            personelOdeme: PersonelOdemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelOdeme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personelOdemePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PersonelOdemeDeletePopupComponent,
        resolve: {
            personelOdeme: PersonelOdemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'defterTakipApp.personelOdeme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
