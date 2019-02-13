import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IBirim } from 'app/shared/model/birim.model';
import { BirimService } from './birim.service';

@Component({
    selector: 'jhi-birim-update',
    templateUrl: './birim-update.component.html'
})
export class BirimUpdateComponent implements OnInit {
    birim: IBirim;
    isSaving: boolean;

    constructor(protected birimService: BirimService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ birim }) => {
            this.birim = birim;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.birim.id !== undefined) {
            this.subscribeToSaveResponse(this.birimService.update(this.birim));
        } else {
            this.subscribeToSaveResponse(this.birimService.create(this.birim));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBirim>>) {
        result.subscribe((res: HttpResponse<IBirim>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
