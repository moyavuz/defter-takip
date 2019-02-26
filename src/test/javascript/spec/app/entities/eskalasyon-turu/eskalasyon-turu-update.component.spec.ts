/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { EskalasyonTuruUpdateComponent } from 'app/entities/eskalasyon-turu/eskalasyon-turu-update.component';
import { EskalasyonTuruService } from 'app/entities/eskalasyon-turu/eskalasyon-turu.service';
import { EskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';

describe('Component Tests', () => {
    describe('EskalasyonTuru Management Update Component', () => {
        let comp: EskalasyonTuruUpdateComponent;
        let fixture: ComponentFixture<EskalasyonTuruUpdateComponent>;
        let service: EskalasyonTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [EskalasyonTuruUpdateComponent]
            })
                .overrideTemplate(EskalasyonTuruUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EskalasyonTuruUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EskalasyonTuruService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EskalasyonTuru(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eskalasyonTuru = entity;
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
                    const entity = new EskalasyonTuru();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eskalasyonTuru = entity;
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
