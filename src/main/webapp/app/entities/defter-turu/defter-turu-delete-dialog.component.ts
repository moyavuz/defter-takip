import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDefterTuru } from 'app/shared/model/defter-turu.model';
import { DefterTuruService } from './defter-turu.service';

@Component({
    selector: 'jhi-defter-turu-delete-dialog',
    templateUrl: './defter-turu-delete-dialog.component.html'
})
export class DefterTuruDeleteDialogComponent {
    defterTuru: IDefterTuru;

    constructor(
        protected defterTuruService: DefterTuruService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.defterTuruService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'defterTuruListModification',
                content: 'Deleted an defterTuru'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-defter-turu-delete-popup',
    template: ''
})
export class DefterTuruDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ defterTuru }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DefterTuruDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.defterTuru = defterTuru;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/defter-turu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/defter-turu', { outlets: { popup: null } }]);
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
