/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { SantralUpdateComponent } from 'app/entities/santral/santral-update.component';
import { SantralService } from 'app/entities/santral/santral.service';
import { Santral } from 'app/shared/model/santral.model';

describe('Component Tests', () => {
    describe('Santral Management Update Component', () => {
        let comp: SantralUpdateComponent;
        let fixture: ComponentFixture<SantralUpdateComponent>;
        let service: SantralService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [SantralUpdateComponent]
            })
                .overrideTemplate(SantralUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SantralUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SantralService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Santral(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.santral = entity;
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
                    const entity = new Santral();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.santral = entity;
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
