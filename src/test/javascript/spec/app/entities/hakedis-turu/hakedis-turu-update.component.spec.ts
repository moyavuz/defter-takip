/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { HakedisTuruUpdateComponent } from 'app/entities/hakedis-turu/hakedis-turu-update.component';
import { HakedisTuruService } from 'app/entities/hakedis-turu/hakedis-turu.service';
import { HakedisTuru } from 'app/shared/model/hakedis-turu.model';

describe('Component Tests', () => {
    describe('HakedisTuru Management Update Component', () => {
        let comp: HakedisTuruUpdateComponent;
        let fixture: ComponentFixture<HakedisTuruUpdateComponent>;
        let service: HakedisTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [HakedisTuruUpdateComponent]
            })
                .overrideTemplate(HakedisTuruUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HakedisTuruUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HakedisTuruService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HakedisTuru(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hakedisTuru = entity;
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
                    const entity = new HakedisTuru();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hakedisTuru = entity;
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
