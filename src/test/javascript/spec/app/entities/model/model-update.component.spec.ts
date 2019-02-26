/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { ModelUpdateComponent } from 'app/entities/model/model-update.component';
import { ModelService } from 'app/entities/model/model.service';
import { Model } from 'app/shared/model/model.model';

describe('Component Tests', () => {
    describe('Model Management Update Component', () => {
        let comp: ModelUpdateComponent;
        let fixture: ComponentFixture<ModelUpdateComponent>;
        let service: ModelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ModelUpdateComponent]
            })
                .overrideTemplate(ModelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ModelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Model(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.model = entity;
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
                    const entity = new Model();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.model = entity;
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
