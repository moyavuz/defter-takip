import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IIscilik } from 'app/shared/model/iscilik.model';

@Component({
    selector: 'jhi-iscilik-detail',
    templateUrl: './iscilik-detail.component.html'
})
export class IscilikDetailComponent implements OnInit {
    iscilik: IIscilik;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ iscilik }) => {
            this.iscilik = iscilik;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
