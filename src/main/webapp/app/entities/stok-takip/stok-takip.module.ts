import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    StokTakipComponent,
    StokTakipDetailComponent,
    StokTakipUpdateComponent,
    StokTakipDeletePopupComponent,
    StokTakipDeleteDialogComponent,
    stokTakipRoute,
    stokTakipPopupRoute
} from './';

const ENTITY_STATES = [...stokTakipRoute, ...stokTakipPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StokTakipComponent,
        StokTakipDetailComponent,
        StokTakipUpdateComponent,
        StokTakipDeleteDialogComponent,
        StokTakipDeletePopupComponent
    ],
    entryComponents: [StokTakipComponent, StokTakipUpdateComponent, StokTakipDeleteDialogComponent, StokTakipDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipStokTakipModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
