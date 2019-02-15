/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeTakipUpdateComponent } from 'app/entities/malzeme-takip/malzeme-takip-update.component';
import { MalzemeTakipService } from 'app/entities/malzeme-takip/malzeme-takip.service';
import { MalzemeTakip } from 'app/shared/model/malzeme-takip.model';

describe('Component Tests', () => {
    describe('MalzemeTakip Management Update Component', () => {
        let comp: MalzemeTakipUpdateComponent;
        let fixture: ComponentFixture<MalzemeTakipUpdateComponent>;
        let service: MalzemeTakipService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeTakipUpdateComponent]
            })
                .overrideTemplate(MalzemeTakipUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MalzemeTakipUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MalzemeTakipService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MalzemeTakip(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.malzemeTakip = entity;
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
                    const entity = new MalzemeTakip();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.malzemeTakip = entity;
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
