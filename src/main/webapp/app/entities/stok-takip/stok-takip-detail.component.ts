import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IStokTakip } from 'app/shared/model/stok-takip.model';

@Component({
    selector: 'jhi-stok-takip-detail',
    templateUrl: './stok-takip-detail.component.html'
})
export class StokTakipDetailComponent implements OnInit {
    stokTakip: IStokTakip;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stokTakip }) => {
            this.stokTakip = stokTakip;
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
