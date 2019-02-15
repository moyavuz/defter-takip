import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    ProjeComponent,
    ProjeDetailComponent,
    ProjeUpdateComponent,
    ProjeDeletePopupComponent,
    ProjeDeleteDialogComponent,
    projeRoute,
    projePopupRoute
} from './';

const ENTITY_STATES = [...projeRoute, ...projePopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ProjeComponent, ProjeDetailComponent, ProjeUpdateComponent, ProjeDeleteDialogComponent, ProjeDeletePopupComponent],
    entryComponents: [ProjeComponent, ProjeUpdateComponent, ProjeDeleteDialogComponent, ProjeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipProjeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
