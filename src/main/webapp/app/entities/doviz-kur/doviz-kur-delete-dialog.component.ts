import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDovizKur } from 'app/shared/model/doviz-kur.model';
import { DovizKurService } from './doviz-kur.service';

@Component({
    selector: 'jhi-doviz-kur-delete-dialog',
    templateUrl: './doviz-kur-delete-dialog.component.html'
})
export class DovizKurDeleteDialogComponent {
    dovizKur: IDovizKur;

    constructor(protected dovizKurService: DovizKurService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dovizKurService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dovizKurListModification',
                content: 'Deleted an dovizKur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-doviz-kur-delete-popup',
    template: ''
})
export class DovizKurDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dovizKur }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DovizKurDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.dovizKur = dovizKur;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/doviz-kur', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/doviz-kur', { outlets: { popup: null } }]);
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
