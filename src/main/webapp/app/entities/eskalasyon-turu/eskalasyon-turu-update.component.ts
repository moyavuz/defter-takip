import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IEskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';
import { EskalasyonTuruService } from './eskalasyon-turu.service';

@Component({
    selector: 'jhi-eskalasyon-turu-update',
    templateUrl: './eskalasyon-turu-update.component.html'
})
export class EskalasyonTuruUpdateComponent implements OnInit {
    eskalasyonTuru: IEskalasyonTuru;
    isSaving: boolean;

    constructor(protected eskalasyonTuruService: EskalasyonTuruService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ eskalasyonTuru }) => {
            this.eskalasyonTuru = eskalasyonTuru;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.eskalasyonTuru.id !== undefined) {
            this.subscribeToSaveResponse(this.eskalasyonTuruService.update(this.eskalasyonTuru));
        } else {
            this.subscribeToSaveResponse(this.eskalasyonTuruService.create(this.eskalasyonTuru));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEskalasyonTuru>>) {
        result.subscribe((res: HttpResponse<IEskalasyonTuru>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
