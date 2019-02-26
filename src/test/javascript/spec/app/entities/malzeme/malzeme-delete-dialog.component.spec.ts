/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeDeleteDialogComponent } from 'app/entities/malzeme/malzeme-delete-dialog.component';
import { MalzemeService } from 'app/entities/malzeme/malzeme.service';

describe('Component Tests', () => {
    describe('Malzeme Management Delete Component', () => {
        let comp: MalzemeDeleteDialogComponent;
        let fixture: ComponentFixture<MalzemeDeleteDialogComponent>;
        let service: MalzemeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeDeleteDialogComponent]
            })
                .overrideTemplate(MalzemeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MalzemeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MalzemeService);
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
