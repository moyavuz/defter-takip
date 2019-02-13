import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMalzeme } from 'app/shared/model/malzeme.model';
import { AccountService } from 'app/core';
import { MalzemeService } from './malzeme.service';

@Component({
    selector: 'jhi-malzeme',
    templateUrl: './malzeme.component.html'
})
export class MalzemeComponent implements OnInit, OnDestroy {
    malzemes: IMalzeme[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected malzemeService: MalzemeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.malzemeService
            .query()
            .pipe(
                filter((res: HttpResponse<IMalzeme[]>) => res.ok),
                map((res: HttpResponse<IMalzeme[]>) => res.body)
            )
            .subscribe(
                (res: IMalzeme[]) => {
                    this.malzemes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMalzemes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMalzeme) {
        return item.id;
    }

    registerChangeInMalzemes() {
        this.eventSubscriber = this.eventManager.subscribe('malzemeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
