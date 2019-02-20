/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { HakedisDetayDeleteDialogComponent } from 'app/entities/hakedis-detay/hakedis-detay-delete-dialog.component';
import { HakedisDetayService } from 'app/entities/hakedis-detay/hakedis-detay.service';

describe('Component Tests', () => {
    describe('HakedisDetay Management Delete Component', () => {
        let comp: HakedisDetayDeleteDialogComponent;
        let fixture: ComponentFixture<HakedisDetayDeleteDialogComponent>;
        let service: HakedisDetayService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [HakedisDetayDeleteDialogComponent]
            })
                .overrideTemplate(HakedisDetayDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HakedisDetayDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HakedisDetayService);
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
