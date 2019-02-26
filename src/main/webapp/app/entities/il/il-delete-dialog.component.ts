import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIl } from 'app/shared/model/il.model';
import { IlService } from './il.service';

@Component({
    selector: 'jhi-il-delete-dialog',
    templateUrl: './il-delete-dialog.component.html'
})
export class IlDeleteDialogComponent {
    il: IIl;

    constructor(protected ilService: IlService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ilService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ilListModification',
                content: 'Deleted an il'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-il-delete-popup',
    template: ''
})
export class IlDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ il }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IlDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.il = il;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/il', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/il', { outlets: { popup: null } }]);
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
