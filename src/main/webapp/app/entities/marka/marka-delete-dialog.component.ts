import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMarka } from 'app/shared/model/marka.model';
import { MarkaService } from './marka.service';

@Component({
    selector: 'jhi-marka-delete-dialog',
    templateUrl: './marka-delete-dialog.component.html'
})
export class MarkaDeleteDialogComponent {
    marka: IMarka;

    constructor(protected markaService: MarkaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.markaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'markaListModification',
                content: 'Deleted an marka'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-marka-delete-popup',
    template: ''
})
export class MarkaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ marka }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MarkaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.marka = marka;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/marka', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/marka', { outlets: { popup: null } }]);
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
