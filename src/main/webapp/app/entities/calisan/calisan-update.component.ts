import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICalisan } from 'app/shared/model/calisan.model';
import { CalisanService } from './calisan.service';
import { IUnvan } from 'app/shared/model/unvan.model';
import { UnvanService } from 'app/entities/unvan';

@Component({
    selector: 'jhi-calisan-update',
    templateUrl: './calisan-update.component.html'
})
export class CalisanUpdateComponent implements OnInit {
    calisan: ICalisan;
    isSaving: boolean;

    unvans: IUnvan[];

    calisans: ICalisan[];
    girisTarihi: string;
    cikisTarihi: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected calisanService: CalisanService,
        protected unvanService: UnvanService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ calisan }) => {
            this.calisan = calisan;
            this.girisTarihi = this.calisan.girisTarihi != null ? this.calisan.girisTarihi.format(DATE_TIME_FORMAT) : null;
            this.cikisTarihi = this.calisan.cikisTarihi != null ? this.calisan.cikisTarihi.format(DATE_TIME_FORMAT) : null;
        });
        this.unvanService
            .query({ filter: 'calisan-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IUnvan[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUnvan[]>) => response.body)
            )
            .subscribe(
                (res: IUnvan[]) => {
                    if (!this.calisan.unvan || !this.calisan.unvan.id) {
                        this.unvans = res;
                    } else {
                        this.unvanService
                            .find(this.calisan.unvan.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IUnvan>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IUnvan>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IUnvan) => (this.unvans = [subRes].concat(res)),
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
        this.calisan.girisTarihi = this.girisTarihi != null ? moment(this.girisTarihi, DATE_TIME_FORMAT) : null;
        this.calisan.cikisTarihi = this.cikisTarihi != null ? moment(this.cikisTarihi, DATE_TIME_FORMAT) : null;
        if (this.calisan.id !== undefined) {
            this.subscribeToSaveResponse(this.calisanService.update(this.calisan));
        } else {
            this.subscribeToSaveResponse(this.calisanService.create(this.calisan));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalisan>>) {
        result.subscribe((res: HttpResponse<ICalisan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUnvanById(index: number, item: IUnvan) {
        return item.id;
    }

    trackCalisanById(index: number, item: ICalisan) {
        return item.id;
    }
}
