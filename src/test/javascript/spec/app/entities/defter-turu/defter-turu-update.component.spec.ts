/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { DefterTuruUpdateComponent } from 'app/entities/defter-turu/defter-turu-update.component';
import { DefterTuruService } from 'app/entities/defter-turu/defter-turu.service';
import { DefterTuru } from 'app/shared/model/defter-turu.model';

describe('Component Tests', () => {
    describe('DefterTuru Management Update Component', () => {
        let comp: DefterTuruUpdateComponent;
        let fixture: ComponentFixture<DefterTuruUpdateComponent>;
        let service: DefterTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DefterTuruUpdateComponent]
            })
                .overrideTemplate(DefterTuruUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DefterTuruUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefterTuruService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DefterTuru(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.defterTuru = entity;
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
                    const entity = new DefterTuru();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.defterTuru = entity;
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
