import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    PersonelIzinComponent,
    PersonelIzinDetailComponent,
    PersonelIzinUpdateComponent,
    PersonelIzinDeletePopupComponent,
    PersonelIzinDeleteDialogComponent,
    personelIzinRoute,
    personelIzinPopupRoute
} from './';

const ENTITY_STATES = [...personelIzinRoute, ...personelIzinPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PersonelIzinComponent,
        PersonelIzinDetailComponent,
        PersonelIzinUpdateComponent,
        PersonelIzinDeleteDialogComponent,
        PersonelIzinDeletePopupComponent
    ],
    entryComponents: [
        PersonelIzinComponent,
        PersonelIzinUpdateComponent,
        PersonelIzinDeleteDialogComponent,
        PersonelIzinDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipPersonelIzinModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
