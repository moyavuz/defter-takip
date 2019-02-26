import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    IlComponent,
    IlDetailComponent,
    IlUpdateComponent,
    IlDeletePopupComponent,
    IlDeleteDialogComponent,
    ilRoute,
    ilPopupRoute
} from './';

const ENTITY_STATES = [...ilRoute, ...ilPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [IlComponent, IlDetailComponent, IlUpdateComponent, IlDeleteDialogComponent, IlDeletePopupComponent],
    entryComponents: [IlComponent, IlUpdateComponent, IlDeleteDialogComponent, IlDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipIlModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
