import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalisan } from 'app/shared/model/calisan.model';

@Component({
    selector: 'jhi-calisan-detail',
    templateUrl: './calisan-detail.component.html'
})
export class CalisanDetailComponent implements OnInit {
    calisan: ICalisan;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calisan }) => {
            this.calisan = calisan;
        });
    }

    previousState() {
        window.history.back();
    }
}
