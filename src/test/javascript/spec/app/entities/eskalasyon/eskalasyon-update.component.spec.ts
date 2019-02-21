/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { EskalasyonUpdateComponent } from 'app/entities/eskalasyon/eskalasyon-update.component';
import { EskalasyonService } from 'app/entities/eskalasyon/eskalasyon.service';
import { Eskalasyon } from 'app/shared/model/eskalasyon.model';

describe('Component Tests', () => {
    describe('Eskalasyon Management Update Component', () => {
        let comp: EskalasyonUpdateComponent;
        let fixture: ComponentFixture<EskalasyonUpdateComponent>;
        let service: EskalasyonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [EskalasyonUpdateComponent]
            })
                .overrideTemplate(EskalasyonUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EskalasyonUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EskalasyonService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Eskalasyon(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eskalasyon = entity;
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
                    const entity = new Eskalasyon();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eskalasyon = entity;
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
