import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    SantralComponent,
    SantralDetailComponent,
    SantralUpdateComponent,
    SantralDeletePopupComponent,
    SantralDeleteDialogComponent,
    santralRoute,
    santralPopupRoute
} from './';

const ENTITY_STATES = [...santralRoute, ...santralPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SantralComponent,
        SantralDetailComponent,
        SantralUpdateComponent,
        SantralDeleteDialogComponent,
        SantralDeletePopupComponent
    ],
    entryComponents: [SantralComponent, SantralUpdateComponent, SantralDeleteDialogComponent, SantralDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipSantralModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
