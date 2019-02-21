/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelZimmetDeleteDialogComponent } from 'app/entities/personel-zimmet/personel-zimmet-delete-dialog.component';
import { PersonelZimmetService } from 'app/entities/personel-zimmet/personel-zimmet.service';

describe('Component Tests', () => {
    describe('PersonelZimmet Management Delete Component', () => {
        let comp: PersonelZimmetDeleteDialogComponent;
        let fixture: ComponentFixture<PersonelZimmetDeleteDialogComponent>;
        let service: PersonelZimmetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelZimmetDeleteDialogComponent]
            })
                .overrideTemplate(PersonelZimmetDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonelZimmetDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelZimmetService);
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
