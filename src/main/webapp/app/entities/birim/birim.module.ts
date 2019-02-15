import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    BirimComponent,
    BirimDetailComponent,
    BirimUpdateComponent,
    BirimDeletePopupComponent,
    BirimDeleteDialogComponent,
    birimRoute,
    birimPopupRoute
} from './';

const ENTITY_STATES = [...birimRoute, ...birimPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BirimComponent, BirimDetailComponent, BirimUpdateComponent, BirimDeleteDialogComponent, BirimDeletePopupComponent],
    entryComponents: [BirimComponent, BirimUpdateComponent, BirimDeleteDialogComponent, BirimDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipBirimModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
