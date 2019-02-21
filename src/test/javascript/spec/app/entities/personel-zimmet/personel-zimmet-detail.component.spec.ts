/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelZimmetDetailComponent } from 'app/entities/personel-zimmet/personel-zimmet-detail.component';
import { PersonelZimmet } from 'app/shared/model/personel-zimmet.model';

describe('Component Tests', () => {
    describe('PersonelZimmet Management Detail Component', () => {
        let comp: PersonelZimmetDetailComponent;
        let fixture: ComponentFixture<PersonelZimmetDetailComponent>;
        const route = ({ data: of({ personelZimmet: new PersonelZimmet(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelZimmetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PersonelZimmetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonelZimmetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.personelZimmet).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
