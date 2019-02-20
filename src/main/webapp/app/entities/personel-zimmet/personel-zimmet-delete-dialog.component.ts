import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonelZimmet } from 'app/shared/model/personel-zimmet.model';
import { PersonelZimmetService } from './personel-zimmet.service';

@Component({
    selector: 'jhi-personel-zimmet-delete-dialog',
    templateUrl: './personel-zimmet-delete-dialog.component.html'
})
export class PersonelZimmetDeleteDialogComponent {
    personelZimmet: IPersonelZimmet;

    constructor(
        protected personelZimmetService: PersonelZimmetService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personelZimmetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personelZimmetListModification',
                content: 'Deleted an personelZimmet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personel-zimmet-delete-popup',
    template: ''
})
export class PersonelZimmetDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personelZimmet }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PersonelZimmetDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.personelZimmet = personelZimmet;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/personel-zimmet', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/personel-zimmet', { outlets: { popup: null } }]);
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
