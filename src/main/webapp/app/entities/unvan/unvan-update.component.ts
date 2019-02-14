import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUnvan } from 'app/shared/model/unvan.model';
import { UnvanService } from './unvan.service';
import { ICalisan } from 'app/shared/model/calisan.model';
import { CalisanService } from 'app/entities/calisan';

@Component({
    selector: 'jhi-unvan-update',
    templateUrl: './unvan-update.component.html'
})
export class UnvanUpdateComponent implements OnInit {
    unvan: IUnvan;
    isSaving: boolean;

    calisans: ICalisan[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected unvanService: UnvanService,
        protected calisanService: CalisanService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unvan }) => {
            this.unvan = unvan;
        });
        this.calisanService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICalisan[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICalisan[]>) => response.body)
            )
            .subscribe((res: ICalisan[]) => (this.calisans = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCalisanById(index: number, item: ICalisan) {
        return item.id;
    }
}
