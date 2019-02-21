import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPozGrubu } from 'app/shared/model/poz-grubu.model';
import { PozGrubuService } from './poz-grubu.service';
import { IPoz } from 'app/shared/model/poz.model';
import { PozService } from 'app/entities/poz';

@Component({
    selector: 'jhi-poz-grubu-update',
    templateUrl: './poz-grubu-update.component.html'
})
export class PozGrubuUpdateComponent implements OnInit {
    pozGrubu: IPozGrubu;
    isSaving: boolean;

    pozs: IPoz[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected pozGrubuService: PozGrubuService,
        protected pozService: PozService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pozGrubu }) => {
            this.pozGrubu = pozGrubu;
        });
        this.pozService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPoz[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPoz[]>) => response.body)
            )
            .subscribe((res: IPoz[]) => (this.pozs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pozGrubu.id !== undefined) {
            this.subscribeToSaveResponse(this.pozGrubuService.update(this.pozGrubu));
        } else {
            this.subscribeToSaveResponse(this.pozGrubuService.create(this.pozGrubu));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPozGrubu>>) {
        result.subscribe((res: HttpResponse<IPozGrubu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPozById(index: number, item: IPoz) {
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
