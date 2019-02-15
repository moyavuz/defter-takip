import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEkip } from 'app/shared/model/ekip.model';

@Component({
    selector: 'jhi-ekip-detail',
    templateUrl: './ekip-detail.component.html'
})
export class EkipDetailComponent implements OnInit {
    ekip: IEkip;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ekip }) => {
            this.ekip = ekip;
        });
    }

    previousState() {
        window.history.back();
    }
}
