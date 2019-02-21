import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModel } from 'app/shared/model/model.model';

@Component({
    selector: 'jhi-model-detail',
    templateUrl: './model-detail.component.html'
})
export class ModelDetailComponent implements OnInit {
    model: IModel;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ model }) => {
            this.model = model;
        });
    }

    previousState() {
        window.history.back();
    }
}
