import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProjeTuru } from 'app/shared/model/proje-turu.model';
import { ProjeTuruService } from './proje-turu.service';

@Component({
    selector: 'jhi-proje-turu-update',
    templateUrl: './proje-turu-update.component.html'
})
export class ProjeTuruUpdateComponent implements OnInit {
    projeTuru: IProjeTuru;
    isSaving: boolean;

    constructor(protected projeTuruService: ProjeTuruService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ projeTuru }) => {
            this.projeTuru = projeTuru;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.projeTuru.id !== undefined) {
            this.subscribeToSaveResponse(this.projeTuruService.update(this.projeTuru));
        } else {
            this.subscribeToSaveResponse(this.projeTuruService.create(this.projeTuru));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjeTuru>>) {
        result.subscribe((res: HttpResponse<IProjeTuru>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
