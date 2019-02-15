/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { IscilikUpdateComponent } from 'app/entities/iscilik/iscilik-update.component';
import { IscilikService } from 'app/entities/iscilik/iscilik.service';
import { Iscilik } from 'app/shared/model/iscilik.model';

describe('Component Tests', () => {
    describe('Iscilik Management Update Component', () => {
        let comp: IscilikUpdateComponent;
        let fixture: ComponentFixture<IscilikUpdateComponent>;
        let service: IscilikService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [IscilikUpdateComponent]
            })
                .overrideTemplate(IscilikUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IscilikUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IscilikService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Iscilik(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iscilik = entity;
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
                    const entity = new Iscilik();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iscilik = entity;
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
