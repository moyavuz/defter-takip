import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMalzeme } from 'app/shared/model/malzeme.model';

@Component({
    selector: 'jhi-malzeme-detail',
    templateUrl: './malzeme-detail.component.html'
})
export class MalzemeDetailComponent implements OnInit {
    malzeme: IMalzeme;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ malzeme }) => {
            this.malzeme = malzeme;
        });
    }

    previousState() {
        window.history.back();
    }
}
