import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPersonelOdeme } from 'app/shared/model/personel-odeme.model';
import { PersonelOdemeService } from './personel-odeme.service';
import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from 'app/entities/personel';

@Component({
    selector: 'jhi-personel-odeme-update',
    templateUrl: './personel-odeme-update.component.html'
})
export class PersonelOdemeUpdateComponent implements OnInit {
    personelOdeme: IPersonelOdeme;
    isSaving: boolean;

    personels: IPersonel[];
    tarihDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected personelOdemeService: PersonelOdemeService,
        protected personelService: PersonelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personelOdeme }) => {
            this.personelOdeme = personelOdeme;
        });
        this.personelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonel[]>) => response.body)
            )
            .subscribe((res: IPersonel[]) => (this.personels = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.personelOdeme.id !== undefined) {
            this.subscribeToSaveResponse(this.personelOdemeService.update(this.personelOdeme));
        } else {
            this.subscribeToSaveResponse(this.personelOdemeService.create(this.personelOdeme));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonelOdeme>>) {
        result.subscribe((res: HttpResponse<IPersonelOdeme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPersonelById(index: number, item: IPersonel) {
        return item.id;
    }
}
