import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjeTuru } from 'app/shared/model/proje-turu.model';
import { ProjeTuruService } from './proje-turu.service';

@Component({
    selector: 'jhi-proje-turu-delete-dialog',
    templateUrl: './proje-turu-delete-dialog.component.html'
})
export class ProjeTuruDeleteDialogComponent {
    projeTuru: IProjeTuru;

    constructor(
        protected projeTuruService: ProjeTuruService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projeTuruService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'projeTuruListModification',
                content: 'Deleted an projeTuru'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-proje-turu-delete-popup',
    template: ''
})
export class ProjeTuruDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ projeTuru }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProjeTuruDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.projeTuru = projeTuru;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/proje-turu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/proje-turu', { outlets: { popup: null } }]);
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
