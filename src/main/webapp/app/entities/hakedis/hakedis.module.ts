import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    HakedisComponent,
    HakedisDetailComponent,
    HakedisUpdateComponent,
    HakedisDeletePopupComponent,
    HakedisDeleteDialogComponent,
    hakedisRoute,
    hakedisPopupRoute
} from './';

const ENTITY_STATES = [...hakedisRoute, ...hakedisPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HakedisComponent,
        HakedisDetailComponent,
        HakedisUpdateComponent,
        HakedisDeleteDialogComponent,
        HakedisDeletePopupComponent
    ],
    entryComponents: [HakedisComponent, HakedisUpdateComponent, HakedisDeleteDialogComponent, HakedisDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipHakedisModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
