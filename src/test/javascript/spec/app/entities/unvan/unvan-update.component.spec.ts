/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { UnvanUpdateComponent } from 'app/entities/unvan/unvan-update.component';
import { UnvanService } from 'app/entities/unvan/unvan.service';
import { Unvan } from 'app/shared/model/unvan.model';

describe('Component Tests', () => {
    describe('Unvan Management Update Component', () => {
        let comp: UnvanUpdateComponent;
        let fixture: ComponentFixture<UnvanUpdateComponent>;
        let service: UnvanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [UnvanUpdateComponent]
            })
                .overrideTemplate(UnvanUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UnvanUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnvanService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Unvan(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.unvan = entity;
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
                    const entity = new Unvan();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.unvan = entity;
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
