import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IZimmetTuru } from 'app/shared/model/zimmet-turu.model';
import { ZimmetTuruService } from './zimmet-turu.service';

@Component({
    selector: 'jhi-zimmet-turu-update',
    templateUrl: './zimmet-turu-update.component.html'
})
export class ZimmetTuruUpdateComponent implements OnInit {
    zimmetTuru: IZimmetTuru;
    isSaving: boolean;

    constructor(protected zimmetTuruService: ZimmetTuruService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ zimmetTuru }) => {
            this.zimmetTuru = zimmetTuru;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.zimmetTuru.id !== undefined) {
            this.subscribeToSaveResponse(this.zimmetTuruService.update(this.zimmetTuru));
        } else {
            this.subscribeToSaveResponse(this.zimmetTuruService.create(this.zimmetTuru));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IZimmetTuru>>) {
        result.subscribe((res: HttpResponse<IZimmetTuru>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
