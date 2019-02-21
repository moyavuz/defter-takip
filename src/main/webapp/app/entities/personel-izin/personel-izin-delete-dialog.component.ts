import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonelIzin } from 'app/shared/model/personel-izin.model';
import { PersonelIzinService } from './personel-izin.service';

@Component({
    selector: 'jhi-personel-izin-delete-dialog',
    templateUrl: './personel-izin-delete-dialog.component.html'
})
export class PersonelIzinDeleteDialogComponent {
    personelIzin: IPersonelIzin;

    constructor(
        protected personelIzinService: PersonelIzinService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personelIzinService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personelIzinListModification',
                content: 'Deleted an personelIzin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personel-izin-delete-popup',
    template: ''
})
export class PersonelIzinDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personelIzin }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PersonelIzinDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.personelIzin = personelIzin;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/personel-izin', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/personel-izin', { outlets: { popup: null } }]);
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
