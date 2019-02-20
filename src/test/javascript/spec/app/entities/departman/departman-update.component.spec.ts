/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { DepartmanUpdateComponent } from 'app/entities/departman/departman-update.component';
import { DepartmanService } from 'app/entities/departman/departman.service';
import { Departman } from 'app/shared/model/departman.model';

describe('Component Tests', () => {
    describe('Departman Management Update Component', () => {
        let comp: DepartmanUpdateComponent;
        let fixture: ComponentFixture<DepartmanUpdateComponent>;
        let service: DepartmanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DepartmanUpdateComponent]
            })
                .overrideTemplate(DepartmanUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepartmanUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepartmanService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Departman(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.departman = entity;
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
                    const entity = new Departman();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.departman = entity;
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
