import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICalisan } from 'app/shared/model/calisan.model';
import { CalisanService } from './calisan.service';
import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from 'app/entities/ekip';
import { IUnvan } from 'app/shared/model/unvan.model';
import { UnvanService } from 'app/entities/unvan';

@Component({
    selector: 'jhi-calisan-update',
    templateUrl: './calisan-update.component.html'
})
export class CalisanUpdateComponent implements OnInit {
    calisan: ICalisan;
    isSaving: boolean;

    calisans: ICalisan[];

    ekips: IEkip[];

    unvans: IUnvan[];
    dogumTarihiDp: any;
    girisTarihiDp: any;
    cikisTarihiDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected calisanService: CalisanService,
        protected ekipService: EkipService,
        protected unvanService: UnvanService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ calisan }) => {
            this.calisan = calisan;
        });
        this.calisanService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICalisan[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICalisan[]>) => response.body)
            )
            .subscribe((res: ICalisan[]) => (this.calisans = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.ekipService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEkip[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEkip[]>) => response.body)
            )
            .subscribe((res: IEkip[]) => (this.ekips = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.unvanService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUnvan[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUnvan[]>) => response.body)
            )
            .subscribe((res: IUnvan[]) => (this.unvans = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
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

    trackCalisanById(index: number, item: ICalisan) {
        return item.id;
    }

    trackEkipById(index: number, item: IEkip) {
        return item.id;
    }

    trackUnvanById(index: number, item: IUnvan) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
