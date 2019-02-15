/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeGrubuUpdateComponent } from 'app/entities/malzeme-grubu/malzeme-grubu-update.component';
import { MalzemeGrubuService } from 'app/entities/malzeme-grubu/malzeme-grubu.service';
import { MalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

describe('Component Tests', () => {
    describe('MalzemeGrubu Management Update Component', () => {
        let comp: MalzemeGrubuUpdateComponent;
        let fixture: ComponentFixture<MalzemeGrubuUpdateComponent>;
        let service: MalzemeGrubuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeGrubuUpdateComponent]
            })
                .overrideTemplate(MalzemeGrubuUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MalzemeGrubuUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MalzemeGrubuService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MalzemeGrubu(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.malzemeGrubu = entity;
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
                    const entity = new MalzemeGrubu();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.malzemeGrubu = entity;
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
