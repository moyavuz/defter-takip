import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    IscilikDetayComponent,
    IscilikDetayDetailComponent,
    IscilikDetayUpdateComponent,
    IscilikDetayDeletePopupComponent,
    IscilikDetayDeleteDialogComponent,
    iscilikDetayRoute,
    iscilikDetayPopupRoute
} from './';

const ENTITY_STATES = [...iscilikDetayRoute, ...iscilikDetayPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IscilikDetayComponent,
        IscilikDetayDetailComponent,
        IscilikDetayUpdateComponent,
        IscilikDetayDeleteDialogComponent,
        IscilikDetayDeletePopupComponent
    ],
    entryComponents: [
        IscilikDetayComponent,
        IscilikDetayUpdateComponent,
        IscilikDetayDeleteDialogComponent,
        IscilikDetayDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipIscilikDetayModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
