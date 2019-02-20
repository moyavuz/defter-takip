import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from './personel.service';
import { IUnvan } from 'app/shared/model/unvan.model';
import { UnvanService } from 'app/entities/unvan';
import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from 'app/entities/ekip';

@Component({
    selector: 'jhi-personel-update',
    templateUrl: './personel-update.component.html'
})
export class PersonelUpdateComponent implements OnInit {
    personel: IPersonel;
    isSaving: boolean;

    personels: IPersonel[];

    unvans: IUnvan[];

    ekips: IEkip[];
    dogumTarihiDp: any;
    girisTarihiDp: any;
    cikisTarihiDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected personelService: PersonelService,
        protected unvanService: UnvanService,
        protected ekipService: EkipService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personel }) => {
            this.personel = personel;
        });
        this.personelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonel[]>) => response.body)
            )
            .subscribe((res: IPersonel[]) => (this.personels = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.unvanService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUnvan[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUnvan[]>) => response.body)
            )
            .subscribe((res: IUnvan[]) => (this.unvans = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.ekipService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEkip[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEkip[]>) => response.body)
            )
            .subscribe((res: IEkip[]) => (this.ekips = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.personel, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.personel.id !== undefined) {
            this.subscribeToSaveResponse(this.personelService.update(this.personel));
        } else {
            this.subscribeToSaveResponse(this.personelService.create(this.personel));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonel>>) {
        result.subscribe((res: HttpResponse<IPersonel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUnvanById(index: number, item: IUnvan) {
        return item.id;
    }

    trackEkipById(index: number, item: IEkip) {
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
