import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IHakedis } from 'app/shared/model/hakedis.model';
import { HakedisService } from './hakedis.service';
import { ISantral } from 'app/shared/model/santral.model';
import { SantralService } from 'app/entities/santral';
import { IHakedisTuru } from 'app/shared/model/hakedis-turu.model';
import { HakedisTuruService } from 'app/entities/hakedis-turu';
import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from 'app/entities/ekip';
import { IProje } from 'app/shared/model/proje.model';
import { ProjeService } from 'app/entities/proje';

@Component({
    selector: 'jhi-hakedis-update',
    templateUrl: './hakedis-update.component.html'
})
export class HakedisUpdateComponent implements OnInit {
    hakedis: IHakedis;
    isSaving: boolean;

    santrals: ISantral[];

    hakedisturus: IHakedisTuru[];

    ekips: IEkip[];

    projes: IProje[];
    tarihDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected hakedisService: HakedisService,
        protected santralService: SantralService,
        protected hakedisTuruService: HakedisTuruService,
        protected ekipService: EkipService,
        protected projeService: ProjeService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hakedis }) => {
            this.hakedis = hakedis;
        });
        this.santralService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISantral[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISantral[]>) => response.body)
            )
            .subscribe((res: ISantral[]) => (this.santrals = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.hakedisTuruService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IHakedisTuru[]>) => mayBeOk.ok),
                map((response: HttpResponse<IHakedisTuru[]>) => response.body)
            )
            .subscribe((res: IHakedisTuru[]) => (this.hakedisturus = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.ekipService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEkip[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEkip[]>) => response.body)
            )
            .subscribe((res: IEkip[]) => (this.ekips = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.projeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProje[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProje[]>) => response.body)
            )
            .subscribe((res: IProje[]) => (this.projes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.hakedis, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hakedis.id !== undefined) {
            this.subscribeToSaveResponse(this.hakedisService.update(this.hakedis));
        } else {
            this.subscribeToSaveResponse(this.hakedisService.create(this.hakedis));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHakedis>>) {
        result.subscribe((res: HttpResponse<IHakedis>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSantralById(index: number, item: ISantral) {
        return item.id;
    }

    trackHakedisTuruById(index: number, item: IHakedisTuru) {
        return item.id;
    }

    trackEkipById(index: number, item: IEkip) {
        return item.id;
    }

    trackProjeById(index: number, item: IProje) {
        return item.id;
    }
}
