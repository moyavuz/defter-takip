import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IArac } from 'app/shared/model/arac.model';
import { AccountService } from 'app/core';
import { AracService } from './arac.service';

@Component({
    selector: 'jhi-arac',
    templateUrl: './arac.component.html'
})
export class AracComponent implements OnInit, OnDestroy {
    aracs: IArac[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected aracService: AracService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.aracService
            .query()
            .pipe(
                filter((res: HttpResponse<IArac[]>) => res.ok),
                map((res: HttpResponse<IArac[]>) => res.body)
            )
            .subscribe(
                (res: IArac[]) => {
                    this.aracs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAracs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IArac) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInAracs() {
        this.eventSubscriber = this.eventManager.subscribe('aracListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
