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

@Component({
    selector: 'jhi-proje-update',
    templateUrl: './proje-update.component.html'
})
export class ProjeUpdateComponent implements OnInit {
    proje: IProje;
    isSaving: boolean;

    projeturus: IProjeTuru[];

    bolges: IBolge[];
    tarihDp: any;
    baslamaTarihiDp: any;
    bitisTarihiDp: any;

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
        });
        this.projeTuruService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProjeTuru[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProjeTuru[]>) => response.body)
            )
            .subscribe((res: IProjeTuru[]) => (this.projeturus = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.bolgeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBolge[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBolge[]>) => response.body)
            )
            .subscribe((res: IBolge[]) => (this.bolges = res), (res: HttpErrorResponse) => this.onError(res.message));
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
}
