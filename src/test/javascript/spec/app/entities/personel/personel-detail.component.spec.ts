/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PersonelDetailComponent } from 'app/entities/personel/personel-detail.component';
import { Personel } from 'app/shared/model/personel.model';

describe('Component Tests', () => {
    describe('Personel Management Detail Component', () => {
        let comp: PersonelDetailComponent;
        let fixture: ComponentFixture<PersonelDetailComponent>;
        const route = ({ data: of({ personel: new Personel(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PersonelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PersonelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PersonelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.personel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
