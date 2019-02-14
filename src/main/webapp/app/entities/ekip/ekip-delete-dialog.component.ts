import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEkip } from 'app/shared/model/ekip.model';
import { EkipService } from './ekip.service';

@Component({
    selector: 'jhi-ekip-delete-dialog',
    templateUrl: './ekip-delete-dialog.component.html'
})
export class EkipDeleteDialogComponent {
    ekip: IEkip;

    constructor(protected ekipService: EkipService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ekipService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ekipListModification',
                content: 'Deleted an ekip'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ekip-delete-popup',
    template: ''
})
export class EkipDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ekip }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EkipDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.ekip = ekip;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/ekip', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/ekip', { outlets: { popup: null } }]);
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
