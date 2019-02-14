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
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from 'app/entities/malzeme';

@Component({
    selector: 'jhi-poz-update',
    templateUrl: './poz-update.component.html'
})
export class PozUpdateComponent implements OnInit {
    poz: IPoz;
    isSaving: boolean;

    birims: IBirim[];

    malzemes: IMalzeme[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected pozService: PozService,
        protected birimService: BirimService,
        protected malzemeService: MalzemeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ poz }) => {
            this.poz = poz;
        });
        this.birimService
            .query({ filter: 'poz-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IBirim[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBirim[]>) => response.body)
            )
            .subscribe(
                (res: IBirim[]) => {
                    if (!this.poz.birim || !this.poz.birim.id) {
                        this.birims = res;
                    } else {
                        this.birimService
                            .find(this.poz.birim.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IBirim>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IBirim>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IBirim) => (this.birims = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.malzemeService
            .query({ filter: 'poz-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IMalzeme[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMalzeme[]>) => response.body)
            )
            .subscribe(
                (res: IMalzeme[]) => {
                    if (!this.poz.malzeme || !this.poz.malzeme.id) {
                        this.malzemes = res;
                    } else {
                        this.malzemeService
                            .find(this.poz.malzeme.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IMalzeme>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IMalzeme>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IMalzeme) => (this.malzemes = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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

    trackMalzemeById(index: number, item: IMalzeme) {
        return item.id;
    }
}
