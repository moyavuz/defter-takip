import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonelOdeme } from 'app/shared/model/personel-odeme.model';

@Component({
    selector: 'jhi-personel-odeme-detail',
    templateUrl: './personel-odeme-detail.component.html'
})
export class PersonelOdemeDetailComponent implements OnInit {
    personelOdeme: IPersonelOdeme;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personelOdeme }) => {
            this.personelOdeme = personelOdeme;
        });
    }

    previousState() {
        window.history.back();
    }
}
