/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { DovizKurComponent } from 'app/entities/doviz-kur/doviz-kur.component';
import { DovizKurService } from 'app/entities/doviz-kur/doviz-kur.service';
import { DovizKur } from 'app/shared/model/doviz-kur.model';

describe('Component Tests', () => {
    describe('DovizKur Management Component', () => {
        let comp: DovizKurComponent;
        let fixture: ComponentFixture<DovizKurComponent>;
        let service: DovizKurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DovizKurComponent],
                providers: []
            })
                .overrideTemplate(DovizKurComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DovizKurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DovizKurService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DovizKur(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dovizKurs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
