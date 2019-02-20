import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPersonelIzin } from 'app/shared/model/personel-izin.model';
import { AccountService } from 'app/core';
import { PersonelIzinService } from './personel-izin.service';

@Component({
    selector: 'jhi-personel-izin',
    templateUrl: './personel-izin.component.html'
})
export class PersonelIzinComponent implements OnInit, OnDestroy {
    personelIzins: IPersonelIzin[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected personelIzinService: PersonelIzinService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.personelIzinService
            .query()
            .pipe(
                filter((res: HttpResponse<IPersonelIzin[]>) => res.ok),
                map((res: HttpResponse<IPersonelIzin[]>) => res.body)
            )
            .subscribe(
                (res: IPersonelIzin[]) => {
                    this.personelIzins = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonelIzins();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPersonelIzin) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInPersonelIzins() {
        this.eventSubscriber = this.eventManager.subscribe('personelIzinListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
