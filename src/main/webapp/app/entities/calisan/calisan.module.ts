import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    CalisanComponent,
    CalisanDetailComponent,
    CalisanUpdateComponent,
    CalisanDeletePopupComponent,
    CalisanDeleteDialogComponent,
    calisanRoute,
    calisanPopupRoute
} from './';

const ENTITY_STATES = [...calisanRoute, ...calisanPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CalisanComponent,
        CalisanDetailComponent,
        CalisanUpdateComponent,
        CalisanDeleteDialogComponent,
        CalisanDeletePopupComponent
    ],
    entryComponents: [CalisanComponent, CalisanUpdateComponent, CalisanDeleteDialogComponent, CalisanDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipCalisanModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
