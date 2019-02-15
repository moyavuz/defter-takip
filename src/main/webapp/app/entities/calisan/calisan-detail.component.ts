import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICalisan } from 'app/shared/model/calisan.model';

@Component({
    selector: 'jhi-calisan-detail',
    templateUrl: './calisan-detail.component.html'
})
export class CalisanDetailComponent implements OnInit {
    calisan: ICalisan;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calisan }) => {
            this.calisan = calisan;
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
