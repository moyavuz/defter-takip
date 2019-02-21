import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPersonelOdeme } from 'app/shared/model/personel-odeme.model';
import { AccountService } from 'app/core';
import { PersonelOdemeService } from './personel-odeme.service';

@Component({
    selector: 'jhi-personel-odeme',
    templateUrl: './personel-odeme.component.html'
})
export class PersonelOdemeComponent implements OnInit, OnDestroy {
    personelOdemes: IPersonelOdeme[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected personelOdemeService: PersonelOdemeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.personelOdemeService
            .query()
            .pipe(
                filter((res: HttpResponse<IPersonelOdeme[]>) => res.ok),
                map((res: HttpResponse<IPersonelOdeme[]>) => res.body)
            )
            .subscribe(
                (res: IPersonelOdeme[]) => {
                    this.personelOdemes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonelOdemes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPersonelOdeme) {
        return item.id;
    }

    registerChangeInPersonelOdemes() {
        this.eventSubscriber = this.eventManager.subscribe('personelOdemeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
