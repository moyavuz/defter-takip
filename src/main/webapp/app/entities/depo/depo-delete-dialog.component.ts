import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepo } from 'app/shared/model/depo.model';
import { DepoService } from './depo.service';

@Component({
    selector: 'jhi-depo-delete-dialog',
    templateUrl: './depo-delete-dialog.component.html'
})
export class DepoDeleteDialogComponent {
    depo: IDepo;

    constructor(protected depoService: DepoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.depoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'depoListModification',
                content: 'Deleted an depo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-depo-delete-popup',
    template: ''
})
export class DepoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DepoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.depo = depo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/depo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/depo', { outlets: { popup: null } }]);
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
