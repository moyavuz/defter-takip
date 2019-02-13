import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProje } from 'app/shared/model/proje.model';
import { ProjeService } from './proje.service';
import { IProjeTuru } from 'app/shared/model/proje-turu.model';
import { ProjeTuruService } from 'app/entities/proje-turu';
import { IBolge } from 'app/shared/model/bolge.model';
import { BolgeService } from 'app/entities/bolge';

@Component({
    selector: 'jhi-proje-update',
    templateUrl: './proje-update.component.html'
})
export class ProjeUpdateComponent implements OnInit {
    proje: IProje;
    isSaving: boolean;

    turus: IProjeTuru[];

    bolges: IBolge[];
    tarih: string;
    baslamaTarihi: string;
    bitisTarihi: string;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected projeService: ProjeService,
        protected projeTuruService: ProjeTuruService,
        protected bolgeService: BolgeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ proje }) => {
            this.proje = proje;
            this.tarih = this.proje.tarih != null ? this.proje.tarih.format(DATE_TIME_FORMAT) : null;
            this.baslamaTarihi = this.proje.baslamaTarihi != null ? this.proje.baslamaTarihi.format(DATE_TIME_FORMAT) : null;
            this.bitisTarihi = this.proje.bitisTarihi != null ? this.proje.bitisTarihi.format(DATE_TIME_FORMAT) : null;
        });
        this.projeTuruService
            .query({ filter: 'proje-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IProjeTuru[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProjeTuru[]>) => response.body)
            )
            .subscribe(
                (res: IProjeTuru[]) => {
                    if (!this.proje.turu || !this.proje.turu.id) {
                        this.turus = res;
                    } else {
                        this.projeTuruService
                            .find(this.proje.turu.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IProjeTuru>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IProjeTuru>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IProjeTuru) => (this.turus = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.bolgeService
            .query({ filter: 'proje-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IBolge[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBolge[]>) => response.body)
            )
            .subscribe(
                (res: IBolge[]) => {
                    if (!this.proje.bolge || !this.proje.bolge.id) {
                        this.bolges = res;
                    } else {
                        this.bolgeService
                            .find(this.proje.bolge.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IBolge>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IBolge>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IBolge) => (this.bolges = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
        this.proje.tarih = this.tarih != null ? moment(this.tarih, DATE_TIME_FORMAT) : null;
        this.proje.baslamaTarihi = this.baslamaTarihi != null ? moment(this.baslamaTarihi, DATE_TIME_FORMAT) : null;
        this.proje.bitisTarihi = this.bitisTarihi != null ? moment(this.bitisTarihi, DATE_TIME_FORMAT) : null;
        if (this.proje.id !== undefined) {
            this.subscribeToSaveResponse(this.projeService.update(this.proje));
        } else {
            this.subscribeToSaveResponse(this.projeService.create(this.proje));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProje>>) {
        result.subscribe((res: HttpResponse<IProje>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProjeTuruById(index: number, item: IProjeTuru) {
        return item.id;
    }

    trackBolgeById(index: number, item: IBolge) {
        return item.id;
    }
}
