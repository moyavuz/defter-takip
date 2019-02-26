/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { ZimmetTuruUpdateComponent } from 'app/entities/zimmet-turu/zimmet-turu-update.component';
import { ZimmetTuruService } from 'app/entities/zimmet-turu/zimmet-turu.service';
import { ZimmetTuru } from 'app/shared/model/zimmet-turu.model';

describe('Component Tests', () => {
    describe('ZimmetTuru Management Update Component', () => {
        let comp: ZimmetTuruUpdateComponent;
        let fixture: ComponentFixture<ZimmetTuruUpdateComponent>;
        let service: ZimmetTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ZimmetTuruUpdateComponent]
            })
                .overrideTemplate(ZimmetTuruUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ZimmetTuruUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZimmetTuruService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ZimmetTuru(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.zimmetTuru = entity;
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
                    const entity = new ZimmetTuru();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.zimmetTuru = entity;
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
