import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IDovizKur } from 'app/shared/model/doviz-kur.model';
import { DovizKurService } from './doviz-kur.service';

@Component({
    selector: 'jhi-doviz-kur-update',
    templateUrl: './doviz-kur-update.component.html'
})
export class DovizKurUpdateComponent implements OnInit {
    dovizKur: IDovizKur;
    isSaving: boolean;
    tarihDp: any;

    constructor(protected dovizKurService: DovizKurService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dovizKur }) => {
            this.dovizKur = dovizKur;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dovizKur.id !== undefined) {
            this.subscribeToSaveResponse(this.dovizKurService.update(this.dovizKur));
        } else {
            this.subscribeToSaveResponse(this.dovizKurService.create(this.dovizKur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDovizKur>>) {
        result.subscribe((res: HttpResponse<IDovizKur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
