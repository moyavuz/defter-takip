/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { IscilikDetayUpdateComponent } from 'app/entities/iscilik-detay/iscilik-detay-update.component';
import { IscilikDetayService } from 'app/entities/iscilik-detay/iscilik-detay.service';
import { IscilikDetay } from 'app/shared/model/iscilik-detay.model';

describe('Component Tests', () => {
    describe('IscilikDetay Management Update Component', () => {
        let comp: IscilikDetayUpdateComponent;
        let fixture: ComponentFixture<IscilikDetayUpdateComponent>;
        let service: IscilikDetayService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [IscilikDetayUpdateComponent]
            })
                .overrideTemplate(IscilikDetayUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IscilikDetayUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IscilikDetayService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IscilikDetay(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iscilikDetay = entity;
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
                    const entity = new IscilikDetay();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iscilikDetay = entity;
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
