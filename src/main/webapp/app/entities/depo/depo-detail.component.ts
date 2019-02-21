import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepo } from 'app/shared/model/depo.model';

@Component({
    selector: 'jhi-depo-detail',
    templateUrl: './depo-detail.component.html'
})
export class DepoDetailComponent implements OnInit {
    depo: IDepo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depo }) => {
            this.depo = depo;
        });
    }

    previousState() {
        window.history.back();
    }
}
