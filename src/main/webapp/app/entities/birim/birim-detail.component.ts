import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBirim } from 'app/shared/model/birim.model';

@Component({
    selector: 'jhi-birim-detail',
    templateUrl: './birim-detail.component.html'
})
export class BirimDetailComponent implements OnInit {
    birim: IBirim;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ birim }) => {
            this.birim = birim;
        });
    }

    previousState() {
        window.history.back();
    }
}
