/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelUpdateComponent } from 'app/entities/personel/personel-update.component';
import { PersonelService } from 'app/entities/personel/personel.service';
import { Personel } from 'app/shared/model/personel.model';

describe('Component Tests', () => {
    describe('Personel Management Update Component', () => {
        let comp: PersonelUpdateComponent;
        let fixture: ComponentFixture<PersonelUpdateComponent>;
        let service: PersonelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelUpdateComponent]
            })
                .overrideTemplate(PersonelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Personel(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personel = entity;
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
                    const entity = new Personel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.personel = entity;
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
