import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDepo } from 'app/shared/model/depo.model';
import { DepoService } from './depo.service';
import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from 'app/entities/personel';

@Component({
    selector: 'jhi-depo-update',
    templateUrl: './depo-update.component.html'
})
export class DepoUpdateComponent implements OnInit {
    depo: IDepo;
    isSaving: boolean;

    personels: IPersonel[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected depoService: DepoService,
        protected personelService: PersonelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ depo }) => {
            this.depo = depo;
        });
        this.personelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonel[]>) => response.body)
            )
            .subscribe((res: IPersonel[]) => (this.personels = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.depo.id !== undefined) {
            this.subscribeToSaveResponse(this.depoService.update(this.depo));
        } else {
            this.subscribeToSaveResponse(this.depoService.create(this.depo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepo>>) {
        result.subscribe((res: HttpResponse<IDepo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
