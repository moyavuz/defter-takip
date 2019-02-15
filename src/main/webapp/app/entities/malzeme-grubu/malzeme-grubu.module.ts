import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    MalzemeGrubuComponent,
    MalzemeGrubuDetailComponent,
    MalzemeGrubuUpdateComponent,
    MalzemeGrubuDeletePopupComponent,
    MalzemeGrubuDeleteDialogComponent,
    malzemeGrubuRoute,
    malzemeGrubuPopupRoute
} from './';

const ENTITY_STATES = [...malzemeGrubuRoute, ...malzemeGrubuPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MalzemeGrubuComponent,
        MalzemeGrubuDetailComponent,
        MalzemeGrubuUpdateComponent,
        MalzemeGrubuDeleteDialogComponent,
        MalzemeGrubuDeletePopupComponent
    ],
    entryComponents: [
        MalzemeGrubuComponent,
        MalzemeGrubuUpdateComponent,
        MalzemeGrubuDeleteDialogComponent,
        MalzemeGrubuDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipMalzemeGrubuModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
