import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnvan } from 'app/shared/model/unvan.model';
import { UnvanService } from './unvan.service';

@Component({
    selector: 'jhi-unvan-delete-dialog',
    templateUrl: './unvan-delete-dialog.component.html'
})
export class UnvanDeleteDialogComponent {
    unvan: IUnvan;

    constructor(protected unvanService: UnvanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.unvanService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'unvanListModification',
                content: 'Deleted an unvan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-unvan-delete-popup',
    template: ''
})
export class UnvanDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ unvan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UnvanDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.unvan = unvan;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/unvan', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/unvan', { outlets: { popup: null } }]);
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
