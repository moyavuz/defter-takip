import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from './ekip.service';
import { ICalisan } from 'app/shared/model/calisan.model';
import { CalisanService } from 'app/entities/calisan';

@Component({
    selector: 'jhi-ekip-update',
    templateUrl: './ekip-update.component.html'
})
export class EkipUpdateComponent implements OnInit {
    ekip: IEkip;
    isSaving: boolean;

    sorumlus: ICalisan[];

    calisans: ICalisan[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected ekipService: EkipService,
        protected calisanService: CalisanService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ekip }) => {
            this.ekip = ekip;
        });
        this.calisanService
            .query({ filter: 'ekip-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICalisan[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICalisan[]>) => response.body)
            )
            .subscribe(
                (res: ICalisan[]) => {
                    if (!this.ekip.sorumlu || !this.ekip.sorumlu.id) {
                        this.sorumlus = res;
                    } else {
                        this.calisanService
                            .find(this.ekip.sorumlu.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICalisan>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICalisan>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICalisan) => (this.sorumlus = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.calisanService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICalisan[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICalisan[]>) => response.body)
            )
            .subscribe((res: ICalisan[]) => (this.calisans = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ekip.id !== undefined) {
            this.subscribeToSaveResponse(this.ekipService.update(this.ekip));
        } else {
            this.subscribeToSaveResponse(this.ekipService.create(this.ekip));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEkip>>) {
        result.subscribe((res: HttpResponse<IEkip>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCalisanById(index: number, item: ICalisan) {
        return item.id;
    }
}
