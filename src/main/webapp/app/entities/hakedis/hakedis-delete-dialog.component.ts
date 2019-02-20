import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHakedis } from 'app/shared/model/hakedis.model';
import { HakedisService } from './hakedis.service';

@Component({
    selector: 'jhi-hakedis-delete-dialog',
    templateUrl: './hakedis-delete-dialog.component.html'
})
export class HakedisDeleteDialogComponent {
    hakedis: IHakedis;

    constructor(protected hakedisService: HakedisService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hakedisService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'hakedisListModification',
                content: 'Deleted an hakedis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hakedis-delete-popup',
    template: ''
})
export class HakedisDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hakedis }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HakedisDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.hakedis = hakedis;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/hakedis', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/hakedis', { outlets: { popup: null } }]);
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
