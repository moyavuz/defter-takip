import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IscilikService } from './iscilik.service';
import { IBirim } from 'app/shared/model/birim.model';
import { BirimService } from 'app/entities/birim';
import { IPoz } from 'app/shared/model/poz.model';
import { PozService } from 'app/entities/poz';
import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from 'app/entities/ekip';
import { IProje } from 'app/shared/model/proje.model';
import { ProjeService } from 'app/entities/proje';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from 'app/entities/malzeme';

@Component({
    selector: 'jhi-iscilik-update',
    templateUrl: './iscilik-update.component.html'
})
export class IscilikUpdateComponent implements OnInit {
    iscilik: IIscilik;
    isSaving: boolean;

    birims: IBirim[];

    pozs: IPoz[];

    ekips: IEkip[];

    projes: IProje[];

    malzemes: IMalzeme[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected iscilikService: IscilikService,
        protected birimService: BirimService,
        protected pozService: PozService,
        protected ekipService: EkipService,
        protected projeService: ProjeService,
        protected malzemeService: MalzemeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ iscilik }) => {
            this.iscilik = iscilik;
        });
        this.birimService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBirim[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBirim[]>) => response.body)
            )
            .subscribe((res: IBirim[]) => (this.birims = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.pozService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPoz[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPoz[]>) => response.body)
            )
            .subscribe((res: IPoz[]) => (this.pozs = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.iscilik.id !== undefined) {
            this.subscribeToSaveResponse(this.iscilikService.update(this.iscilik));
        } else {
            this.subscribeToSaveResponse(this.iscilikService.create(this.iscilik));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIscilik>>) {
        result.subscribe((res: HttpResponse<IIscilik>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPozById(index: number, item: IPoz) {
        return item.id;
    }

    trackEkipById(index: number, item: IEkip) {
        return item.id;
    }

    trackProjeById(index: number, item: IProje) {
        return item.id;
    }

    trackMalzemeById(index: number, item: IMalzeme) {
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
