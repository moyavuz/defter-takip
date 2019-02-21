import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPersonelArac } from 'app/shared/model/personel-arac.model';
import { AccountService } from 'app/core';
import { PersonelAracService } from './personel-arac.service';

@Component({
    selector: 'jhi-personel-arac',
    templateUrl: './personel-arac.component.html'
})
export class PersonelAracComponent implements OnInit, OnDestroy {
    personelAracs: IPersonelArac[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected personelAracService: PersonelAracService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.personelAracService
            .query()
            .pipe(
                filter((res: HttpResponse<IPersonelArac[]>) => res.ok),
                map((res: HttpResponse<IPersonelArac[]>) => res.body)
            )
            .subscribe(
                (res: IPersonelArac[]) => {
                    this.personelAracs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonelAracs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPersonelArac) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInPersonelAracs() {
        this.eventSubscriber = this.eventManager.subscribe('personelAracListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
