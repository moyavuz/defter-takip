import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    AracComponent,
    AracDetailComponent,
    AracUpdateComponent,
    AracDeletePopupComponent,
    AracDeleteDialogComponent,
    aracRoute,
    aracPopupRoute
} from './';

const ENTITY_STATES = [...aracRoute, ...aracPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AracComponent, AracDetailComponent, AracUpdateComponent, AracDeleteDialogComponent, AracDeletePopupComponent],
    entryComponents: [AracComponent, AracUpdateComponent, AracDeleteDialogComponent, AracDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipAracModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
