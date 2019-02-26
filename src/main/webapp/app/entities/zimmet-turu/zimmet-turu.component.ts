import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IZimmetTuru } from 'app/shared/model/zimmet-turu.model';
import { AccountService } from 'app/core';
import { ZimmetTuruService } from './zimmet-turu.service';

@Component({
    selector: 'jhi-zimmet-turu',
    templateUrl: './zimmet-turu.component.html'
})
export class ZimmetTuruComponent implements OnInit, OnDestroy {
    zimmetTurus: IZimmetTuru[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected zimmetTuruService: ZimmetTuruService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.zimmetTuruService
            .query()
            .pipe(
                filter((res: HttpResponse<IZimmetTuru[]>) => res.ok),
                map((res: HttpResponse<IZimmetTuru[]>) => res.body)
            )
            .subscribe(
                (res: IZimmetTuru[]) => {
                    this.zimmetTurus = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInZimmetTurus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IZimmetTuru) {
        return item.id;
    }

    registerChangeInZimmetTurus() {
        this.eventSubscriber = this.eventManager.subscribe('zimmetTuruListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
