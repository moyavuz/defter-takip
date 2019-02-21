import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';
import { AccountService } from 'app/core';
import { EskalasyonTuruService } from './eskalasyon-turu.service';

@Component({
    selector: 'jhi-eskalasyon-turu',
    templateUrl: './eskalasyon-turu.component.html'
})
export class EskalasyonTuruComponent implements OnInit, OnDestroy {
    eskalasyonTurus: IEskalasyonTuru[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected eskalasyonTuruService: EskalasyonTuruService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.eskalasyonTuruService
            .query()
            .pipe(
                filter((res: HttpResponse<IEskalasyonTuru[]>) => res.ok),
                map((res: HttpResponse<IEskalasyonTuru[]>) => res.body)
            )
            .subscribe(
                (res: IEskalasyonTuru[]) => {
                    this.eskalasyonTurus = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEskalasyonTurus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEskalasyonTuru) {
        return item.id;
    }

    registerChangeInEskalasyonTurus() {
        this.eventSubscriber = this.eventManager.subscribe('eskalasyonTuruListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
