/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelOdemeDetailComponent } from 'app/entities/personel-odeme/personel-odeme-detail.component';
import { PersonelOdeme } from 'app/shared/model/personel-odeme.model';

describe('Component Tests', () => {
    describe('PersonelOdeme Management Detail Component', () => {
        let comp: PersonelOdemeDetailComponent;
        let fixture: ComponentFixture<PersonelOdemeDetailComponent>;
        const route = ({ data: of({ personelOdeme: new PersonelOdeme(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelOdemeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PersonelOdemeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonelOdemeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.personelOdeme).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
