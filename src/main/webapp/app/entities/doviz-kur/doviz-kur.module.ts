import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    DovizKurComponent,
    DovizKurDetailComponent,
    DovizKurUpdateComponent,
    DovizKurDeletePopupComponent,
    DovizKurDeleteDialogComponent,
    dovizKurRoute,
    dovizKurPopupRoute
} from './';

const ENTITY_STATES = [...dovizKurRoute, ...dovizKurPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DovizKurComponent,
        DovizKurDetailComponent,
        DovizKurUpdateComponent,
        DovizKurDeleteDialogComponent,
        DovizKurDeletePopupComponent
    ],
    entryComponents: [DovizKurComponent, DovizKurUpdateComponent, DovizKurDeleteDialogComponent, DovizKurDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipDovizKurModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
