import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDovizKur } from 'app/shared/model/doviz-kur.model';

@Component({
    selector: 'jhi-doviz-kur-detail',
    templateUrl: './doviz-kur-detail.component.html'
})
export class DovizKurDetailComponent implements OnInit {
    dovizKur: IDovizKur;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dovizKur }) => {
            this.dovizKur = dovizKur;
        });
    }

    previousState() {
        window.history.back();
    }
}
