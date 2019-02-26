import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMarka } from 'app/shared/model/marka.model';

@Component({
    selector: 'jhi-marka-detail',
    templateUrl: './marka-detail.component.html'
})
export class MarkaDetailComponent implements OnInit {
    marka: IMarka;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ marka }) => {
            this.marka = marka;
        });
    }

    previousState() {
        window.history.back();
    }
}
