/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { AracComponent } from 'app/entities/arac/arac.component';
import { AracService } from 'app/entities/arac/arac.service';
import { Arac } from 'app/shared/model/arac.model';

describe('Component Tests', () => {
    describe('Arac Management Component', () => {
        let comp: AracComponent;
        let fixture: ComponentFixture<AracComponent>;
        let service: AracService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [AracComponent],
                providers: []
            })
                .overrideTemplate(AracComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AracComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AracService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Arac(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.aracs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
