import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBirim } from 'app/shared/model/birim.model';
import { BirimService } from './birim.service';

@Component({
    selector: 'jhi-birim-delete-dialog',
    templateUrl: './birim-delete-dialog.component.html'
})
export class BirimDeleteDialogComponent {
    birim: IBirim;

    constructor(protected birimService: BirimService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.birimService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'birimListModification',
                content: 'Deleted an birim'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-birim-delete-popup',
    template: ''
})
export class BirimDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ birim }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BirimDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.birim = birim;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/birim', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/birim', { outlets: { popup: null } }]);
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
