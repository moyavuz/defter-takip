/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { ProjeTuruComponent } from 'app/entities/proje-turu/proje-turu.component';
import { ProjeTuruService } from 'app/entities/proje-turu/proje-turu.service';
import { ProjeTuru } from 'app/shared/model/proje-turu.model';

describe('Component Tests', () => {
    describe('ProjeTuru Management Component', () => {
        let comp: ProjeTuruComponent;
        let fixture: ComponentFixture<ProjeTuruComponent>;
        let service: ProjeTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ProjeTuruComponent],
                providers: []
            })
                .overrideTemplate(ProjeTuruComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjeTuruComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjeTuruService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProjeTuru(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.projeTurus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
