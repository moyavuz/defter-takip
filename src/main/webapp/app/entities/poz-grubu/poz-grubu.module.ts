import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    PozGrubuComponent,
    PozGrubuDetailComponent,
    PozGrubuUpdateComponent,
    PozGrubuDeletePopupComponent,
    PozGrubuDeleteDialogComponent,
    pozGrubuRoute,
    pozGrubuPopupRoute
} from './';

const ENTITY_STATES = [...pozGrubuRoute, ...pozGrubuPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PozGrubuComponent,
        PozGrubuDetailComponent,
        PozGrubuUpdateComponent,
        PozGrubuDeleteDialogComponent,
        PozGrubuDeletePopupComponent
    ],
    entryComponents: [PozGrubuComponent, PozGrubuUpdateComponent, PozGrubuDeleteDialogComponent, PozGrubuDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipPozGrubuModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
