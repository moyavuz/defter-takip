import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBolge } from 'app/shared/model/bolge.model';
import { BolgeService } from './bolge.service';
import { ICalisan } from 'app/shared/model/calisan.model';
import { CalisanService } from 'app/entities/calisan';

@Component({
    selector: 'jhi-bolge-update',
    templateUrl: './bolge-update.component.html'
})
export class BolgeUpdateComponent implements OnInit {
    bolge: IBolge;
    isSaving: boolean;

    sorumlus: ICalisan[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bolgeService: BolgeService,
        protected calisanService: CalisanService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bolge }) => {
            this.bolge = bolge;
        });
        this.calisanService
            .query({ filter: 'bolge-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICalisan[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICalisan[]>) => response.body)
            )
            .subscribe(
                (res: ICalisan[]) => {
                    if (!this.bolge.sorumlu || !this.bolge.sorumlu.id) {
                        this.sorumlus = res;
                    } else {
                        this.calisanService
                            .find(this.bolge.sorumlu.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICalisan>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICalisan>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICalisan) => (this.sorumlus = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bolge.id !== undefined) {
            this.subscribeToSaveResponse(this.bolgeService.update(this.bolge));
        } else {
            this.subscribeToSaveResponse(this.bolgeService.create(this.bolge));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBolge>>) {
        result.subscribe((res: HttpResponse<IBolge>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
