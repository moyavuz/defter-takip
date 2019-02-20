/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelOdemeUpdateComponent } from 'app/entities/personel-odeme/personel-odeme-update.component';
import { PersonelOdemeService } from 'app/entities/personel-odeme/personel-odeme.service';
import { PersonelOdeme } from 'app/shared/model/personel-odeme.model';

describe('Component Tests', () => {
    describe('PersonelOdeme Management Update Component', () => {
        let comp: PersonelOdemeUpdateComponent;
        let fixture: ComponentFixture<PersonelOdemeUpdateComponent>;
        let service: PersonelOdemeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelOdemeUpdateComponent]
            })
                .overrideTemplate(PersonelOdemeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonelOdemeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelOdemeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PersonelOdeme(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personelOdeme = entity;
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
                    const entity = new PersonelOdeme();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personelOdeme = entity;
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
