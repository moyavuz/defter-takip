import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHakedisDetay } from 'app/shared/model/hakedis-detay.model';
import { HakedisDetayService } from './hakedis-detay.service';

@Component({
    selector: 'jhi-hakedis-detay-delete-dialog',
    templateUrl: './hakedis-detay-delete-dialog.component.html'
})
export class HakedisDetayDeleteDialogComponent {
    hakedisDetay: IHakedisDetay;

    constructor(
        protected hakedisDetayService: HakedisDetayService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hakedisDetayService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'hakedisDetayListModification',
                content: 'Deleted an hakedisDetay'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hakedis-detay-delete-popup',
    template: ''
})
export class HakedisDetayDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hakedisDetay }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HakedisDetayDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.hakedisDetay = hakedisDetay;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/hakedis-detay', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/hakedis-detay', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
