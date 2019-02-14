import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProje } from 'app/shared/model/proje.model';

@Component({
    selector: 'jhi-proje-detail',
    templateUrl: './proje-detail.component.html'
})
export class ProjeDetailComponent implements OnInit {
    proje: IProje;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ proje }) => {
            this.proje = proje;
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
