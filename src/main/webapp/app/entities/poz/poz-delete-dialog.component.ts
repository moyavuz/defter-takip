import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoz } from 'app/shared/model/poz.model';
import { PozService } from './poz.service';

@Component({
    selector: 'jhi-poz-delete-dialog',
    templateUrl: './poz-delete-dialog.component.html'
})
export class PozDeleteDialogComponent {
    poz: IPoz;

    constructor(protected pozService: PozService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pozService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pozListModification',
                content: 'Deleted an poz'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-poz-delete-popup',
    template: ''
})
export class PozDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ poz }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PozDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.poz = poz;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/poz', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/poz', { outlets: { popup: null } }]);
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
