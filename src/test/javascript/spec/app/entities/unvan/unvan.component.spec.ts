/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { UnvanComponent } from 'app/entities/unvan/unvan.component';
import { UnvanService } from 'app/entities/unvan/unvan.service';
import { Unvan } from 'app/shared/model/unvan.model';

describe('Component Tests', () => {
    describe('Unvan Management Component', () => {
        let comp: UnvanComponent;
        let fixture: ComponentFixture<UnvanComponent>;
        let service: UnvanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [UnvanComponent],
                providers: []
            })
                .overrideTemplate(UnvanComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UnvanComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnvanService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Unvan(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.unvans[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
