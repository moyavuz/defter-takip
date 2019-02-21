import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIl } from 'app/shared/model/il.model';
import { AccountService } from 'app/core';
import { IlService } from './il.service';

@Component({
    selector: 'jhi-il',
    templateUrl: './il.component.html'
})
export class IlComponent implements OnInit, OnDestroy {
    ils: IIl[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected ilService: IlService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.ilService
            .query()
            .pipe(
                filter((res: HttpResponse<IIl[]>) => res.ok),
                map((res: HttpResponse<IIl[]>) => res.body)
            )
            .subscribe(
                (res: IIl[]) => {
                    this.ils = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIls();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIl) {
        return item.id;
    }

    registerChangeInIls() {
        this.eventSubscriber = this.eventManager.subscribe('ilListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
