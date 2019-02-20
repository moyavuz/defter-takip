import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    HakedisDetayComponent,
    HakedisDetayDetailComponent,
    HakedisDetayUpdateComponent,
    HakedisDetayDeletePopupComponent,
    HakedisDetayDeleteDialogComponent,
    hakedisDetayRoute,
    hakedisDetayPopupRoute
} from './';

const ENTITY_STATES = [...hakedisDetayRoute, ...hakedisDetayPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HakedisDetayComponent,
        HakedisDetayDetailComponent,
        HakedisDetayUpdateComponent,
        HakedisDetayDeleteDialogComponent,
        HakedisDetayDeletePopupComponent
    ],
    entryComponents: [
        HakedisDetayComponent,
        HakedisDetayUpdateComponent,
        HakedisDetayDeleteDialogComponent,
        HakedisDetayDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipHakedisDetayModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
