import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';
import { EskalasyonTuruService } from './eskalasyon-turu.service';

@Component({
    selector: 'jhi-eskalasyon-turu-delete-dialog',
    templateUrl: './eskalasyon-turu-delete-dialog.component.html'
})
export class EskalasyonTuruDeleteDialogComponent {
    eskalasyonTuru: IEskalasyonTuru;

    constructor(
        protected eskalasyonTuruService: EskalasyonTuruService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eskalasyonTuruService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'eskalasyonTuruListModification',
                content: 'Deleted an eskalasyonTuru'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-eskalasyon-turu-delete-popup',
    template: ''
})
export class EskalasyonTuruDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eskalasyonTuru }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EskalasyonTuruDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.eskalasyonTuru = eskalasyonTuru;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/eskalasyon-turu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/eskalasyon-turu', { outlets: { popup: null } }]);
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
