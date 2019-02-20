/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { AracUpdateComponent } from 'app/entities/arac/arac-update.component';
import { AracService } from 'app/entities/arac/arac.service';
import { Arac } from 'app/shared/model/arac.model';

describe('Component Tests', () => {
    describe('Arac Management Update Component', () => {
        let comp: AracUpdateComponent;
        let fixture: ComponentFixture<AracUpdateComponent>;
        let service: AracService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [AracUpdateComponent]
            })
                .overrideTemplate(AracUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AracUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AracService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Arac(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.arac = entity;
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
                    const entity = new Arac();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.arac = entity;
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
