import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZimmetTuru } from 'app/shared/model/zimmet-turu.model';

@Component({
    selector: 'jhi-zimmet-turu-detail',
    templateUrl: './zimmet-turu-detail.component.html'
})
export class ZimmetTuruDetailComponent implements OnInit {
    zimmetTuru: IZimmetTuru;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ zimmetTuru }) => {
            this.zimmetTuru = zimmetTuru;
        });
    }

    previousState() {
        window.history.back();
    }
}
