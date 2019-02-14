import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';
import { MalzemeGrubuService } from './malzeme-grubu.service';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from 'app/entities/malzeme';

@Component({
    selector: 'jhi-malzeme-grubu-update',
    templateUrl: './malzeme-grubu-update.component.html'
})
export class MalzemeGrubuUpdateComponent implements OnInit {
    malzemeGrubu: IMalzemeGrubu;
    isSaving: boolean;

    malzemes: IMalzeme[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected malzemeGrubuService: MalzemeGrubuService,
        protected malzemeService: MalzemeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ malzemeGrubu }) => {
            this.malzemeGrubu = malzemeGrubu;
        });
        this.malzemeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMalzeme[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMalzeme[]>) => response.body)
            )
            .subscribe((res: IMalzeme[]) => (this.malzemes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.malzemeGrubu.id !== undefined) {
            this.subscribeToSaveResponse(this.malzemeGrubuService.update(this.malzemeGrubu));
        } else {
            this.subscribeToSaveResponse(this.malzemeGrubuService.create(this.malzemeGrubu));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMalzemeGrubu>>) {
        result.subscribe((res: HttpResponse<IMalzemeGrubu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMalzemeById(index: number, item: IMalzeme) {
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
