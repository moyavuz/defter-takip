import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEskalasyon } from 'app/shared/model/eskalasyon.model';
import { EskalasyonService } from './eskalasyon.service';

@Component({
    selector: 'jhi-eskalasyon-delete-dialog',
    templateUrl: './eskalasyon-delete-dialog.component.html'
})
export class EskalasyonDeleteDialogComponent {
    eskalasyon: IEskalasyon;

    constructor(
        protected eskalasyonService: EskalasyonService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eskalasyonService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'eskalasyonListModification',
                content: 'Deleted an eskalasyon'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-eskalasyon-delete-popup',
    template: ''
})
export class EskalasyonDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eskalasyon }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EskalasyonDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.eskalasyon = eskalasyon;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/eskalasyon', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/eskalasyon', { outlets: { popup: null } }]);
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
