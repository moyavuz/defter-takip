import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';
import { MalzemeTakipService } from './malzeme-takip.service';
import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from 'app/entities/ekip';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from 'app/entities/malzeme';

@Component({
    selector: 'jhi-malzeme-takip-update',
    templateUrl: './malzeme-takip-update.component.html'
})
export class MalzemeTakipUpdateComponent implements OnInit {
    malzemeTakip: IMalzemeTakip;
    isSaving: boolean;

    ekips: IEkip[];

    malzemes: IMalzeme[];
    tarihDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected malzemeTakipService: MalzemeTakipService,
        protected ekipService: EkipService,
        protected malzemeService: MalzemeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ malzemeTakip }) => {
            this.malzemeTakip = malzemeTakip;
        });
        this.ekipService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEkip[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEkip[]>) => response.body)
            )
            .subscribe((res: IEkip[]) => (this.ekips = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.malzemeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMalzeme[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMalzeme[]>) => response.body)
            )
            .subscribe((res: IMalzeme[]) => (this.malzemes = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.malzemeTakip.id !== undefined) {
            this.subscribeToSaveResponse(this.malzemeTakipService.update(this.malzemeTakip));
        } else {
            this.subscribeToSaveResponse(this.malzemeTakipService.create(this.malzemeTakip));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMalzemeTakip>>) {
        result.subscribe((res: HttpResponse<IMalzemeTakip>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEkipById(index: number, item: IEkip) {
        return item.id;
    }

    trackMalzemeById(index: number, item: IMalzeme) {
        return item.id;
    }
}
