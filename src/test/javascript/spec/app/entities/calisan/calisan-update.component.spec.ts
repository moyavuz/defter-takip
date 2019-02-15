/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { CalisanUpdateComponent } from 'app/entities/calisan/calisan-update.component';
import { CalisanService } from 'app/entities/calisan/calisan.service';
import { Calisan } from 'app/shared/model/calisan.model';

describe('Component Tests', () => {
    describe('Calisan Management Update Component', () => {
        let comp: CalisanUpdateComponent;
        let fixture: ComponentFixture<CalisanUpdateComponent>;
        let service: CalisanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [CalisanUpdateComponent]
            })
                .overrideTemplate(CalisanUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CalisanUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalisanService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Calisan(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.calisan = entity;
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
                    const entity = new Calisan();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.calisan = entity;
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
