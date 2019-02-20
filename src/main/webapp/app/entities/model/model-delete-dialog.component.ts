import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModel } from 'app/shared/model/model.model';
import { ModelService } from './model.service';

@Component({
    selector: 'jhi-model-delete-dialog',
    templateUrl: './model-delete-dialog.component.html'
})
export class ModelDeleteDialogComponent {
    model: IModel;

    constructor(protected modelService: ModelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.modelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'modelListModification',
                content: 'Deleted an model'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-model-delete-popup',
    template: ''
})
export class ModelDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ model }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ModelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.model = model;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/model', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/model', { outlets: { popup: null } }]);
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
