import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHakedisTuru } from 'app/shared/model/hakedis-turu.model';

@Component({
    selector: 'jhi-hakedis-turu-detail',
    templateUrl: './hakedis-turu-detail.component.html'
})
export class HakedisTuruDetailComponent implements OnInit {
    hakedisTuru: IHakedisTuru;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hakedisTuru }) => {
            this.hakedisTuru = hakedisTuru;
        });
    }

    previousState() {
        window.history.back();
    }
}
