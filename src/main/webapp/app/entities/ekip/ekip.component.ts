import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEkip } from 'app/shared/model/ekip.model';
import { AccountService } from 'app/core';
import { EkipService } from './ekip.service';

@Component({
    selector: 'jhi-ekip',
    templateUrl: './ekip.component.html'
})
export class EkipComponent implements OnInit, OnDestroy {
    ekips: IEkip[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected ekipService: EkipService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.ekipService
            .query()
            .pipe(
                filter((res: HttpResponse<IEkip[]>) => res.ok),
                map((res: HttpResponse<IEkip[]>) => res.body)
            )
            .subscribe(
                (res: IEkip[]) => {
                    this.ekips = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEkips();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEkip) {
        return item.id;
    }

    registerChangeInEkips() {
        this.eventSubscriber = this.eventManager.subscribe('ekipListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
