import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnvan } from 'app/shared/model/unvan.model';

@Component({
    selector: 'jhi-unvan-detail',
    templateUrl: './unvan-detail.component.html'
})
export class UnvanDetailComponent implements OnInit {
    unvan: IUnvan;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ unvan }) => {
            this.unvan = unvan;
        });
    }

    previousState() {
        window.history.back();
    }
}
