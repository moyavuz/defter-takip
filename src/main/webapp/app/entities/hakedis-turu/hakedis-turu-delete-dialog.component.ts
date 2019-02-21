import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHakedisTuru } from 'app/shared/model/hakedis-turu.model';
import { HakedisTuruService } from './hakedis-turu.service';

@Component({
    selector: 'jhi-hakedis-turu-delete-dialog',
    templateUrl: './hakedis-turu-delete-dialog.component.html'
})
export class HakedisTuruDeleteDialogComponent {
    hakedisTuru: IHakedisTuru;

    constructor(
        protected hakedisTuruService: HakedisTuruService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hakedisTuruService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'hakedisTuruListModification',
                content: 'Deleted an hakedisTuru'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hakedis-turu-delete-popup',
    template: ''
})
export class HakedisTuruDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hakedisTuru }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HakedisTuruDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.hakedisTuru = hakedisTuru;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/hakedis-turu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/hakedis-turu', { outlets: { popup: null } }]);
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
