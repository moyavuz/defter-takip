import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';

@Component({
    selector: 'jhi-malzeme-takip-detail',
    templateUrl: './malzeme-takip-detail.component.html'
})
export class MalzemeTakipDetailComponent implements OnInit {
    malzemeTakip: IMalzemeTakip;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ malzemeTakip }) => {
            this.malzemeTakip = malzemeTakip;
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
