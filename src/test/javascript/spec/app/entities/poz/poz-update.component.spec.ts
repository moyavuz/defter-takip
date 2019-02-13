/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PozUpdateComponent } from 'app/entities/poz/poz-update.component';
import { PozService } from 'app/entities/poz/poz.service';
import { Poz } from 'app/shared/model/poz.model';

describe('Component Tests', () => {
    describe('Poz Management Update Component', () => {
        let comp: PozUpdateComponent;
        let fixture: ComponentFixture<PozUpdateComponent>;
        let service: PozService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PozUpdateComponent]
            })
                .overrideTemplate(PozUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PozUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PozService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Poz(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.poz = entity;
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
                    const entity = new Poz();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.poz = entity;
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
