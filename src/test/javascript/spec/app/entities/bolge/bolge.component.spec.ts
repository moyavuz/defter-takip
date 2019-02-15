/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { BolgeComponent } from 'app/entities/bolge/bolge.component';
import { BolgeService } from 'app/entities/bolge/bolge.service';
import { Bolge } from 'app/shared/model/bolge.model';

describe('Component Tests', () => {
    describe('Bolge Management Component', () => {
        let comp: BolgeComponent;
        let fixture: ComponentFixture<BolgeComponent>;
        let service: BolgeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [BolgeComponent],
                providers: []
            })
                .overrideTemplate(BolgeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BolgeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BolgeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Bolge(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bolges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
