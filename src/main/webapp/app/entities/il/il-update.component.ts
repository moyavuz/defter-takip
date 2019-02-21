import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IIl } from 'app/shared/model/il.model';
import { IlService } from './il.service';

@Component({
    selector: 'jhi-il-update',
    templateUrl: './il-update.component.html'
})
export class IlUpdateComponent implements OnInit {
    il: IIl;
    isSaving: boolean;

    constructor(protected ilService: IlService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ il }) => {
            this.il = il;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.il.id !== undefined) {
            this.subscribeToSaveResponse(this.ilService.update(this.il));
        } else {
            this.subscribeToSaveResponse(this.ilService.create(this.il));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIl>>) {
        result.subscribe((res: HttpResponse<IIl>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
