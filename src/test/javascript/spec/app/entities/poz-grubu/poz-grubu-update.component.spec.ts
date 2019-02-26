/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PozGrubuUpdateComponent } from 'app/entities/poz-grubu/poz-grubu-update.component';
import { PozGrubuService } from 'app/entities/poz-grubu/poz-grubu.service';
import { PozGrubu } from 'app/shared/model/poz-grubu.model';

describe('Component Tests', () => {
    describe('PozGrubu Management Update Component', () => {
        let comp: PozGrubuUpdateComponent;
        let fixture: ComponentFixture<PozGrubuUpdateComponent>;
        let service: PozGrubuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PozGrubuUpdateComponent]
            })
                .overrideTemplate(PozGrubuUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PozGrubuUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PozGrubuService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PozGrubu(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pozGrubu = entity;
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
                    const entity = new PozGrubu();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pozGrubu = entity;
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
