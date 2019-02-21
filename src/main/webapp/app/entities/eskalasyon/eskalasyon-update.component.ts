import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEskalasyon } from 'app/shared/model/eskalasyon.model';
import { EskalasyonService } from './eskalasyon.service';
import { IEskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';
import { EskalasyonTuruService } from 'app/entities/eskalasyon-turu';

@Component({
    selector: 'jhi-eskalasyon-update',
    templateUrl: './eskalasyon-update.component.html'
})
export class EskalasyonUpdateComponent implements OnInit {
    eskalasyon: IEskalasyon;
    isSaving: boolean;

    eskalasyonturus: IEskalasyonTuru[];
    tarihDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected eskalasyonService: EskalasyonService,
        protected eskalasyonTuruService: EskalasyonTuruService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ eskalasyon }) => {
            this.eskalasyon = eskalasyon;
        });
        this.eskalasyonTuruService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEskalasyonTuru[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEskalasyonTuru[]>) => response.body)
            )
            .subscribe((res: IEskalasyonTuru[]) => (this.eskalasyonturus = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.eskalasyon.id !== undefined) {
            this.subscribeToSaveResponse(this.eskalasyonService.update(this.eskalasyon));
        } else {
            this.subscribeToSaveResponse(this.eskalasyonService.create(this.eskalasyon));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEskalasyon>>) {
        result.subscribe((res: HttpResponse<IEskalasyon>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEskalasyonTuruById(index: number, item: IEskalasyonTuru) {
        return item.id;
    }
}
