import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjeTuru } from 'app/shared/model/proje-turu.model';
import { AccountService } from 'app/core';
import { ProjeTuruService } from './proje-turu.service';

@Component({
    selector: 'jhi-proje-turu',
    templateUrl: './proje-turu.component.html'
})
export class ProjeTuruComponent implements OnInit, OnDestroy {
    projeTurus: IProjeTuru[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected projeTuruService: ProjeTuruService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.projeTuruService
            .query()
            .pipe(
                filter((res: HttpResponse<IProjeTuru[]>) => res.ok),
                map((res: HttpResponse<IProjeTuru[]>) => res.body)
            )
            .subscribe(
                (res: IProjeTuru[]) => {
                    this.projeTurus = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProjeTurus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProjeTuru) {
        return item.id;
    }

    registerChangeInProjeTurus() {
        this.eventSubscriber = this.eventManager.subscribe('projeTuruListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
