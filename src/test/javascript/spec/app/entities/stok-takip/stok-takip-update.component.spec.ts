/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { StokTakipUpdateComponent } from 'app/entities/stok-takip/stok-takip-update.component';
import { StokTakipService } from 'app/entities/stok-takip/stok-takip.service';
import { StokTakip } from 'app/shared/model/stok-takip.model';

describe('Component Tests', () => {
    describe('StokTakip Management Update Component', () => {
        let comp: StokTakipUpdateComponent;
        let fixture: ComponentFixture<StokTakipUpdateComponent>;
        let service: StokTakipService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [StokTakipUpdateComponent]
            })
                .overrideTemplate(StokTakipUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StokTakipUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StokTakipService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StokTakip(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stokTakip = entity;
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
                    const entity = new StokTakip();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stokTakip = entity;
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
