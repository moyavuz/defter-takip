import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    UnvanComponent,
    UnvanDetailComponent,
    UnvanUpdateComponent,
    UnvanDeletePopupComponent,
    UnvanDeleteDialogComponent,
    unvanRoute,
    unvanPopupRoute
} from './';

const ENTITY_STATES = [...unvanRoute, ...unvanPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [UnvanComponent, UnvanDetailComponent, UnvanUpdateComponent, UnvanDeleteDialogComponent, UnvanDeletePopupComponent],
    entryComponents: [UnvanComponent, UnvanUpdateComponent, UnvanDeleteDialogComponent, UnvanDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipUnvanModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
