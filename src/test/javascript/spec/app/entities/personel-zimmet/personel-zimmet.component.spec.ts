/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelZimmetComponent } from 'app/entities/personel-zimmet/personel-zimmet.component';
import { PersonelZimmetService } from 'app/entities/personel-zimmet/personel-zimmet.service';
import { PersonelZimmet } from 'app/shared/model/personel-zimmet.model';

describe('Component Tests', () => {
    describe('PersonelZimmet Management Component', () => {
        let comp: PersonelZimmetComponent;
        let fixture: ComponentFixture<PersonelZimmetComponent>;
        let service: PersonelZimmetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelZimmetComponent],
                providers: []
            })
                .overrideTemplate(PersonelZimmetComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonelZimmetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelZimmetService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PersonelZimmet(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.personelZimmets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
