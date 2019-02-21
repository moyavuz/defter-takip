import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPersonelIzin } from 'app/shared/model/personel-izin.model';

@Component({
    selector: 'jhi-personel-izin-detail',
    templateUrl: './personel-izin-detail.component.html'
})
export class PersonelIzinDetailComponent implements OnInit {
    personelIzin: IPersonelIzin;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personelIzin }) => {
            this.personelIzin = personelIzin;
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
