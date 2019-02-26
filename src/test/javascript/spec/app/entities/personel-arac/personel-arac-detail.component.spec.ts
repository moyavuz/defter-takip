/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelAracDetailComponent } from 'app/entities/personel-arac/personel-arac-detail.component';
import { PersonelArac } from 'app/shared/model/personel-arac.model';

describe('Component Tests', () => {
    describe('PersonelArac Management Detail Component', () => {
        let comp: PersonelAracDetailComponent;
        let fixture: ComponentFixture<PersonelAracDetailComponent>;
        const route = ({ data: of({ personelArac: new PersonelArac(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelAracDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PersonelAracDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonelAracDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.personelArac).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
