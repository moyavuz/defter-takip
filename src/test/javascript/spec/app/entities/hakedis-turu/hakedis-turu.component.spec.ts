/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { HakedisTuruComponent } from 'app/entities/hakedis-turu/hakedis-turu.component';
import { HakedisTuruService } from 'app/entities/hakedis-turu/hakedis-turu.service';
import { HakedisTuru } from 'app/shared/model/hakedis-turu.model';

describe('Component Tests', () => {
    describe('HakedisTuru Management Component', () => {
        let comp: HakedisTuruComponent;
        let fixture: ComponentFixture<HakedisTuruComponent>;
        let service: HakedisTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [HakedisTuruComponent],
                providers: []
            })
                .overrideTemplate(HakedisTuruComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HakedisTuruComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HakedisTuruService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new HakedisTuru(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.hakedisTurus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
