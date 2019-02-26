import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDepo } from 'app/shared/model/depo.model';
import { AccountService } from 'app/core';
import { DepoService } from './depo.service';

@Component({
    selector: 'jhi-depo',
    templateUrl: './depo.component.html'
})
export class DepoComponent implements OnInit, OnDestroy {
    depos: IDepo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected depoService: DepoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.depoService
            .query()
            .pipe(
                filter((res: HttpResponse<IDepo[]>) => res.ok),
                map((res: HttpResponse<IDepo[]>) => res.body)
            )
            .subscribe(
                (res: IDepo[]) => {
                    this.depos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDepos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDepo) {
        return item.id;
    }

    registerChangeInDepos() {
        this.eventSubscriber = this.eventManager.subscribe('depoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
