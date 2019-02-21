import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPersonelZimmet } from 'app/shared/model/personel-zimmet.model';
import { PersonelZimmetService } from './personel-zimmet.service';
import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from 'app/entities/personel';
import { IZimmetTuru } from 'app/shared/model/zimmet-turu.model';
import { ZimmetTuruService } from 'app/entities/zimmet-turu';

@Component({
    selector: 'jhi-personel-zimmet-update',
    templateUrl: './personel-zimmet-update.component.html'
})
export class PersonelZimmetUpdateComponent implements OnInit {
    personelZimmet: IPersonelZimmet;
    isSaving: boolean;

    personels: IPersonel[];

    zimmetturus: IZimmetTuru[];
    tarihDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected personelZimmetService: PersonelZimmetService,
        protected personelService: PersonelService,
        protected zimmetTuruService: ZimmetTuruService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personelZimmet }) => {
            this.personelZimmet = personelZimmet;
        });
        this.personelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonel[]>) => response.body)
            )
            .subscribe((res: IPersonel[]) => (this.personels = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.zimmetTuruService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IZimmetTuru[]>) => mayBeOk.ok),
                map((response: HttpResponse<IZimmetTuru[]>) => response.body)
            )
            .subscribe((res: IZimmetTuru[]) => (this.zimmetturus = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.personelZimmet, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.personelZimmet.id !== undefined) {
            this.subscribeToSaveResponse(this.personelZimmetService.update(this.personelZimmet));
        } else {
            this.subscribeToSaveResponse(this.personelZimmetService.create(this.personelZimmet));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonelZimmet>>) {
        result.subscribe((res: HttpResponse<IPersonelZimmet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPersonelById(index: number, item: IPersonel) {
        return item.id;
    }

    trackZimmetTuruById(index: number, item: IZimmetTuru) {
        return item.id;
    }
}
