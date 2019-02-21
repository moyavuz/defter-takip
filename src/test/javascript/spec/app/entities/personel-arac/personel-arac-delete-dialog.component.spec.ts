/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelAracDeleteDialogComponent } from 'app/entities/personel-arac/personel-arac-delete-dialog.component';
import { PersonelAracService } from 'app/entities/personel-arac/personel-arac.service';

describe('Component Tests', () => {
    describe('PersonelArac Management Delete Component', () => {
        let comp: PersonelAracDeleteDialogComponent;
        let fixture: ComponentFixture<PersonelAracDeleteDialogComponent>;
        let service: PersonelAracService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelAracDeleteDialogComponent]
            })
                .overrideTemplate(PersonelAracDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonelAracDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelAracService);
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
