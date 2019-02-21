/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { StokTakipDeleteDialogComponent } from 'app/entities/stok-takip/stok-takip-delete-dialog.component';
import { StokTakipService } from 'app/entities/stok-takip/stok-takip.service';

describe('Component Tests', () => {
    describe('StokTakip Management Delete Component', () => {
        let comp: StokTakipDeleteDialogComponent;
        let fixture: ComponentFixture<StokTakipDeleteDialogComponent>;
        let service: StokTakipService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [StokTakipDeleteDialogComponent]
            })
                .overrideTemplate(StokTakipDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StokTakipDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StokTakipService);
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
