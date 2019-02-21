/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { IlUpdateComponent } from 'app/entities/il/il-update.component';
import { IlService } from 'app/entities/il/il.service';
import { Il } from 'app/shared/model/il.model';

describe('Component Tests', () => {
    describe('Il Management Update Component', () => {
        let comp: IlUpdateComponent;
        let fixture: ComponentFixture<IlUpdateComponent>;
        let service: IlService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [IlUpdateComponent]
            })
                .overrideTemplate(IlUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IlUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IlService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Il(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.il = entity;
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
                    const entity = new Il();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.il = entity;
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
