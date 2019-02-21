import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMudurluk } from 'app/shared/model/mudurluk.model';
import { MudurlukService } from './mudurluk.service';
import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from 'app/entities/personel';
import { IIl } from 'app/shared/model/il.model';
import { IlService } from 'app/entities/il';

@Component({
    selector: 'jhi-mudurluk-update',
    templateUrl: './mudurluk-update.component.html'
})
export class MudurlukUpdateComponent implements OnInit {
    mudurluk: IMudurluk;
    isSaving: boolean;

    personels: IPersonel[];

    ils: IIl[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected mudurlukService: MudurlukService,
        protected personelService: PersonelService,
        protected ilService: IlService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mudurluk }) => {
            this.mudurluk = mudurluk;
        });
        this.personelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonel[]>) => response.body)
            )
            .subscribe((res: IPersonel[]) => (this.personels = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.ilService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIl[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIl[]>) => response.body)
            )
            .subscribe((res: IIl[]) => (this.ils = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mudurluk.id !== undefined) {
            this.subscribeToSaveResponse(this.mudurlukService.update(this.mudurluk));
        } else {
            this.subscribeToSaveResponse(this.mudurlukService.create(this.mudurluk));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMudurluk>>) {
        result.subscribe((res: HttpResponse<IMudurluk>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackIlById(index: number, item: IIl) {
        return item.id;
    }
}
