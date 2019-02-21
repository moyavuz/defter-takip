import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMudurluk } from 'app/shared/model/mudurluk.model';

@Component({
    selector: 'jhi-mudurluk-detail',
    templateUrl: './mudurluk-detail.component.html'
})
export class MudurlukDetailComponent implements OnInit {
    mudurluk: IMudurluk;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mudurluk }) => {
            this.mudurluk = mudurluk;
        });
    }

    previousState() {
        window.history.back();
    }
}
