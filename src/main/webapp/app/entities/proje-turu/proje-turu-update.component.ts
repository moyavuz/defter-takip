import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProjeTuru } from 'app/shared/model/proje-turu.model';
import { ProjeTuruService } from './proje-turu.service';
import { IProje } from 'app/shared/model/proje.model';
import { ProjeService } from 'app/entities/proje';

@Component({
    selector: 'jhi-proje-turu-update',
    templateUrl: './proje-turu-update.component.html'
})
export class ProjeTuruUpdateComponent implements OnInit {
    projeTuru: IProjeTuru;
    isSaving: boolean;

    projes: IProje[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected projeTuruService: ProjeTuruService,
        protected projeService: ProjeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ projeTuru }) => {
            this.projeTuru = projeTuru;
        });
        this.projeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProje[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProje[]>) => response.body)
            )
            .subscribe((res: IProje[]) => (this.projes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.projeTuru.id !== undefined) {
            this.subscribeToSaveResponse(this.projeTuruService.update(this.projeTuru));
        } else {
            this.subscribeToSaveResponse(this.projeTuruService.create(this.projeTuru));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjeTuru>>) {
        result.subscribe((res: HttpResponse<IProjeTuru>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProjeById(index: number, item: IProje) {
        return item.id;
    }
}
