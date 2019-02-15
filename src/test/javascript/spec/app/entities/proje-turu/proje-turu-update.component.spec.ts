/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { ProjeTuruUpdateComponent } from 'app/entities/proje-turu/proje-turu-update.component';
import { ProjeTuruService } from 'app/entities/proje-turu/proje-turu.service';
import { ProjeTuru } from 'app/shared/model/proje-turu.model';

describe('Component Tests', () => {
    describe('ProjeTuru Management Update Component', () => {
        let comp: ProjeTuruUpdateComponent;
        let fixture: ComponentFixture<ProjeTuruUpdateComponent>;
        let service: ProjeTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ProjeTuruUpdateComponent]
            })
                .overrideTemplate(ProjeTuruUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjeTuruUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjeTuruService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProjeTuru(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.projeTuru = entity;
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
                    const entity = new ProjeTuru();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.projeTuru = entity;
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
