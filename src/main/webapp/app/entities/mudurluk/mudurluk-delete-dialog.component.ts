import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMudurluk } from 'app/shared/model/mudurluk.model';
import { MudurlukService } from './mudurluk.service';

@Component({
    selector: 'jhi-mudurluk-delete-dialog',
    templateUrl: './mudurluk-delete-dialog.component.html'
})
export class MudurlukDeleteDialogComponent {
    mudurluk: IMudurluk;

    constructor(protected mudurlukService: MudurlukService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mudurlukService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mudurlukListModification',
                content: 'Deleted an mudurluk'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mudurluk-delete-popup',
    template: ''
})
export class MudurlukDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mudurluk }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MudurlukDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.mudurluk = mudurluk;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/mudurluk', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/mudurluk', { outlets: { popup: null } }]);
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
