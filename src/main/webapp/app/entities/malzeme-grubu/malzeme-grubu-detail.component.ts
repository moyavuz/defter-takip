import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

@Component({
    selector: 'jhi-malzeme-grubu-detail',
    templateUrl: './malzeme-grubu-detail.component.html'
})
export class MalzemeGrubuDetailComponent implements OnInit {
    malzemeGrubu: IMalzemeGrubu;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ malzemeGrubu }) => {
            this.malzemeGrubu = malzemeGrubu;
        });
    }

    previousState() {
        window.history.back();
    }
}
