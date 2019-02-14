import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    PozComponent,
    PozDetailComponent,
    PozUpdateComponent,
    PozDeletePopupComponent,
    PozDeleteDialogComponent,
    pozRoute,
    pozPopupRoute
} from './';

const ENTITY_STATES = [...pozRoute, ...pozPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PozComponent, PozDetailComponent, PozUpdateComponent, PozDeleteDialogComponent, PozDeletePopupComponent],
    entryComponents: [PozComponent, PozUpdateComponent, PozDeleteDialogComponent, PozDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipPozModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
