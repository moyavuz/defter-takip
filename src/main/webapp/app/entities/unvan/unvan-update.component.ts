import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IUnvan } from 'app/shared/model/unvan.model';
import { UnvanService } from './unvan.service';

@Component({
    selector: 'jhi-unvan-update',
    templateUrl: './unvan-update.component.html'
})
export class UnvanUpdateComponent implements OnInit {
    unvan: IUnvan;
    isSaving: boolean;

    constructor(protected unvanService: UnvanService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unvan }) => {
            this.unvan = unvan;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.unvan.id !== undefined) {
            this.subscribeToSaveResponse(this.unvanService.update(this.unvan));
        } else {
            this.subscribeToSaveResponse(this.unvanService.create(this.unvan));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnvan>>) {
        result.subscribe((res: HttpResponse<IUnvan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
