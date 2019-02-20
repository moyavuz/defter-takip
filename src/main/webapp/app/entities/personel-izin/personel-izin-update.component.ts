import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPersonelIzin } from 'app/shared/model/personel-izin.model';
import { PersonelIzinService } from './personel-izin.service';
import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from 'app/entities/personel';

@Component({
    selector: 'jhi-personel-izin-update',
    templateUrl: './personel-izin-update.component.html'
})
export class PersonelIzinUpdateComponent implements OnInit {
    personelIzin: IPersonelIzin;
    isSaving: boolean;

    personels: IPersonel[];
    tarihDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected personelIzinService: PersonelIzinService,
        protected personelService: PersonelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personelIzin }) => {
            this.personelIzin = personelIzin;
        });
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.personelIzin.id !== undefined) {
            this.subscribeToSaveResponse(this.personelIzinService.update(this.personelIzin));
        } else {
            this.subscribeToSaveResponse(this.personelIzinService.create(this.personelIzin));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonelIzin>>) {
        result.subscribe((res: HttpResponse<IPersonelIzin>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
