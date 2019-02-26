import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArac } from 'app/shared/model/arac.model';
import { AracService } from './arac.service';

@Component({
    selector: 'jhi-arac-delete-dialog',
    templateUrl: './arac-delete-dialog.component.html'
})
export class AracDeleteDialogComponent {
    arac: IArac;

    constructor(protected aracService: AracService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aracService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'aracListModification',
                content: 'Deleted an arac'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-arac-delete-popup',
    template: ''
})
export class AracDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ arac }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AracDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.arac = arac;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/arac', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/arac', { outlets: { popup: null } }]);
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
