import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPersonelZimmet } from 'app/shared/model/personel-zimmet.model';

@Component({
    selector: 'jhi-personel-zimmet-detail',
    templateUrl: './personel-zimmet-detail.component.html'
})
export class PersonelZimmetDetailComponent implements OnInit {
    personelZimmet: IPersonelZimmet;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personelZimmet }) => {
            this.personelZimmet = personelZimmet;
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
