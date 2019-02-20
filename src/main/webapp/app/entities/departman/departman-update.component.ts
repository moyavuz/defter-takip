import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDepartman } from 'app/shared/model/departman.model';
import { DepartmanService } from './departman.service';

@Component({
    selector: 'jhi-departman-update',
    templateUrl: './departman-update.component.html'
})
export class DepartmanUpdateComponent implements OnInit {
    departman: IDepartman;
    isSaving: boolean;

    constructor(protected departmanService: DepartmanService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ departman }) => {
            this.departman = departman;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.departman.id !== undefined) {
            this.subscribeToSaveResponse(this.departmanService.update(this.departman));
        } else {
            this.subscribeToSaveResponse(this.departmanService.create(this.departman));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartman>>) {
        result.subscribe((res: HttpResponse<IDepartman>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
