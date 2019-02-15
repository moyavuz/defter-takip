import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIscilikDetay } from 'app/shared/model/iscilik-detay.model';
import { IscilikDetayService } from './iscilik-detay.service';

@Component({
    selector: 'jhi-iscilik-detay-delete-dialog',
    templateUrl: './iscilik-detay-delete-dialog.component.html'
})
export class IscilikDetayDeleteDialogComponent {
    iscilikDetay: IIscilikDetay;

    constructor(
        protected iscilikDetayService: IscilikDetayService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.iscilikDetayService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'iscilikDetayListModification',
                content: 'Deleted an iscilikDetay'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-iscilik-detay-delete-popup',
    template: ''
})
export class IscilikDetayDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ iscilikDetay }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IscilikDetayDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.iscilikDetay = iscilikDetay;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/iscilik-detay', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/iscilik-detay', { outlets: { popup: null } }]);
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
