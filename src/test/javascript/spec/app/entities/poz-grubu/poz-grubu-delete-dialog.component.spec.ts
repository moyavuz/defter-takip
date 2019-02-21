/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { PozGrubuDeleteDialogComponent } from 'app/entities/poz-grubu/poz-grubu-delete-dialog.component';
import { PozGrubuService } from 'app/entities/poz-grubu/poz-grubu.service';

describe('Component Tests', () => {
    describe('PozGrubu Management Delete Component', () => {
        let comp: PozGrubuDeleteDialogComponent;
        let fixture: ComponentFixture<PozGrubuDeleteDialogComponent>;
        let service: PozGrubuService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PozGrubuDeleteDialogComponent]
            })
                .overrideTemplate(PozGrubuDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PozGrubuDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PozGrubuService);
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
