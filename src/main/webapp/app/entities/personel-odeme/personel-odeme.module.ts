import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    PersonelOdemeComponent,
    PersonelOdemeDetailComponent,
    PersonelOdemeUpdateComponent,
    PersonelOdemeDeletePopupComponent,
    PersonelOdemeDeleteDialogComponent,
    personelOdemeRoute,
    personelOdemePopupRoute
} from './';

const ENTITY_STATES = [...personelOdemeRoute, ...personelOdemePopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PersonelOdemeComponent,
        PersonelOdemeDetailComponent,
        PersonelOdemeUpdateComponent,
        PersonelOdemeDeleteDialogComponent,
        PersonelOdemeDeletePopupComponent
    ],
    entryComponents: [
        PersonelOdemeComponent,
        PersonelOdemeUpdateComponent,
        PersonelOdemeDeleteDialogComponent,
        PersonelOdemeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipPersonelOdemeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
