/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { ModelDeleteDialogComponent } from 'app/entities/model/model-delete-dialog.component';
import { ModelService } from 'app/entities/model/model.service';

describe('Component Tests', () => {
    describe('Model Management Delete Component', () => {
        let comp: ModelDeleteDialogComponent;
        let fixture: ComponentFixture<ModelDeleteDialogComponent>;
        let service: ModelService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ModelDeleteDialogComponent]
            })
                .overrideTemplate(ModelDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ModelDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModelService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
