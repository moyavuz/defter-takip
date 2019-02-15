import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIscilikDetay } from 'app/shared/model/iscilik-detay.model';
import { IscilikDetayService } from './iscilik-detay.service';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IscilikService } from 'app/entities/iscilik';

@Component({
    selector: 'jhi-iscilik-detay-update',
    templateUrl: './iscilik-detay-update.component.html'
})
export class IscilikDetayUpdateComponent implements OnInit {
    iscilikDetay: IIscilikDetay;
    isSaving: boolean;

    isciliks: IIscilik[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected iscilikDetayService: IscilikDetayService,
        protected iscilikService: IscilikService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ iscilikDetay }) => {
            this.iscilikDetay = iscilikDetay;
        });
        this.iscilikService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIscilik[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIscilik[]>) => response.body)
            )
            .subscribe((res: IIscilik[]) => (this.isciliks = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.iscilikDetay.id !== undefined) {
            this.subscribeToSaveResponse(this.iscilikDetayService.update(this.iscilikDetay));
        } else {
            this.subscribeToSaveResponse(this.iscilikDetayService.create(this.iscilikDetay));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIscilikDetay>>) {
        result.subscribe((res: HttpResponse<IIscilikDetay>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackIscilikById(index: number, item: IIscilik) {
        return item.id;
    }
}
