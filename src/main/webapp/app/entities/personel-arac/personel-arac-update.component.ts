import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPersonelArac } from 'app/shared/model/personel-arac.model';
import { PersonelAracService } from './personel-arac.service';
import { IArac } from 'app/shared/model/arac.model';
import { AracService } from 'app/entities/arac';
import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from 'app/entities/personel';

@Component({
    selector: 'jhi-personel-arac-update',
    templateUrl: './personel-arac-update.component.html'
})
export class PersonelAracUpdateComponent implements OnInit {
    personelArac: IPersonelArac;
    isSaving: boolean;

    aracs: IArac[];

    personels: IPersonel[];
    tarihDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected personelAracService: PersonelAracService,
        protected aracService: AracService,
        protected personelService: PersonelService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personelArac }) => {
            this.personelArac = personelArac;
        });
        this.aracService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IArac[]>) => mayBeOk.ok),
                map((response: HttpResponse<IArac[]>) => response.body)
            )
            .subscribe((res: IArac[]) => (this.aracs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.personelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonel[]>) => response.body)
            )
            .subscribe((res: IPersonel[]) => (this.personels = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.personelArac, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.personelArac.id !== undefined) {
            this.subscribeToSaveResponse(this.personelAracService.update(this.personelArac));
        } else {
            this.subscribeToSaveResponse(this.personelAracService.create(this.personelArac));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonelArac>>) {
        result.subscribe((res: HttpResponse<IPersonelArac>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAracById(index: number, item: IArac) {
        return item.id;
    }

    trackPersonelById(index: number, item: IPersonel) {
        return item.id;
    }
}
