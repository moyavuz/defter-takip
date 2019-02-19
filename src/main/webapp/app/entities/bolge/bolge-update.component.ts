import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBolge } from 'app/shared/model/bolge.model';
import { BolgeService } from './bolge.service';
import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from 'app/entities/personel';

@Component({
    selector: 'jhi-bolge-update',
    templateUrl: './bolge-update.component.html'
})
export class BolgeUpdateComponent implements OnInit {
    bolge: IBolge;
    isSaving: boolean;

    personels: IPersonel[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bolgeService: BolgeService,
        protected personelService: PersonelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bolge }) => {
            this.bolge = bolge;
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
        if (this.bolge.id !== undefined) {
            this.subscribeToSaveResponse(this.bolgeService.update(this.bolge));
        } else {
            this.subscribeToSaveResponse(this.bolgeService.create(this.bolge));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBolge>>) {
        result.subscribe((res: HttpResponse<IBolge>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
