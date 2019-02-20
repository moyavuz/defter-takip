/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { DepoComponent } from 'app/entities/depo/depo.component';
import { DepoService } from 'app/entities/depo/depo.service';
import { Depo } from 'app/shared/model/depo.model';

describe('Component Tests', () => {
    describe('Depo Management Component', () => {
        let comp: DepoComponent;
        let fixture: ComponentFixture<DepoComponent>;
        let service: DepoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DepoComponent],
                providers: []
            })
                .overrideTemplate(DepoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Depo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.depos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
