import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from './ekip.service';
import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from 'app/entities/personel';
import { IMudurluk } from 'app/shared/model/mudurluk.model';
import { MudurlukService } from 'app/entities/mudurluk';

@Component({
    selector: 'jhi-ekip-update',
    templateUrl: './ekip-update.component.html'
})
export class EkipUpdateComponent implements OnInit {
    ekip: IEkip;
    isSaving: boolean;

    personels: IPersonel[];

    mudurluks: IMudurluk[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected ekipService: EkipService,
        protected personelService: PersonelService,
        protected mudurlukService: MudurlukService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ekip }) => {
            this.ekip = ekip;
        });
        this.personelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonel[]>) => response.body)
            )
            .subscribe((res: IPersonel[]) => (this.personels = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackPersonelById(index: number, item: IPersonel) {
        return item.id;
    }

    trackMudurlukById(index: number, item: IMudurluk) {
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
