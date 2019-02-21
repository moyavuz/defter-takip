import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonel } from 'app/shared/model/personel.model';
import { PersonelService } from './personel.service';

@Component({
    selector: 'jhi-personel-delete-dialog',
    templateUrl: './personel-delete-dialog.component.html'
})
export class PersonelDeleteDialogComponent {
    personel: IPersonel;

    constructor(protected personelService: PersonelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personelListModification',
                content: 'Deleted an personel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personel-delete-popup',
    template: ''
})
export class PersonelDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PersonelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.personel = personel;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/personel', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/personel', { outlets: { popup: null } }]);
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
