/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { DepartmanComponent } from 'app/entities/departman/departman.component';
import { DepartmanService } from 'app/entities/departman/departman.service';
import { Departman } from 'app/shared/model/departman.model';

describe('Component Tests', () => {
    describe('Departman Management Component', () => {
        let comp: DepartmanComponent;
        let fixture: ComponentFixture<DepartmanComponent>;
        let service: DepartmanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DepartmanComponent],
                providers: []
            })
                .overrideTemplate(DepartmanComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepartmanComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepartmanService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Departman(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.departmen[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
