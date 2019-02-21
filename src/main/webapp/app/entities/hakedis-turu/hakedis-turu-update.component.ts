import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IHakedisTuru } from 'app/shared/model/hakedis-turu.model';
import { HakedisTuruService } from './hakedis-turu.service';

@Component({
    selector: 'jhi-hakedis-turu-update',
    templateUrl: './hakedis-turu-update.component.html'
})
export class HakedisTuruUpdateComponent implements OnInit {
    hakedisTuru: IHakedisTuru;
    isSaving: boolean;

    constructor(protected hakedisTuruService: HakedisTuruService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hakedisTuru }) => {
            this.hakedisTuru = hakedisTuru;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hakedisTuru.id !== undefined) {
            this.subscribeToSaveResponse(this.hakedisTuruService.update(this.hakedisTuru));
        } else {
            this.subscribeToSaveResponse(this.hakedisTuruService.create(this.hakedisTuru));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHakedisTuru>>) {
        result.subscribe((res: HttpResponse<IHakedisTuru>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
