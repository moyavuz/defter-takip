import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from './malzeme.service';
import { IBirim } from 'app/shared/model/birim.model';
import { BirimService } from 'app/entities/birim';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IscilikService } from 'app/entities/iscilik';
import { IPoz } from 'app/shared/model/poz.model';
import { PozService } from 'app/entities/poz';
import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';
import { MalzemeGrubuService } from 'app/entities/malzeme-grubu';

@Component({
    selector: 'jhi-malzeme-update',
    templateUrl: './malzeme-update.component.html'
})
export class MalzemeUpdateComponent implements OnInit {
    malzeme: IMalzeme;
    isSaving: boolean;

    birims: IBirim[];

    isciliks: IIscilik[];

    pozs: IPoz[];

    malzemegrubus: IMalzemeGrubu[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected malzemeService: MalzemeService,
        protected birimService: BirimService,
        protected iscilikService: IscilikService,
        protected pozService: PozService,
        protected malzemeGrubuService: MalzemeGrubuService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ malzeme }) => {
            this.malzeme = malzeme;
        });
        this.birimService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBirim[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBirim[]>) => response.body)
            )
            .subscribe((res: IBirim[]) => (this.birims = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.iscilikService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIscilik[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIscilik[]>) => response.body)
            )
            .subscribe((res: IIscilik[]) => (this.isciliks = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.pozService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPoz[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPoz[]>) => response.body)
            )
            .subscribe((res: IPoz[]) => (this.pozs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.malzemeGrubuService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMalzemeGrubu[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMalzemeGrubu[]>) => response.body)
            )
            .subscribe((res: IMalzemeGrubu[]) => (this.malzemegrubus = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.malzeme.id !== undefined) {
            this.subscribeToSaveResponse(this.malzemeService.update(this.malzeme));
        } else {
            this.subscribeToSaveResponse(this.malzemeService.create(this.malzeme));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMalzeme>>) {
        result.subscribe((res: HttpResponse<IMalzeme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBirimById(index: number, item: IBirim) {
        return item.id;
    }

    trackIscilikById(index: number, item: IIscilik) {
        return item.id;
    }

    trackPozById(index: number, item: IPoz) {
        return item.id;
    }

    trackMalzemeGrubuById(index: number, item: IMalzemeGrubu) {
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
