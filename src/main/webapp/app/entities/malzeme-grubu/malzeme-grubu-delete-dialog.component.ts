import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';
import { MalzemeGrubuService } from './malzeme-grubu.service';

@Component({
    selector: 'jhi-malzeme-grubu-delete-dialog',
    templateUrl: './malzeme-grubu-delete-dialog.component.html'
})
export class MalzemeGrubuDeleteDialogComponent {
    malzemeGrubu: IMalzemeGrubu;

    constructor(
        protected malzemeGrubuService: MalzemeGrubuService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.malzemeGrubuService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'malzemeGrubuListModification',
                content: 'Deleted an malzemeGrubu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-malzeme-grubu-delete-popup',
    template: ''
})
export class MalzemeGrubuDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ malzemeGrubu }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MalzemeGrubuDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.malzemeGrubu = malzemeGrubu;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/malzeme-grubu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/malzeme-grubu', { outlets: { popup: null } }]);
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
