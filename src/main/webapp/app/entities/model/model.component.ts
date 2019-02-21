import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IModel } from 'app/shared/model/model.model';
import { AccountService } from 'app/core';
import { ModelService } from './model.service';

@Component({
    selector: 'jhi-model',
    templateUrl: './model.component.html'
})
export class ModelComponent implements OnInit, OnDestroy {
    models: IModel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected modelService: ModelService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.modelService
            .query()
            .pipe(
                filter((res: HttpResponse<IModel[]>) => res.ok),
                map((res: HttpResponse<IModel[]>) => res.body)
            )
            .subscribe(
                (res: IModel[]) => {
                    this.models = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInModels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IModel) {
        return item.id;
    }

    registerChangeInModels() {
        this.eventSubscriber = this.eventManager.subscribe('modelListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
