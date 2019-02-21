import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMudurluk } from 'app/shared/model/mudurluk.model';
import { AccountService } from 'app/core';
import { MudurlukService } from './mudurluk.service';

@Component({
    selector: 'jhi-mudurluk',
    templateUrl: './mudurluk.component.html'
})
export class MudurlukComponent implements OnInit, OnDestroy {
    mudurluks: IMudurluk[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected mudurlukService: MudurlukService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.mudurlukService
            .query()
            .pipe(
                filter((res: HttpResponse<IMudurluk[]>) => res.ok),
                map((res: HttpResponse<IMudurluk[]>) => res.body)
            )
            .subscribe(
                (res: IMudurluk[]) => {
                    this.mudurluks = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMudurluks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMudurluk) {
        return item.id;
    }

    registerChangeInMudurluks() {
        this.eventSubscriber = this.eventManager.subscribe('mudurlukListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
