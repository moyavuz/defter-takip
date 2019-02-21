import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPozGrubu } from 'app/shared/model/poz-grubu.model';
import { AccountService } from 'app/core';
import { PozGrubuService } from './poz-grubu.service';

@Component({
    selector: 'jhi-poz-grubu',
    templateUrl: './poz-grubu.component.html'
})
export class PozGrubuComponent implements OnInit, OnDestroy {
    pozGrubus: IPozGrubu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected pozGrubuService: PozGrubuService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.pozGrubuService
            .query()
            .pipe(
                filter((res: HttpResponse<IPozGrubu[]>) => res.ok),
                map((res: HttpResponse<IPozGrubu[]>) => res.body)
            )
            .subscribe(
                (res: IPozGrubu[]) => {
                    this.pozGrubus = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPozGrubus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPozGrubu) {
        return item.id;
    }

    registerChangeInPozGrubus() {
        this.eventSubscriber = this.eventManager.subscribe('pozGrubuListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
