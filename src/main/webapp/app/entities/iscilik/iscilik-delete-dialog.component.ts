import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIscilik } from 'app/shared/model/iscilik.model';
import { IscilikService } from './iscilik.service';

@Component({
    selector: 'jhi-iscilik-delete-dialog',
    templateUrl: './iscilik-delete-dialog.component.html'
})
export class IscilikDeleteDialogComponent {
    iscilik: IIscilik;

    constructor(protected iscilikService: IscilikService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.iscilikService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'iscilikListModification',
                content: 'Deleted an iscilik'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-iscilik-delete-popup',
    template: ''
})
export class IscilikDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ iscilik }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IscilikDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.iscilik = iscilik;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/iscilik', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/iscilik', { outlets: { popup: null } }]);
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
