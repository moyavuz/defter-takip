import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMalzemeTakip } from 'app/shared/model/malzeme-takip.model';
import { MalzemeTakipService } from './malzeme-takip.service';

@Component({
    selector: 'jhi-malzeme-takip-delete-dialog',
    templateUrl: './malzeme-takip-delete-dialog.component.html'
})
export class MalzemeTakipDeleteDialogComponent {
    malzemeTakip: IMalzemeTakip;

    constructor(
        protected malzemeTakipService: MalzemeTakipService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.malzemeTakipService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'malzemeTakipListModification',
                content: 'Deleted an malzemeTakip'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-malzeme-takip-delete-popup',
    template: ''
})
export class MalzemeTakipDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ malzemeTakip }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MalzemeTakipDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.malzemeTakip = malzemeTakip;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/malzeme-takip', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/malzeme-takip', { outlets: { popup: null } }]);
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
