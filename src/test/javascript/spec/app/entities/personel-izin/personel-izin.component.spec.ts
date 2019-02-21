/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelIzinComponent } from 'app/entities/personel-izin/personel-izin.component';
import { PersonelIzinService } from 'app/entities/personel-izin/personel-izin.service';
import { PersonelIzin } from 'app/shared/model/personel-izin.model';

describe('Component Tests', () => {
    describe('PersonelIzin Management Component', () => {
        let comp: PersonelIzinComponent;
        let fixture: ComponentFixture<PersonelIzinComponent>;
        let service: PersonelIzinService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelIzinComponent],
                providers: []
            })
                .overrideTemplate(PersonelIzinComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonelIzinComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelIzinService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PersonelIzin(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.personelIzins[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
