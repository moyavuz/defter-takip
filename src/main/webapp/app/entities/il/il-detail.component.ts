import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIl } from 'app/shared/model/il.model';

@Component({
    selector: 'jhi-il-detail',
    templateUrl: './il-detail.component.html'
})
export class IlDetailComponent implements OnInit {
    il: IIl;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ il }) => {
            this.il = il;
        });
    }

    previousState() {
        window.history.back();
    }
}
