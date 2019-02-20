import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISantral } from 'app/shared/model/santral.model';

@Component({
    selector: 'jhi-santral-detail',
    templateUrl: './santral-detail.component.html'
})
export class SantralDetailComponent implements OnInit {
    santral: ISantral;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ santral }) => {
            this.santral = santral;
        });
    }

    previousState() {
        window.history.back();
    }
}
