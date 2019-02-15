/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeGrubuDeleteDialogComponent } from 'app/entities/malzeme-grubu/malzeme-grubu-delete-dialog.component';
import { MalzemeGrubuService } from 'app/entities/malzeme-grubu/malzeme-grubu.service';

describe('Component Tests', () => {
    describe('MalzemeGrubu Management Delete Component', () => {
        let comp: MalzemeGrubuDeleteDialogComponent;
        let fixture: ComponentFixture<MalzemeGrubuDeleteDialogComponent>;
        let service: MalzemeGrubuService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeGrubuDeleteDialogComponent]
            })
                .overrideTemplate(MalzemeGrubuDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MalzemeGrubuDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MalzemeGrubuService);
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
