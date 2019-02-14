import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPoz } from 'app/shared/model/poz.model';
import { PozService } from './poz.service';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from 'app/entities/malzeme';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IscilikService } from 'app/entities/iscilik';

@Component({
    selector: 'jhi-poz-update',
    templateUrl: './poz-update.component.html'
})
export class PozUpdateComponent implements OnInit {
    poz: IPoz;
    isSaving: boolean;

    malzemes: IMalzeme[];

    isciliks: IIscilik[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected pozService: PozService,
        protected malzemeService: MalzemeService,
        protected iscilikService: IscilikService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ poz }) => {
            this.poz = poz;
        });
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
        this.iscilikService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIscilik[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIscilik[]>) => response.body)
            )
            .subscribe((res: IIscilik[]) => (this.isciliks = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackMalzemeById(index: number, item: IMalzeme) {
        return item.id;
    }

    trackIscilikById(index: number, item: IIscilik) {
        return item.id;
    }
}
