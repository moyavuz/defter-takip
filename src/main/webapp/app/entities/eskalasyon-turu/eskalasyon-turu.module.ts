import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    EskalasyonTuruComponent,
    EskalasyonTuruDetailComponent,
    EskalasyonTuruUpdateComponent,
    EskalasyonTuruDeletePopupComponent,
    EskalasyonTuruDeleteDialogComponent,
    eskalasyonTuruRoute,
    eskalasyonTuruPopupRoute
} from './';

const ENTITY_STATES = [...eskalasyonTuruRoute, ...eskalasyonTuruPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EskalasyonTuruComponent,
        EskalasyonTuruDetailComponent,
        EskalasyonTuruUpdateComponent,
        EskalasyonTuruDeleteDialogComponent,
        EskalasyonTuruDeletePopupComponent
    ],
    entryComponents: [
        EskalasyonTuruComponent,
        EskalasyonTuruUpdateComponent,
        EskalasyonTuruDeleteDialogComponent,
        EskalasyonTuruDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipEskalasyonTuruModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
