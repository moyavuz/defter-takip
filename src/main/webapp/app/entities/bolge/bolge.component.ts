import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBolge } from 'app/shared/model/bolge.model';
import { AccountService } from 'app/core';
import { BolgeService } from './bolge.service';

@Component({
    selector: 'jhi-bolge',
    templateUrl: './bolge.component.html'
})
export class BolgeComponent implements OnInit, OnDestroy {
    bolges: IBolge[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected bolgeService: BolgeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.bolgeService
            .query()
            .pipe(
                filter((res: HttpResponse<IBolge[]>) => res.ok),
                map((res: HttpResponse<IBolge[]>) => res.body)
            )
            .subscribe(
                (res: IBolge[]) => {
                    this.bolges = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBolges();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBolge) {
        return item.id;
    }

    registerChangeInBolges() {
        this.eventSubscriber = this.eventManager.subscribe('bolgeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
