import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProje } from 'app/shared/model/proje.model';
import { ProjeService } from './proje.service';

@Component({
    selector: 'jhi-proje-delete-dialog',
    templateUrl: './proje-delete-dialog.component.html'
})
export class ProjeDeleteDialogComponent {
    proje: IProje;

    constructor(protected projeService: ProjeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'projeListModification',
                content: 'Deleted an proje'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-proje-delete-popup',
    template: ''
})
export class ProjeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ proje }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProjeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.proje = proje;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/proje', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/proje', { outlets: { popup: null } }]);
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
