import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    PersonelAracComponent,
    PersonelAracDetailComponent,
    PersonelAracUpdateComponent,
    PersonelAracDeletePopupComponent,
    PersonelAracDeleteDialogComponent,
    personelAracRoute,
    personelAracPopupRoute
} from './';

const ENTITY_STATES = [...personelAracRoute, ...personelAracPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PersonelAracComponent,
        PersonelAracDetailComponent,
        PersonelAracUpdateComponent,
        PersonelAracDeleteDialogComponent,
        PersonelAracDeletePopupComponent
    ],
    entryComponents: [
        PersonelAracComponent,
        PersonelAracUpdateComponent,
        PersonelAracDeleteDialogComponent,
        PersonelAracDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipPersonelAracModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
