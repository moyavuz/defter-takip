import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDepartman } from 'app/shared/model/departman.model';
import { AccountService } from 'app/core';
import { DepartmanService } from './departman.service';

@Component({
    selector: 'jhi-departman',
    templateUrl: './departman.component.html'
})
export class DepartmanComponent implements OnInit, OnDestroy {
    departmen: IDepartman[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected departmanService: DepartmanService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.departmanService
            .query()
            .pipe(
                filter((res: HttpResponse<IDepartman[]>) => res.ok),
                map((res: HttpResponse<IDepartman[]>) => res.body)
            )
            .subscribe(
                (res: IDepartman[]) => {
                    this.departmen = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDepartmen();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDepartman) {
        return item.id;
    }

    registerChangeInDepartmen() {
        this.eventSubscriber = this.eventManager.subscribe('departmanListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
