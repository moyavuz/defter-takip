import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEskalasyon } from 'app/shared/model/eskalasyon.model';
import { AccountService } from 'app/core';
import { EskalasyonService } from './eskalasyon.service';

@Component({
    selector: 'jhi-eskalasyon',
    templateUrl: './eskalasyon.component.html'
})
export class EskalasyonComponent implements OnInit, OnDestroy {
    eskalasyons: IEskalasyon[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected eskalasyonService: EskalasyonService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.eskalasyonService
            .query()
            .pipe(
                filter((res: HttpResponse<IEskalasyon[]>) => res.ok),
                map((res: HttpResponse<IEskalasyon[]>) => res.body)
            )
            .subscribe(
                (res: IEskalasyon[]) => {
                    this.eskalasyons = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEskalasyons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEskalasyon) {
        return item.id;
    }

    registerChangeInEskalasyons() {
        this.eventSubscriber = this.eventManager.subscribe('eskalasyonListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
