/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { BolgeUpdateComponent } from 'app/entities/bolge/bolge-update.component';
import { BolgeService } from 'app/entities/bolge/bolge.service';
import { Bolge } from 'app/shared/model/bolge.model';

describe('Component Tests', () => {
    describe('Bolge Management Update Component', () => {
        let comp: BolgeUpdateComponent;
        let fixture: ComponentFixture<BolgeUpdateComponent>;
        let service: BolgeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [BolgeUpdateComponent]
            })
                .overrideTemplate(BolgeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BolgeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BolgeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bolge(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bolge = entity;
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
                    const entity = new Bolge();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bolge = entity;
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
