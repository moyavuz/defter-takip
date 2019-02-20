/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelZimmetUpdateComponent } from 'app/entities/personel-zimmet/personel-zimmet-update.component';
import { PersonelZimmetService } from 'app/entities/personel-zimmet/personel-zimmet.service';
import { PersonelZimmet } from 'app/shared/model/personel-zimmet.model';

describe('Component Tests', () => {
    describe('PersonelZimmet Management Update Component', () => {
        let comp: PersonelZimmetUpdateComponent;
        let fixture: ComponentFixture<PersonelZimmetUpdateComponent>;
        let service: PersonelZimmetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelZimmetUpdateComponent]
            })
                .overrideTemplate(PersonelZimmetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonelZimmetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelZimmetService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PersonelZimmet(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personelZimmet = entity;
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
                    const entity = new PersonelZimmet();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personelZimmet = entity;
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
