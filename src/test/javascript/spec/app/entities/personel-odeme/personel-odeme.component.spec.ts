/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelOdemeComponent } from 'app/entities/personel-odeme/personel-odeme.component';
import { PersonelOdemeService } from 'app/entities/personel-odeme/personel-odeme.service';
import { PersonelOdeme } from 'app/shared/model/personel-odeme.model';

describe('Component Tests', () => {
    describe('PersonelOdeme Management Component', () => {
        let comp: PersonelOdemeComponent;
        let fixture: ComponentFixture<PersonelOdemeComponent>;
        let service: PersonelOdemeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelOdemeComponent],
                providers: []
            })
                .overrideTemplate(PersonelOdemeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonelOdemeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonelOdemeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PersonelOdeme(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.personelOdemes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
