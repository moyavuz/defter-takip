/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { ProjeTuruDeleteDialogComponent } from 'app/entities/proje-turu/proje-turu-delete-dialog.component';
import { ProjeTuruService } from 'app/entities/proje-turu/proje-turu.service';

describe('Component Tests', () => {
    describe('ProjeTuru Management Delete Component', () => {
        let comp: ProjeTuruDeleteDialogComponent;
        let fixture: ComponentFixture<ProjeTuruDeleteDialogComponent>;
        let service: ProjeTuruService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ProjeTuruDeleteDialogComponent]
            })
                .overrideTemplate(ProjeTuruDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjeTuruDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjeTuruService);
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
