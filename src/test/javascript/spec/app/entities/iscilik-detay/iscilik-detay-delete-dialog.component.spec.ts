/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { IscilikDetayDeleteDialogComponent } from 'app/entities/iscilik-detay/iscilik-detay-delete-dialog.component';
import { IscilikDetayService } from 'app/entities/iscilik-detay/iscilik-detay.service';

describe('Component Tests', () => {
    describe('IscilikDetay Management Delete Component', () => {
        let comp: IscilikDetayDeleteDialogComponent;
        let fixture: ComponentFixture<IscilikDetayDeleteDialogComponent>;
        let service: IscilikDetayService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [IscilikDetayDeleteDialogComponent]
            })
                .overrideTemplate(IscilikDetayDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IscilikDetayDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IscilikDetayService);
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
