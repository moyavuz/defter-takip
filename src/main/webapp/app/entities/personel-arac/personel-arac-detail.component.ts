import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPersonelArac } from 'app/shared/model/personel-arac.model';

@Component({
    selector: 'jhi-personel-arac-detail',
    templateUrl: './personel-arac-detail.component.html'
})
export class PersonelAracDetailComponent implements OnInit {
    personelArac: IPersonelArac;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personelArac }) => {
            this.personelArac = personelArac;
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
