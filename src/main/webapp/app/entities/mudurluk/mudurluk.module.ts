import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DefterTakipSharedModule } from 'app/shared';
import {
    MudurlukComponent,
    MudurlukDetailComponent,
    MudurlukUpdateComponent,
    MudurlukDeletePopupComponent,
    MudurlukDeleteDialogComponent,
    mudurlukRoute,
    mudurlukPopupRoute
} from './';

const ENTITY_STATES = [...mudurlukRoute, ...mudurlukPopupRoute];

@NgModule({
    imports: [DefterTakipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MudurlukComponent,
        MudurlukDetailComponent,
        MudurlukUpdateComponent,
        MudurlukDeleteDialogComponent,
        MudurlukDeletePopupComponent
    ],
    entryComponents: [MudurlukComponent, MudurlukUpdateComponent, MudurlukDeleteDialogComponent, MudurlukDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipMudurlukModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
