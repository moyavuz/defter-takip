/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { DovizKurUpdateComponent } from 'app/entities/doviz-kur/doviz-kur-update.component';
import { DovizKurService } from 'app/entities/doviz-kur/doviz-kur.service';
import { DovizKur } from 'app/shared/model/doviz-kur.model';

describe('Component Tests', () => {
    describe('DovizKur Management Update Component', () => {
        let comp: DovizKurUpdateComponent;
        let fixture: ComponentFixture<DovizKurUpdateComponent>;
        let service: DovizKurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DovizKurUpdateComponent]
            })
                .overrideTemplate(DovizKurUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DovizKurUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DovizKurService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DovizKur(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dovizKur = entity;
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
                    const entity = new DovizKur();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dovizKur = entity;
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
