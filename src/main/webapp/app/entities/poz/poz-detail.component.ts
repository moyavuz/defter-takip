import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoz } from 'app/shared/model/poz.model';

@Component({
    selector: 'jhi-poz-detail',
    templateUrl: './poz-detail.component.html'
})
export class PozDetailComponent implements OnInit {
    poz: IPoz;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ poz }) => {
            this.poz = poz;
        });
    }

    previousState() {
        window.history.back();
    }
}
