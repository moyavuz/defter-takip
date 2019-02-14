import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalisan } from 'app/shared/model/calisan.model';
import { CalisanService } from './calisan.service';

@Component({
    selector: 'jhi-calisan-delete-dialog',
    templateUrl: './calisan-delete-dialog.component.html'
})
export class CalisanDeleteDialogComponent {
    calisan: ICalisan;

    constructor(protected calisanService: CalisanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.calisanService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'calisanListModification',
                content: 'Deleted an calisan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-calisan-delete-popup',
    template: ''
})
export class CalisanDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calisan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CalisanDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.calisan = calisan;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/calisan', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/calisan', { outlets: { popup: null } }]);
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
