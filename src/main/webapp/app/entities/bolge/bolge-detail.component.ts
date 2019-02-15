import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBolge } from 'app/shared/model/bolge.model';

@Component({
    selector: 'jhi-bolge-detail',
    templateUrl: './bolge-detail.component.html'
})
export class BolgeDetailComponent implements OnInit {
    bolge: IBolge;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bolge }) => {
            this.bolge = bolge;
        });
    }

    previousState() {
        window.history.back();
    }
}
