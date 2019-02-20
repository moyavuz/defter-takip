import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils } from 'ng-jhipster';
import { IArac } from 'app/shared/model/arac.model';
import { AracService } from './arac.service';

@Component({
    selector: 'jhi-arac-update',
    templateUrl: './arac-update.component.html'
})
export class AracUpdateComponent implements OnInit {
    arac: IArac;
    isSaving: boolean;
    tarihDp: any;
    muayeneTarihDp: any;
    kaskoTarihDp: any;
    sigortaTarihDp: any;
    bakimTarihDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected aracService: AracService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ arac }) => {
            this.arac = arac;
        });
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
        this.dataUtils.clearInputImage(this.arac, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.arac.id !== undefined) {
            this.subscribeToSaveResponse(this.aracService.update(this.arac));
        } else {
            this.subscribeToSaveResponse(this.aracService.create(this.arac));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IArac>>) {
        result.subscribe((res: HttpResponse<IArac>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
