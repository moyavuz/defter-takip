/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DefterTakipTestModule } from '../../../test.module';
import { MudurlukDeleteDialogComponent } from 'app/entities/mudurluk/mudurluk-delete-dialog.component';
import { MudurlukService } from 'app/entities/mudurluk/mudurluk.service';

describe('Component Tests', () => {
    describe('Mudurluk Management Delete Component', () => {
        let comp: MudurlukDeleteDialogComponent;
        let fixture: ComponentFixture<MudurlukDeleteDialogComponent>;
        let service: MudurlukService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MudurlukDeleteDialogComponent]
            })
                .overrideTemplate(MudurlukDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MudurlukDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MudurlukService);
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
