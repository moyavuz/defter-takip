import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPozGrubu } from 'app/shared/model/poz-grubu.model';

@Component({
    selector: 'jhi-poz-grubu-detail',
    templateUrl: './poz-grubu-detail.component.html'
})
export class PozGrubuDetailComponent implements OnInit {
    pozGrubu: IPozGrubu;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pozGrubu }) => {
            this.pozGrubu = pozGrubu;
        });
    }

    previousState() {
        window.history.back();
    }
}
