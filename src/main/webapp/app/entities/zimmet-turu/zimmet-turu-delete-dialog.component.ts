import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IZimmetTuru } from 'app/shared/model/zimmet-turu.model';
import { ZimmetTuruService } from './zimmet-turu.service';

@Component({
    selector: 'jhi-zimmet-turu-delete-dialog',
    templateUrl: './zimmet-turu-delete-dialog.component.html'
})
export class ZimmetTuruDeleteDialogComponent {
    zimmetTuru: IZimmetTuru;

    constructor(
        protected zimmetTuruService: ZimmetTuruService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.zimmetTuruService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'zimmetTuruListModification',
                content: 'Deleted an zimmetTuru'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-zimmet-turu-delete-popup',
    template: ''
})
export class ZimmetTuruDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ zimmetTuru }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ZimmetTuruDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.zimmetTuru = zimmetTuru;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/zimmet-turu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/zimmet-turu', { outlets: { popup: null } }]);
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
