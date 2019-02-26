import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';

@Component({
    selector: 'jhi-eskalasyon-turu-detail',
    templateUrl: './eskalasyon-turu-detail.component.html'
})
export class EskalasyonTuruDetailComponent implements OnInit {
    eskalasyonTuru: IEskalasyonTuru;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eskalasyonTuru }) => {
            this.eskalasyonTuru = eskalasyonTuru;
        });
    }

    previousState() {
        window.history.back();
    }
}
