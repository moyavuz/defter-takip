import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHakedisTuru } from 'app/shared/model/hakedis-turu.model';
import { AccountService } from 'app/core';
import { HakedisTuruService } from './hakedis-turu.service';

@Component({
    selector: 'jhi-hakedis-turu',
    templateUrl: './hakedis-turu.component.html'
})
export class HakedisTuruComponent implements OnInit, OnDestroy {
    hakedisTurus: IHakedisTuru[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected hakedisTuruService: HakedisTuruService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.hakedisTuruService
            .query()
            .pipe(
                filter((res: HttpResponse<IHakedisTuru[]>) => res.ok),
                map((res: HttpResponse<IHakedisTuru[]>) => res.body)
            )
            .subscribe(
                (res: IHakedisTuru[]) => {
                    this.hakedisTurus = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHakedisTurus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHakedisTuru) {
        return item.id;
    }

    registerChangeInHakedisTurus() {
        this.eventSubscriber = this.eventManager.subscribe('hakedisTuruListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
