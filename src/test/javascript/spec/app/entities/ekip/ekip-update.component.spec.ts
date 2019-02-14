/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { EkipUpdateComponent } from 'app/entities/ekip/ekip-update.component';
import { EkipService } from 'app/entities/ekip/ekip.service';
import { Ekip } from 'app/shared/model/ekip.model';

describe('Component Tests', () => {
    describe('Ekip Management Update Component', () => {
        let comp: EkipUpdateComponent;
        let fixture: ComponentFixture<EkipUpdateComponent>;
        let service: EkipService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [EkipUpdateComponent]
            })
                .overrideTemplate(EkipUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EkipUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EkipService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Ekip(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ekip = entity;
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
                    const entity = new Ekip();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ekip = entity;
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
