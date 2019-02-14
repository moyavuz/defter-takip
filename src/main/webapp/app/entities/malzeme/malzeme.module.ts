import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    MalzemeComponent,
    MalzemeDetailComponent,
    MalzemeUpdateComponent,
    MalzemeDeletePopupComponent,
    MalzemeDeleteDialogComponent,
    malzemeRoute,
    malzemePopupRoute
} from './';

const ENTITY_STATES = [...malzemeRoute, ...malzemePopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MalzemeComponent,
        MalzemeDetailComponent,
        MalzemeUpdateComponent,
        MalzemeDeleteDialogComponent,
        MalzemeDeletePopupComponent
    ],
    entryComponents: [MalzemeComponent, MalzemeUpdateComponent, MalzemeDeleteDialogComponent, MalzemeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipMalzemeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
