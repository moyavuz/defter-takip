import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPersonel } from 'app/shared/model/personel.model';

@Component({
    selector: 'jhi-personel-detail',
    templateUrl: './personel-detail.component.html'
})
export class PersonelDetailComponent implements OnInit {
    personel: IPersonel;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personel }) => {
            this.personel = personel;
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
