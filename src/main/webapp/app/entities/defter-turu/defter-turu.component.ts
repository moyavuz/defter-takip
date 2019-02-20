import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDefterTuru } from 'app/shared/model/defter-turu.model';
import { AccountService } from 'app/core';
import { DefterTuruService } from './defter-turu.service';

@Component({
    selector: 'jhi-defter-turu',
    templateUrl: './defter-turu.component.html'
})
export class DefterTuruComponent implements OnInit, OnDestroy {
    defterTurus: IDefterTuru[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected defterTuruService: DefterTuruService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.defterTuruService
            .query()
            .pipe(
                filter((res: HttpResponse<IDefterTuru[]>) => res.ok),
                map((res: HttpResponse<IDefterTuru[]>) => res.body)
            )
            .subscribe(
                (res: IDefterTuru[]) => {
                    this.defterTurus = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDefterTurus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDefterTuru) {
        return item.id;
    }

    registerChangeInDefterTurus() {
        this.eventSubscriber = this.eventManager.subscribe('defterTuruListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
