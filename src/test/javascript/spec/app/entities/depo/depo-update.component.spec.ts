/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { DepoUpdateComponent } from 'app/entities/depo/depo-update.component';
import { DepoService } from 'app/entities/depo/depo.service';
import { Depo } from 'app/shared/model/depo.model';

describe('Component Tests', () => {
    describe('Depo Management Update Component', () => {
        let comp: DepoUpdateComponent;
        let fixture: ComponentFixture<DepoUpdateComponent>;
        let service: DepoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DepoUpdateComponent]
            })
                .overrideTemplate(DepoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Depo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.depo = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Depo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.depo = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
