import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';
import { AccountService } from 'app/core';
import { MalzemeGrubuService } from './malzeme-grubu.service';

@Component({
    selector: 'jhi-malzeme-grubu',
    templateUrl: './malzeme-grubu.component.html'
})
export class MalzemeGrubuComponent implements OnInit, OnDestroy {
    malzemeGrubus: IMalzemeGrubu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected malzemeGrubuService: MalzemeGrubuService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.malzemeGrubuService
            .query()
            .pipe(
                filter((res: HttpResponse<IMalzemeGrubu[]>) => res.ok),
                map((res: HttpResponse<IMalzemeGrubu[]>) => res.body)
            )
            .subscribe(
                (res: IMalzemeGrubu[]) => {
                    this.malzemeGrubus = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMalzemeGrubus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMalzemeGrubu) {
        return item.id;
    }

    registerChangeInMalzemeGrubus() {
        this.eventSubscriber = this.eventManager.subscribe('malzemeGrubuListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
