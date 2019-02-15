/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { BirimComponent } from 'app/entities/birim/birim.component';
import { BirimService } from 'app/entities/birim/birim.service';
import { Birim } from 'app/shared/model/birim.model';

describe('Component Tests', () => {
    describe('Birim Management Component', () => {
        let comp: BirimComponent;
        let fixture: ComponentFixture<BirimComponent>;
        let service: BirimService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [BirimComponent],
                providers: []
            })
                .overrideTemplate(BirimComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BirimComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BirimService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Birim(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.birims[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
