/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MudurlukUpdateComponent } from 'app/entities/mudurluk/mudurluk-update.component';
import { MudurlukService } from 'app/entities/mudurluk/mudurluk.service';
import { Mudurluk } from 'app/shared/model/mudurluk.model';

describe('Component Tests', () => {
    describe('Mudurluk Management Update Component', () => {
        let comp: MudurlukUpdateComponent;
        let fixture: ComponentFixture<MudurlukUpdateComponent>;
        let service: MudurlukService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MudurlukUpdateComponent]
            })
                .overrideTemplate(MudurlukUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MudurlukUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MudurlukService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Mudurluk(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mudurluk = entity;
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
                    const entity = new Mudurluk();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mudurluk = entity;
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
