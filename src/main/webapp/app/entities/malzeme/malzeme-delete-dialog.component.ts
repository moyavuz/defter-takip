import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMalzeme } from 'app/shared/model/malzeme.model';
import { MalzemeService } from './malzeme.service';

@Component({
    selector: 'jhi-malzeme-delete-dialog',
    templateUrl: './malzeme-delete-dialog.component.html'
})
export class MalzemeDeleteDialogComponent {
    malzeme: IMalzeme;

    constructor(protected malzemeService: MalzemeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.malzemeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'malzemeListModification',
                content: 'Deleted an malzeme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-malzeme-delete-popup',
    template: ''
})
export class MalzemeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ malzeme }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MalzemeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.malzeme = malzeme;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/malzeme', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/malzeme', { outlets: { popup: null } }]);
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
