/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { IlComponent } from 'app/entities/il/il.component';
import { IlService } from 'app/entities/il/il.service';
import { Il } from 'app/shared/model/il.model';

describe('Component Tests', () => {
    describe('Il Management Component', () => {
        let comp: IlComponent;
        let fixture: ComponentFixture<IlComponent>;
        let service: IlService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [IlComponent],
                providers: []
            })
                .overrideTemplate(IlComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IlComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IlService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Il(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ils[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
