/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { SantralComponent } from 'app/entities/santral/santral.component';
import { SantralService } from 'app/entities/santral/santral.service';
import { Santral } from 'app/shared/model/santral.model';

describe('Component Tests', () => {
    describe('Santral Management Component', () => {
        let comp: SantralComponent;
        let fixture: ComponentFixture<SantralComponent>;
        let service: SantralService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [SantralComponent],
                providers: []
            })
                .overrideTemplate(SantralComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SantralComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SantralService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Santral(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.santrals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
