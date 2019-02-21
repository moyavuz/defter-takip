import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISantral } from 'app/shared/model/santral.model';
import { AccountService } from 'app/core';
import { SantralService } from './santral.service';

@Component({
    selector: 'jhi-santral',
    templateUrl: './santral.component.html'
})
export class SantralComponent implements OnInit, OnDestroy {
    santrals: ISantral[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected santralService: SantralService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.santralService
            .query()
            .pipe(
                filter((res: HttpResponse<ISantral[]>) => res.ok),
                map((res: HttpResponse<ISantral[]>) => res.body)
            )
            .subscribe(
                (res: ISantral[]) => {
                    this.santrals = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSantrals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISantral) {
        return item.id;
    }

    registerChangeInSantrals() {
        this.eventSubscriber = this.eventManager.subscribe('santralListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
