import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IHakedisDetay } from 'app/shared/model/hakedis-detay.model';
import { HakedisDetayService } from './hakedis-detay.service';
import { IHakedis } from 'app/shared/model/hakedis.model';
import { HakedisService } from 'app/entities/hakedis';
import { IPoz } from 'app/shared/model/poz.model';
import { PozService } from 'app/entities/poz';

@Component({
    selector: 'jhi-hakedis-detay-update',
    templateUrl: './hakedis-detay-update.component.html'
})
export class HakedisDetayUpdateComponent implements OnInit {
    hakedisDetay: IHakedisDetay;
    isSaving: boolean;

    hakedis: IHakedis[];

    pozs: IPoz[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected hakedisDetayService: HakedisDetayService,
        protected hakedisService: HakedisService,
        protected pozService: PozService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hakedisDetay }) => {
            this.hakedisDetay = hakedisDetay;
        });
        this.hakedisService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IHakedis[]>) => mayBeOk.ok),
                map((response: HttpResponse<IHakedis[]>) => response.body)
            )
            .subscribe((res: IHakedis[]) => (this.hakedis = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.pozService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPoz[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPoz[]>) => response.body)
            )
            .subscribe((res: IPoz[]) => (this.pozs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hakedisDetay.id !== undefined) {
            this.subscribeToSaveResponse(this.hakedisDetayService.update(this.hakedisDetay));
        } else {
            this.subscribeToSaveResponse(this.hakedisDetayService.create(this.hakedisDetay));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHakedisDetay>>) {
        result.subscribe((res: HttpResponse<IHakedisDetay>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackHakedisById(index: number, item: IHakedis) {
        return item.id;
    }

    trackPozById(index: number, item: IPoz) {
        return item.id;
    }
}
