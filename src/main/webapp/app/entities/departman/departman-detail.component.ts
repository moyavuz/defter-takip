import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartman } from 'app/shared/model/departman.model';

@Component({
    selector: 'jhi-departman-detail',
    templateUrl: './departman-detail.component.html'
})
export class DepartmanDetailComponent implements OnInit {
    departman: IDepartman;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ departman }) => {
            this.departman = departman;
        });
    }

    previousState() {
        window.history.back();
    }
}
