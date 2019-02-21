/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { MarkaComponent } from 'app/entities/marka/marka.component';
import { MarkaService } from 'app/entities/marka/marka.service';
import { Marka } from 'app/shared/model/marka.model';

describe('Component Tests', () => {
    describe('Marka Management Component', () => {
        let comp: MarkaComponent;
        let fixture: ComponentFixture<MarkaComponent>;
        let service: MarkaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MarkaComponent],
                providers: []
            })
                .overrideTemplate(MarkaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MarkaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarkaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Marka(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.markas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
