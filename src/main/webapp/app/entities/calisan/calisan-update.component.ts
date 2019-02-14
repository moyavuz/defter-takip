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
import { IBolge } from 'app/shared/model/bolge.model';
import { BolgeService } from 'app/entities/bolge';

@Component({
    selector: 'jhi-calisan-update',
    templateUrl: './calisan-update.component.html'
})
export class CalisanUpdateComponent implements OnInit {
    calisan: ICalisan;
    isSaving: boolean;

    calisans: ICalisan[];

    ekips: IEkip[];

    bolges: IBolge[];
    dogumTarihiDp: any;
    girisTarihiDp: any;
    cikisTarihiDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected calisanService: CalisanService,
        protected ekipService: EkipService,
        protected bolgeService: BolgeService,
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
        this.bolgeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBolge[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBolge[]>) => response.body)
            )
            .subscribe((res: IBolge[]) => (this.bolges = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackBolgeById(index: number, item: IBolge) {
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
