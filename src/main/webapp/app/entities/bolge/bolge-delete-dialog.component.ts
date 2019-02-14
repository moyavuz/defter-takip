import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBolge } from 'app/shared/model/bolge.model';
import { BolgeService } from './bolge.service';

@Component({
    selector: 'jhi-bolge-delete-dialog',
    templateUrl: './bolge-delete-dialog.component.html'
})
export class BolgeDeleteDialogComponent {
    bolge: IBolge;

    constructor(protected bolgeService: BolgeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bolgeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bolgeListModification',
                content: 'Deleted an bolge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bolge-delete-popup',
    template: ''
})
export class BolgeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bolge }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BolgeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bolge = bolge;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/bolge', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/bolge', { outlets: { popup: null } }]);
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
