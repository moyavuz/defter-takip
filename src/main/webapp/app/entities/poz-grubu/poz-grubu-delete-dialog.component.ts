import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPozGrubu } from 'app/shared/model/poz-grubu.model';
import { PozGrubuService } from './poz-grubu.service';

@Component({
    selector: 'jhi-poz-grubu-delete-dialog',
    templateUrl: './poz-grubu-delete-dialog.component.html'
})
export class PozGrubuDeleteDialogComponent {
    pozGrubu: IPozGrubu;

    constructor(protected pozGrubuService: PozGrubuService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pozGrubuService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pozGrubuListModification',
                content: 'Deleted an pozGrubu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-poz-grubu-delete-popup',
    template: ''
})
export class PozGrubuDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pozGrubu }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PozGrubuDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pozGrubu = pozGrubu;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/poz-grubu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/poz-grubu', { outlets: { popup: null } }]);
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
