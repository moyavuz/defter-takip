import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProje } from 'app/shared/model/proje.model';
import { ProjeService } from './proje.service';
import { IProjeTuru } from 'app/shared/model/proje-turu.model';
import { ProjeTuruService } from 'app/entities/proje-turu';
import { IBolge } from 'app/shared/model/bolge.model';
import { BolgeService } from 'app/entities/bolge';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IscilikService } from 'app/entities/iscilik';

@Component({
    selector: 'jhi-proje-update',
    templateUrl: './proje-update.component.html'
})
export class ProjeUpdateComponent implements OnInit {
    proje: IProje;
    isSaving: boolean;

    turus: IProjeTuru[];

    bolges: IBolge[];

    isciliks: IIscilik[];
    tarihDp: any;
    baslamaTarihiDp: any;
    bitisTarihiDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected projeService: ProjeService,
        protected projeTuruService: ProjeTuruService,
        protected bolgeService: BolgeService,
        protected iscilikService: IscilikService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ proje }) => {
            this.proje = proje;
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
        this.iscilikService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIscilik[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIscilik[]>) => response.body)
            )
            .subscribe((res: IIscilik[]) => (this.isciliks = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackIscilikById(index: number, item: IIscilik) {
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
