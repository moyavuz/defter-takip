import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISantral } from 'app/shared/model/santral.model';
import { SantralService } from './santral.service';

@Component({
    selector: 'jhi-santral-delete-dialog',
    templateUrl: './santral-delete-dialog.component.html'
})
export class SantralDeleteDialogComponent {
    santral: ISantral;

    constructor(protected santralService: SantralService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.santralService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'santralListModification',
                content: 'Deleted an santral'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-santral-delete-popup',
    template: ''
})
export class SantralDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ santral }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SantralDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.santral = santral;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/santral', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/santral', { outlets: { popup: null } }]);
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
