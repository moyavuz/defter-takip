import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    ZimmetTuruComponent,
    ZimmetTuruDetailComponent,
    ZimmetTuruUpdateComponent,
    ZimmetTuruDeletePopupComponent,
    ZimmetTuruDeleteDialogComponent,
    zimmetTuruRoute,
    zimmetTuruPopupRoute
} from './';

const ENTITY_STATES = [...zimmetTuruRoute, ...zimmetTuruPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ZimmetTuruComponent,
        ZimmetTuruDetailComponent,
        ZimmetTuruUpdateComponent,
        ZimmetTuruDeleteDialogComponent,
        ZimmetTuruDeletePopupComponent
    ],
    entryComponents: [ZimmetTuruComponent, ZimmetTuruUpdateComponent, ZimmetTuruDeleteDialogComponent, ZimmetTuruDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipZimmetTuruModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
