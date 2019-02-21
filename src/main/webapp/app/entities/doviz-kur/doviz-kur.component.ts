import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDovizKur } from 'app/shared/model/doviz-kur.model';
import { AccountService } from 'app/core';
import { DovizKurService } from './doviz-kur.service';

@Component({
    selector: 'jhi-doviz-kur',
    templateUrl: './doviz-kur.component.html'
})
export class DovizKurComponent implements OnInit, OnDestroy {
    dovizKurs: IDovizKur[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected dovizKurService: DovizKurService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.dovizKurService
            .query()
            .pipe(
                filter((res: HttpResponse<IDovizKur[]>) => res.ok),
                map((res: HttpResponse<IDovizKur[]>) => res.body)
            )
            .subscribe(
                (res: IDovizKur[]) => {
                    this.dovizKurs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDovizKurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDovizKur) {
        return item.id;
    }

    registerChangeInDovizKurs() {
        this.eventSubscriber = this.eventManager.subscribe('dovizKurListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
