import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IStokTakip } from 'app/shared/model/stok-takip.model';
import { StokTakipService } from './stok-takip.service';
import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from 'app/entities/ekip';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from 'app/entities/malzeme';
import { IDepo } from 'app/shared/model/depo.model';
import { DepoService } from 'app/entities/depo';

@Component({
    selector: 'jhi-stok-takip-update',
    templateUrl: './stok-takip-update.component.html'
})
export class StokTakipUpdateComponent implements OnInit {
    stokTakip: IStokTakip;
    isSaving: boolean;

    ekips: IEkip[];

    malzemes: IMalzeme[];

    depos: IDepo[];
    tarihDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected stokTakipService: StokTakipService,
        protected ekipService: EkipService,
        protected malzemeService: MalzemeService,
        protected depoService: DepoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stokTakip }) => {
            this.stokTakip = stokTakip;
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
        this.depoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDepo[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepo[]>) => response.body)
            )
            .subscribe((res: IDepo[]) => (this.depos = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.stokTakip.id !== undefined) {
            this.subscribeToSaveResponse(this.stokTakipService.update(this.stokTakip));
        } else {
            this.subscribeToSaveResponse(this.stokTakipService.create(this.stokTakip));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStokTakip>>) {
        result.subscribe((res: HttpResponse<IStokTakip>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDepoById(index: number, item: IDepo) {
        return item.id;
    }
}
