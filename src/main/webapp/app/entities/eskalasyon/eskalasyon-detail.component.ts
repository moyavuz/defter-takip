import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEskalasyon } from 'app/shared/model/eskalasyon.model';

@Component({
    selector: 'jhi-eskalasyon-detail',
    templateUrl: './eskalasyon-detail.component.html'
})
export class EskalasyonDetailComponent implements OnInit {
    eskalasyon: IEskalasyon;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eskalasyon }) => {
            this.eskalasyon = eskalasyon;
        });
    }

    previousState() {
        window.history.back();
    }
}
