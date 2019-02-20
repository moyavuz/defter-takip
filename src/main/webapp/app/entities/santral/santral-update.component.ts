import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISantral } from 'app/shared/model/santral.model';
import { SantralService } from './santral.service';

@Component({
    selector: 'jhi-santral-update',
    templateUrl: './santral-update.component.html'
})
export class SantralUpdateComponent implements OnInit {
    santral: ISantral;
    isSaving: boolean;

    constructor(protected santralService: SantralService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ santral }) => {
            this.santral = santral;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.santral.id !== undefined) {
            this.subscribeToSaveResponse(this.santralService.update(this.santral));
        } else {
            this.subscribeToSaveResponse(this.santralService.create(this.santral));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISantral>>) {
        result.subscribe((res: HttpResponse<ISantral>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
