import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHakedisDetay } from 'app/shared/model/hakedis-detay.model';

@Component({
    selector: 'jhi-hakedis-detay-detail',
    templateUrl: './hakedis-detay-detail.component.html'
})
export class HakedisDetayDetailComponent implements OnInit {
    hakedisDetay: IHakedisDetay;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hakedisDetay }) => {
            this.hakedisDetay = hakedisDetay;
        });
    }

    previousState() {
        window.history.back();
    }
}
