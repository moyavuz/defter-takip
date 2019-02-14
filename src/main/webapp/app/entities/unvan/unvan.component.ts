import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUnvan } from 'app/shared/model/unvan.model';
import { AccountService } from 'app/core';
import { UnvanService } from './unvan.service';

@Component({
    selector: 'jhi-unvan',
    templateUrl: './unvan.component.html'
})
export class UnvanComponent implements OnInit, OnDestroy {
    unvans: IUnvan[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected unvanService: UnvanService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.unvanService
            .query()
            .pipe(
                filter((res: HttpResponse<IUnvan[]>) => res.ok),
                map((res: HttpResponse<IUnvan[]>) => res.body)
            )
            .subscribe(
                (res: IUnvan[]) => {
                    this.unvans = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUnvans();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUnvan) {
        return item.id;
    }

    registerChangeInUnvans() {
        this.eventSubscriber = this.eventManager.subscribe('unvanListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
