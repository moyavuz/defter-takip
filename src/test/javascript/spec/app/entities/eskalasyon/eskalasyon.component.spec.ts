/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { EskalasyonComponent } from 'app/entities/eskalasyon/eskalasyon.component';
import { EskalasyonService } from 'app/entities/eskalasyon/eskalasyon.service';
import { Eskalasyon } from 'app/shared/model/eskalasyon.model';

describe('Component Tests', () => {
    describe('Eskalasyon Management Component', () => {
        let comp: EskalasyonComponent;
        let fixture: ComponentFixture<EskalasyonComponent>;
        let service: EskalasyonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [EskalasyonComponent],
                providers: []
            })
                .overrideTemplate(EskalasyonComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EskalasyonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EskalasyonService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Eskalasyon(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.eskalasyons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
