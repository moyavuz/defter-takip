import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IEskalasyon } from 'app/shared/model/eskalasyon.model';
import { EskalasyonService } from './eskalasyon.service';

@Component({
    selector: 'jhi-eskalasyon-update',
    templateUrl: './eskalasyon-update.component.html'
})
export class EskalasyonUpdateComponent implements OnInit {
    eskalasyon: IEskalasyon;
    isSaving: boolean;
    tarihDp: any;

    constructor(protected eskalasyonService: EskalasyonService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ eskalasyon }) => {
            this.eskalasyon = eskalasyon;
        });
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
}
