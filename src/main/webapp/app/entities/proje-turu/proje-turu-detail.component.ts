import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjeTuru } from 'app/shared/model/proje-turu.model';

@Component({
    selector: 'jhi-proje-turu-detail',
    templateUrl: './proje-turu-detail.component.html'
})
export class ProjeTuruDetailComponent implements OnInit {
    projeTuru: IProjeTuru;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ projeTuru }) => {
            this.projeTuru = projeTuru;
        });
    }

    previousState() {
        window.history.back();
    }
}
