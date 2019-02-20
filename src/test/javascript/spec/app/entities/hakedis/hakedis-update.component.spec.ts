/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { HakedisUpdateComponent } from 'app/entities/hakedis/hakedis-update.component';
import { HakedisService } from 'app/entities/hakedis/hakedis.service';
import { Hakedis } from 'app/shared/model/hakedis.model';

describe('Component Tests', () => {
    describe('Hakedis Management Update Component', () => {
        let comp: HakedisUpdateComponent;
        let fixture: ComponentFixture<HakedisUpdateComponent>;
        let service: HakedisService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [HakedisUpdateComponent]
            })
                .overrideTemplate(HakedisUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HakedisUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HakedisService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Hakedis(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hakedis = entity;
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
                    const entity = new Hakedis();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hakedis = entity;
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
