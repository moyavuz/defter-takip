import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStokTakip } from 'app/shared/model/stok-takip.model';
import { StokTakipService } from './stok-takip.service';

@Component({
    selector: 'jhi-stok-takip-delete-dialog',
    templateUrl: './stok-takip-delete-dialog.component.html'
})
export class StokTakipDeleteDialogComponent {
    stokTakip: IStokTakip;

    constructor(
        protected stokTakipService: StokTakipService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stokTakipService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stokTakipListModification',
                content: 'Deleted an stokTakip'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stok-takip-delete-popup',
    template: ''
})
export class StokTakipDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stokTakip }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StokTakipDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.stokTakip = stokTakip;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/stok-takip', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/stok-takip', { outlets: { popup: null } }]);
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
