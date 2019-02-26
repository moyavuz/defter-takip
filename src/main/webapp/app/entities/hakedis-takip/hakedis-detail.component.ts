import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils, JhiParseLinks, JhiEventManager } from 'ng-jhipster';

import { IHakedis } from 'app/shared/model/hakedis.model';
import { IHakedisDetay } from 'app/shared/model/hakedis-detay.model';
import { HakedisDetayService } from 'app/entities/hakedis-detay';
import { ITEMS_PER_PAGE } from 'app/shared';
import { AccountService } from 'app/core';

@Component({
    selector: 'jhi-hakedis-detail',
    templateUrl: './hakedis-detail.component.html'
})
export class HakedisDetailComponent implements OnInit {
    hakedis: IHakedis;

    hakedisDetays: IHakedisDetay[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected hakedisDetayService: HakedisDetayService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute
    ) {
        this.hakedisDetays = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hakedis }) => {
            this.hakedis = hakedis;
        });

        console.error('Hakedis Detay Listesi');
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHakedisDetays();
    }

    loadAll() {
        this.hakedisDetayService
            .findByHakedis(this.hakedis.id)
            .pipe(
                filter((mayBeOk: HttpResponse<IHakedisDetay[]>) => mayBeOk.ok),
                map((response: HttpResponse<IHakedisDetay[]>) => response.body)
            )
            .subscribe((res: IHakedisDetay[]) => (this.hakedisDetays = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    reset() {
        this.page = 0;
        this.hakedisDetays = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    trackId(index: number, item: IHakedisDetay) {
        return item.id;
    }

    registerChangeInHakedisDetays() {
        this.eventSubscriber = this.eventManager.subscribe('hakedisDetayListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateHakedisDetays(data: IHakedisDetay[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.hakedisDetays.push(data[i]);
        }
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
