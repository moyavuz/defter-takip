/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelOdemeDeleteDialogComponent } from 'app/entities/personel-odeme/personel-odeme-delete-dialog.component';
import { PersonelOdemeService } from 'app/entities/personel-odeme/personel-odeme.service';

describe('Component Tests', () => {
    describe('PersonelOdeme Management Delete Component', () => {
        let comp: PersonelOdemeDeleteDialogComponent;
        let fixture: ComponentFixture<PersonelOdemeDeleteDialogComponent>;
        let service: PersonelOdemeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelOdemeDeleteDialogComponent]
            })
                .overrideTemplate(PersonelOdemeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonelOdemeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelOdemeService);
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
