import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    ProjeTuruComponent,
    ProjeTuruDetailComponent,
    ProjeTuruUpdateComponent,
    ProjeTuruDeletePopupComponent,
    ProjeTuruDeleteDialogComponent,
    projeTuruRoute,
    projeTuruPopupRoute
} from './';

const ENTITY_STATES = [...projeTuruRoute, ...projeTuruPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProjeTuruComponent,
        ProjeTuruDetailComponent,
        ProjeTuruUpdateComponent,
        ProjeTuruDeleteDialogComponent,
        ProjeTuruDeletePopupComponent
    ],
    entryComponents: [ProjeTuruComponent, ProjeTuruUpdateComponent, ProjeTuruDeleteDialogComponent, ProjeTuruDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipProjeTuruModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
