/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeUpdateComponent } from 'app/entities/malzeme/malzeme-update.component';
import { MalzemeService } from 'app/entities/malzeme/malzeme.service';
import { Malzeme } from 'app/shared/model/malzeme.model';

describe('Component Tests', () => {
    describe('Malzeme Management Update Component', () => {
        let comp: MalzemeUpdateComponent;
        let fixture: ComponentFixture<MalzemeUpdateComponent>;
        let service: MalzemeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeUpdateComponent]
            })
                .overrideTemplate(MalzemeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MalzemeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MalzemeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Malzeme(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.malzeme = entity;
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
                    const entity = new Malzeme();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.malzeme = entity;
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
