/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { ProjeUpdateComponent } from 'app/entities/proje/proje-update.component';
import { ProjeService } from 'app/entities/proje/proje.service';
import { Proje } from 'app/shared/model/proje.model';

describe('Component Tests', () => {
    describe('Proje Management Update Component', () => {
        let comp: ProjeUpdateComponent;
        let fixture: ComponentFixture<ProjeUpdateComponent>;
        let service: ProjeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ProjeUpdateComponent]
            })
                .overrideTemplate(ProjeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Proje(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.proje = entity;
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
                    const entity = new Proje();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.proje = entity;
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
