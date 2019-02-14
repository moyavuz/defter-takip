import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';
import { AccountService } from 'app/core';
import { MalzemeTakipService } from './malzeme-takip.service';

@Component({
    selector: 'jhi-malzeme-takip',
    templateUrl: './malzeme-takip.component.html'
})
export class MalzemeTakipComponent implements OnInit, OnDestroy {
    malzemeTakips: IMalzemeTakip[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected malzemeTakipService: MalzemeTakipService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.malzemeTakipService
            .query()
            .pipe(
                filter((res: HttpResponse<IMalzemeTakip[]>) => res.ok),
                map((res: HttpResponse<IMalzemeTakip[]>) => res.body)
            )
            .subscribe(
                (res: IMalzemeTakip[]) => {
                    this.malzemeTakips = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMalzemeTakips();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMalzemeTakip) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInMalzemeTakips() {
        this.eventSubscriber = this.eventManager.subscribe('malzemeTakipListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
