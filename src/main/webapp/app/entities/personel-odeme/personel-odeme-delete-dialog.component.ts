import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonelOdeme } from 'app/shared/model/personel-odeme.model';
import { PersonelOdemeService } from './personel-odeme.service';

@Component({
    selector: 'jhi-personel-odeme-delete-dialog',
    templateUrl: './personel-odeme-delete-dialog.component.html'
})
export class PersonelOdemeDeleteDialogComponent {
    personelOdeme: IPersonelOdeme;

    constructor(
        protected personelOdemeService: PersonelOdemeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personelOdemeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personelOdemeListModification',
                content: 'Deleted an personelOdeme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personel-odeme-delete-popup',
    template: ''
})
export class PersonelOdemeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personelOdeme }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PersonelOdemeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.personelOdeme = personelOdeme;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/personel-odeme', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/personel-odeme', { outlets: { popup: null } }]);
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
