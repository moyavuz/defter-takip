import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDefterTuru } from 'app/shared/model/defter-turu.model';

@Component({
    selector: 'jhi-defter-turu-detail',
    templateUrl: './defter-turu-detail.component.html'
})
export class DefterTuruDetailComponent implements OnInit {
    defterTuru: IDefterTuru;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ defterTuru }) => {
            this.defterTuru = defterTuru;
        });
    }

    previousState() {
        window.history.back();
    }
}
