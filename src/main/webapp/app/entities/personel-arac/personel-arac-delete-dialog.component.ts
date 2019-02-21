import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonelArac } from 'app/shared/model/personel-arac.model';
import { PersonelAracService } from './personel-arac.service';

@Component({
    selector: 'jhi-personel-arac-delete-dialog',
    templateUrl: './personel-arac-delete-dialog.component.html'
})
export class PersonelAracDeleteDialogComponent {
    personelArac: IPersonelArac;

    constructor(
        protected personelAracService: PersonelAracService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personelAracService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personelAracListModification',
                content: 'Deleted an personelArac'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personel-arac-delete-popup',
    template: ''
})
export class PersonelAracDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personelArac }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PersonelAracDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.personelArac = personelArac;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/personel-arac', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/personel-arac', { outlets: { popup: null } }]);
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
