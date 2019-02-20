import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepartman } from 'app/shared/model/departman.model';
import { DepartmanService } from './departman.service';

@Component({
    selector: 'jhi-departman-delete-dialog',
    templateUrl: './departman-delete-dialog.component.html'
})
export class DepartmanDeleteDialogComponent {
    departman: IDepartman;

    constructor(
        protected departmanService: DepartmanService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.departmanService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'departmanListModification',
                content: 'Deleted an departman'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-departman-delete-popup',
    template: ''
})
export class DepartmanDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ departman }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DepartmanDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.departman = departman;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/departman', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/departman', { outlets: { popup: null } }]);
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
