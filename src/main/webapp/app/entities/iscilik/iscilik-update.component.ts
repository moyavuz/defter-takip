import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IscilikService } from './iscilik.service';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from 'app/entities/malzeme';
import { IProje } from 'app/shared/model/proje.model';
import { ProjeService } from 'app/entities/proje';

@Component({
    selector: 'jhi-iscilik-update',
    templateUrl: './iscilik-update.component.html'
})
export class IscilikUpdateComponent implements OnInit {
    iscilik: IIscilik;
    isSaving: boolean;

    malzemes: IMalzeme[];

    projes: IProje[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected iscilikService: IscilikService,
        protected malzemeService: MalzemeService,
        protected projeService: ProjeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ iscilik }) => {
            this.iscilik = iscilik;
        });
        this.malzemeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMalzeme[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMalzeme[]>) => response.body)
            )
            .subscribe((res: IMalzeme[]) => (this.malzemes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.projeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProje[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProje[]>) => response.body)
            )
            .subscribe((res: IProje[]) => (this.projes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.iscilik.id !== undefined) {
            this.subscribeToSaveResponse(this.iscilikService.update(this.iscilik));
        } else {
            this.subscribeToSaveResponse(this.iscilikService.create(this.iscilik));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIscilik>>) {
        result.subscribe((res: HttpResponse<IIscilik>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProjeById(index: number, item: IProje) {
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
