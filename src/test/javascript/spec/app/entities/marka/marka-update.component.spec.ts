/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MarkaUpdateComponent } from 'app/entities/marka/marka-update.component';
import { MarkaService } from 'app/entities/marka/marka.service';
import { Marka } from 'app/shared/model/marka.model';

describe('Component Tests', () => {
    describe('Marka Management Update Component', () => {
        let comp: MarkaUpdateComponent;
        let fixture: ComponentFixture<MarkaUpdateComponent>;
        let service: MarkaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MarkaUpdateComponent]
            })
                .overrideTemplate(MarkaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MarkaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarkaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Marka(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.marka = entity;
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
                    const entity = new Marka();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.marka = entity;
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
