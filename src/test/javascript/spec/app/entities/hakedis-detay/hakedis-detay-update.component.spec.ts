/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { HakedisDetayUpdateComponent } from 'app/entities/hakedis-detay/hakedis-detay-update.component';
import { HakedisDetayService } from 'app/entities/hakedis-detay/hakedis-detay.service';
import { HakedisDetay } from 'app/shared/model/hakedis-detay.model';

describe('Component Tests', () => {
    describe('HakedisDetay Management Update Component', () => {
        let comp: HakedisDetayUpdateComponent;
        let fixture: ComponentFixture<HakedisDetayUpdateComponent>;
        let service: HakedisDetayService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [HakedisDetayUpdateComponent]
            })
                .overrideTemplate(HakedisDetayUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HakedisDetayUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HakedisDetayService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HakedisDetay(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hakedisDetay = entity;
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
                    const entity = new HakedisDetay();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hakedisDetay = entity;
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
