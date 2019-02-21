import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISantral } from 'app/shared/model/santral.model';
import { SantralService } from './santral.service';
import { IMudurluk } from 'app/shared/model/mudurluk.model';
import { MudurlukService } from 'app/entities/mudurluk';

@Component({
    selector: 'jhi-santral-update',
    templateUrl: './santral-update.component.html'
})
export class SantralUpdateComponent implements OnInit {
    santral: ISantral;
    isSaving: boolean;

    mudurluks: IMudurluk[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected santralService: SantralService,
        protected mudurlukService: MudurlukService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ santral }) => {
            this.santral = santral;
        });
        this.mudurlukService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMudurluk[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMudurluk[]>) => response.body)
            )
            .subscribe((res: IMudurluk[]) => (this.mudurluks = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.santral.id !== undefined) {
            this.subscribeToSaveResponse(this.santralService.update(this.santral));
        } else {
            this.subscribeToSaveResponse(this.santralService.create(this.santral));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISantral>>) {
        result.subscribe((res: HttpResponse<ISantral>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMudurlukById(index: number, item: IMudurluk) {
        return item.id;
    }
}
