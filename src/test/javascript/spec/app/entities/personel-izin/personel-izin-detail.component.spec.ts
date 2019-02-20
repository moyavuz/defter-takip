/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelIzinDetailComponent } from 'app/entities/personel-izin/personel-izin-detail.component';
import { PersonelIzin } from 'app/shared/model/personel-izin.model';

describe('Component Tests', () => {
    describe('PersonelIzin Management Detail Component', () => {
        let comp: PersonelIzinDetailComponent;
        let fixture: ComponentFixture<PersonelIzinDetailComponent>;
        const route = ({ data: of({ personelIzin: new PersonelIzin(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelIzinDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PersonelIzinDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonelIzinDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.personelIzin).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
