/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { DefterTuruDeleteDialogComponent } from 'app/entities/defter-turu/defter-turu-delete-dialog.component';
import { DefterTuruService } from 'app/entities/defter-turu/defter-turu.service';

describe('Component Tests', () => {
    describe('DefterTuru Management Delete Component', () => {
        let comp: DefterTuruDeleteDialogComponent;
        let fixture: ComponentFixture<DefterTuruDeleteDialogComponent>;
        let service: DefterTuruService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DefterTuruDeleteDialogComponent]
            })
                .overrideTemplate(DefterTuruDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DefterTuruDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefterTuruService);
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
