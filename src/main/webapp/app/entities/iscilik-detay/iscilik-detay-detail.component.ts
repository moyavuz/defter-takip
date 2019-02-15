import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIscilikDetay } from 'app/shared/model/iscilik-detay.model';

@Component({
    selector: 'jhi-iscilik-detay-detail',
    templateUrl: './iscilik-detay-detail.component.html'
})
export class IscilikDetayDetailComponent implements OnInit {
    iscilikDetay: IIscilikDetay;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ iscilikDetay }) => {
            this.iscilikDetay = iscilikDetay;
        });
    }

    previousState() {
        window.history.back();
    }
}
