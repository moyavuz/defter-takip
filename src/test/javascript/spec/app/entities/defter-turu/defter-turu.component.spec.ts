/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { DefterTuruComponent } from 'app/entities/defter-turu/defter-turu.component';
import { DefterTuruService } from 'app/entities/defter-turu/defter-turu.service';
import { DefterTuru } from 'app/shared/model/defter-turu.model';

describe('Component Tests', () => {
    describe('DefterTuru Management Component', () => {
        let comp: DefterTuruComponent;
        let fixture: ComponentFixture<DefterTuruComponent>;
        let service: DefterTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DefterTuruComponent],
                providers: []
            })
                .overrideTemplate(DefterTuruComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DefterTuruComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefterTuruService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DefterTuru(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.defterTurus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
