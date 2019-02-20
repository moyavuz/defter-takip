import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    ModelComponent,
    ModelDetailComponent,
    ModelUpdateComponent,
    ModelDeletePopupComponent,
    ModelDeleteDialogComponent,
    modelRoute,
    modelPopupRoute
} from './';

const ENTITY_STATES = [...modelRoute, ...modelPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ModelComponent, ModelDetailComponent, ModelUpdateComponent, ModelDeleteDialogComponent, ModelDeletePopupComponent],
    entryComponents: [ModelComponent, ModelUpdateComponent, ModelDeleteDialogComponent, ModelDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipModelModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
