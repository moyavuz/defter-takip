import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    DepoComponent,
    DepoDetailComponent,
    DepoUpdateComponent,
    DepoDeletePopupComponent,
    DepoDeleteDialogComponent,
    depoRoute,
    depoPopupRoute
} from './';

const ENTITY_STATES = [...depoRoute, ...depoPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DepoComponent, DepoDetailComponent, DepoUpdateComponent, DepoDeleteDialogComponent, DepoDeletePopupComponent],
    entryComponents: [DepoComponent, DepoUpdateComponent, DepoDeleteDialogComponent, DepoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipDepoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
