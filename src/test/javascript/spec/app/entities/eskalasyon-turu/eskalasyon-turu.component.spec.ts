/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { EskalasyonTuruComponent } from 'app/entities/eskalasyon-turu/eskalasyon-turu.component';
import { EskalasyonTuruService } from 'app/entities/eskalasyon-turu/eskalasyon-turu.service';
import { EskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';

describe('Component Tests', () => {
    describe('EskalasyonTuru Management Component', () => {
        let comp: EskalasyonTuruComponent;
        let fixture: ComponentFixture<EskalasyonTuruComponent>;
        let service: EskalasyonTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [EskalasyonTuruComponent],
                providers: []
            })
                .overrideTemplate(EskalasyonTuruComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EskalasyonTuruComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EskalasyonTuruService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EskalasyonTuru(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.eskalasyonTurus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
