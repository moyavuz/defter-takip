import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IModel } from 'app/shared/model/model.model';
import { ModelService } from './model.service';
import { IMarka } from 'app/shared/model/marka.model';
import { MarkaService } from 'app/entities/marka';

@Component({
    selector: 'jhi-model-update',
    templateUrl: './model-update.component.html'
})
export class ModelUpdateComponent implements OnInit {
    model: IModel;
    isSaving: boolean;

    markas: IMarka[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected modelService: ModelService,
        protected markaService: MarkaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ model }) => {
            this.model = model;
        });
        this.markaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMarka[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMarka[]>) => response.body)
            )
            .subscribe((res: IMarka[]) => (this.markas = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.model.id !== undefined) {
            this.subscribeToSaveResponse(this.modelService.update(this.model));
        } else {
            this.subscribeToSaveResponse(this.modelService.create(this.model));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IModel>>) {
        result.subscribe((res: HttpResponse<IModel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMarkaById(index: number, item: IMarka) {
        return item.id;
    }
}
