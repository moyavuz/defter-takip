import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMarka } from 'app/shared/model/marka.model';
import { AccountService } from 'app/core';
import { MarkaService } from './marka.service';

@Component({
    selector: 'jhi-marka',
    templateUrl: './marka.component.html'
})
export class MarkaComponent implements OnInit, OnDestroy {
    markas: IMarka[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected markaService: MarkaService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.markaService
            .query()
            .pipe(
                filter((res: HttpResponse<IMarka[]>) => res.ok),
                map((res: HttpResponse<IMarka[]>) => res.body)
            )
            .subscribe(
                (res: IMarka[]) => {
                    this.markas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMarkas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMarka) {
        return item.id;
    }

    registerChangeInMarkas() {
        this.eventSubscriber = this.eventManager.subscribe('markaListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
