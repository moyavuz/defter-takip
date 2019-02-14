import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    BolgeComponent,
    BolgeDetailComponent,
    BolgeUpdateComponent,
    BolgeDeletePopupComponent,
    BolgeDeleteDialogComponent,
    bolgeRoute,
    bolgePopupRoute
} from './';

const ENTITY_STATES = [...bolgeRoute, ...bolgePopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BolgeComponent, BolgeDetailComponent, BolgeUpdateComponent, BolgeDeleteDialogComponent, BolgeDeletePopupComponent],
    entryComponents: [BolgeComponent, BolgeUpdateComponent, BolgeDeleteDialogComponent, BolgeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipBolgeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
