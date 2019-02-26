import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IHakedis } from 'app/shared/model/hakedis.model';

@Component({
    selector: 'jhi-hakedis-detail',
    templateUrl: './hakedis-detail.component.html'
})
export class HakedisDetailComponent implements OnInit {
    hakedis: IHakedis;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hakedis }) => {
            this.hakedis = hakedis;
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
