import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from './ekip.service';
import { ICalisan } from 'app/shared/model/calisan.model';
import { CalisanService } from 'app/entities/calisan';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IscilikService } from 'app/entities/iscilik';

@Component({
    selector: 'jhi-ekip-update',
    templateUrl: './ekip-update.component.html'
})
export class EkipUpdateComponent implements OnInit {
    ekip: IEkip;
    isSaving: boolean;

    calisans: ICalisan[];

    isciliks: IIscilik[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected ekipService: EkipService,
        protected calisanService: CalisanService,
        protected iscilikService: IscilikService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ekip }) => {
            this.ekip = ekip;
        });
        this.calisanService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICalisan[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICalisan[]>) => response.body)
            )
            .subscribe((res: ICalisan[]) => (this.calisans = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.ekip.id !== undefined) {
            this.subscribeToSaveResponse(this.ekipService.update(this.ekip));
        } else {
            this.subscribeToSaveResponse(this.ekipService.create(this.ekip));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEkip>>) {
        result.subscribe((res: HttpResponse<IEkip>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackIscilikById(index: number, item: IIscilik) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
