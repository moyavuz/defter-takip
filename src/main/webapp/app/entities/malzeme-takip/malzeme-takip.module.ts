import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    MalzemeTakipComponent,
    MalzemeTakipDetailComponent,
    MalzemeTakipUpdateComponent,
    MalzemeTakipDeletePopupComponent,
    MalzemeTakipDeleteDialogComponent,
    malzemeTakipRoute,
    malzemeTakipPopupRoute
} from './';

const ENTITY_STATES = [...malzemeTakipRoute, ...malzemeTakipPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MalzemeTakipComponent,
        MalzemeTakipDetailComponent,
        MalzemeTakipUpdateComponent,
        MalzemeTakipDeleteDialogComponent,
        MalzemeTakipDeletePopupComponent
    ],
    entryComponents: [
        MalzemeTakipComponent,
        MalzemeTakipUpdateComponent,
        MalzemeTakipDeleteDialogComponent,
        MalzemeTakipDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipMalzemeTakipModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
