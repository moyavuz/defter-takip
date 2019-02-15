import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    EkipComponent,
    EkipDetailComponent,
    EkipUpdateComponent,
    EkipDeletePopupComponent,
    EkipDeleteDialogComponent,
    ekipRoute,
    ekipPopupRoute
} from './';

const ENTITY_STATES = [...ekipRoute, ...ekipPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [EkipComponent, EkipDetailComponent, EkipUpdateComponent, EkipDeleteDialogComponent, EkipDeletePopupComponent],
    entryComponents: [EkipComponent, EkipUpdateComponent, EkipDeleteDialogComponent, EkipDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipEkipModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
