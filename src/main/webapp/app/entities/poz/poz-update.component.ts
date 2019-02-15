import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPoz } from 'app/shared/model/poz.model';
import { PozService } from './poz.service';
import { IBirim } from 'app/shared/model/birim.model';
import { BirimService } from 'app/entities/birim';

@Component({
    selector: 'jhi-poz-update',
    templateUrl: './poz-update.component.html'
})
export class PozUpdateComponent implements OnInit {
    poz: IPoz;
    isSaving: boolean;

    birims: IBirim[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected pozService: PozService,
        protected birimService: BirimService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ poz }) => {
            this.poz = poz;
        });
        this.birimService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBirim[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBirim[]>) => response.body)
            )
            .subscribe((res: IBirim[]) => (this.birims = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.poz.id !== undefined) {
            this.subscribeToSaveResponse(this.pozService.update(this.poz));
        } else {
            this.subscribeToSaveResponse(this.pozService.create(this.poz));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoz>>) {
        result.subscribe((res: HttpResponse<IPoz>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBirimById(index: number, item: IBirim) {
        return item.id;
    }
}
