/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelAracComponent } from 'app/entities/personel-arac/personel-arac.component';
import { PersonelAracService } from 'app/entities/personel-arac/personel-arac.service';
import { PersonelArac } from 'app/shared/model/personel-arac.model';

describe('Component Tests', () => {
    describe('PersonelArac Management Component', () => {
        let comp: PersonelAracComponent;
        let fixture: ComponentFixture<PersonelAracComponent>;
        let service: PersonelAracService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelAracComponent],
                providers: []
            })
                .overrideTemplate(PersonelAracComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonelAracComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelAracService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PersonelArac(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.personelAracs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
