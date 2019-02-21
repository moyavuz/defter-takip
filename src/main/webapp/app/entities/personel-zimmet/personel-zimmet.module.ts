import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    PersonelZimmetComponent,
    PersonelZimmetDetailComponent,
    PersonelZimmetUpdateComponent,
    PersonelZimmetDeletePopupComponent,
    PersonelZimmetDeleteDialogComponent,
    personelZimmetRoute,
    personelZimmetPopupRoute
} from './';

const ENTITY_STATES = [...personelZimmetRoute, ...personelZimmetPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PersonelZimmetComponent,
        PersonelZimmetDetailComponent,
        PersonelZimmetUpdateComponent,
        PersonelZimmetDeleteDialogComponent,
        PersonelZimmetDeletePopupComponent
    ],
    entryComponents: [
        PersonelZimmetComponent,
        PersonelZimmetUpdateComponent,
        PersonelZimmetDeleteDialogComponent,
        PersonelZimmetDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipPersonelZimmetModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
