import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDefterTuru } from 'app/shared/model/defter-turu.model';
import { DefterTuruService } from './defter-turu.service';

@Component({
    selector: 'jhi-defter-turu-update',
    templateUrl: './defter-turu-update.component.html'
})
export class DefterTuruUpdateComponent implements OnInit {
    defterTuru: IDefterTuru;
    isSaving: boolean;

    constructor(protected defterTuruService: DefterTuruService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ defterTuru }) => {
            this.defterTuru = defterTuru;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.defterTuru.id !== undefined) {
            this.subscribeToSaveResponse(this.defterTuruService.update(this.defterTuru));
        } else {
            this.subscribeToSaveResponse(this.defterTuruService.create(this.defterTuru));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDefterTuru>>) {
        result.subscribe((res: HttpResponse<IDefterTuru>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
