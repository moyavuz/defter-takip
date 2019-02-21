/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelIzinUpdateComponent } from 'app/entities/personel-izin/personel-izin-update.component';
import { PersonelIzinService } from 'app/entities/personel-izin/personel-izin.service';
import { PersonelIzin } from 'app/shared/model/personel-izin.model';

describe('Component Tests', () => {
    describe('PersonelIzin Management Update Component', () => {
        let comp: PersonelIzinUpdateComponent;
        let fixture: ComponentFixture<PersonelIzinUpdateComponent>;
        let service: PersonelIzinService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelIzinUpdateComponent]
            })
                .overrideTemplate(PersonelIzinUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonelIzinUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelIzinService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PersonelIzin(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personelIzin = entity;
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
                    const entity = new PersonelIzin();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personelIzin = entity;
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
