/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelAracUpdateComponent } from 'app/entities/personel-arac/personel-arac-update.component';
import { PersonelAracService } from 'app/entities/personel-arac/personel-arac.service';
import { PersonelArac } from 'app/shared/model/personel-arac.model';

describe('Component Tests', () => {
    describe('PersonelArac Management Update Component', () => {
        let comp: PersonelAracUpdateComponent;
        let fixture: ComponentFixture<PersonelAracUpdateComponent>;
        let service: PersonelAracService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelAracUpdateComponent]
            })
                .overrideTemplate(PersonelAracUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonelAracUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelAracService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PersonelArac(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personelArac = entity;
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
                    const entity = new PersonelArac();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personelArac = entity;
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
