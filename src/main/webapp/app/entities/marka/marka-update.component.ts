import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMarka } from 'app/shared/model/marka.model';
import { MarkaService } from './marka.service';

@Component({
    selector: 'jhi-marka-update',
    templateUrl: './marka-update.component.html'
})
export class MarkaUpdateComponent implements OnInit {
    marka: IMarka;
    isSaving: boolean;

    constructor(protected markaService: MarkaService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ marka }) => {
            this.marka = marka;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.marka.id !== undefined) {
            this.subscribeToSaveResponse(this.markaService.update(this.marka));
        } else {
            this.subscribeToSaveResponse(this.markaService.create(this.marka));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMarka>>) {
        result.subscribe((res: HttpResponse<IMarka>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
