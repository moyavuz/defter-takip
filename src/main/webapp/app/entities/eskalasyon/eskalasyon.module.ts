import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    EskalasyonComponent,
    EskalasyonDetailComponent,
    EskalasyonUpdateComponent,
    EskalasyonDeletePopupComponent,
    EskalasyonDeleteDialogComponent,
    eskalasyonRoute,
    eskalasyonPopupRoute
} from './';

const ENTITY_STATES = [...eskalasyonRoute, ...eskalasyonPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EskalasyonComponent,
        EskalasyonDetailComponent,
        EskalasyonUpdateComponent,
        EskalasyonDeleteDialogComponent,
        EskalasyonDeletePopupComponent
    ],
    entryComponents: [EskalasyonComponent, EskalasyonUpdateComponent, EskalasyonDeleteDialogComponent, EskalasyonDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipEskalasyonModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
