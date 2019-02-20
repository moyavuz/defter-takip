import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    DefterTuruComponent,
    DefterTuruDetailComponent,
    DefterTuruUpdateComponent,
    DefterTuruDeletePopupComponent,
    DefterTuruDeleteDialogComponent,
    defterTuruRoute,
    defterTuruPopupRoute
} from './';

const ENTITY_STATES = [...defterTuruRoute, ...defterTuruPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DefterTuruComponent,
        DefterTuruDetailComponent,
        DefterTuruUpdateComponent,
        DefterTuruDeleteDialogComponent,
        DefterTuruDeletePopupComponent
    ],
    entryComponents: [DefterTuruComponent, DefterTuruUpdateComponent, DefterTuruDeleteDialogComponent, DefterTuruDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipDefterTuruModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
