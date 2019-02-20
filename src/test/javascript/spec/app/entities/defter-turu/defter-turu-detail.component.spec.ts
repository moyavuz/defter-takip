/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { DefterTuruDetailComponent } from 'app/entities/defter-turu/defter-turu-detail.component';
import { DefterTuru } from 'app/shared/model/defter-turu.model';

describe('Component Tests', () => {
    describe('DefterTuru Management Detail Component', () => {
        let comp: DefterTuruDetailComponent;
        let fixture: ComponentFixture<DefterTuruDetailComponent>;
        const route = ({ data: of({ defterTuru: new DefterTuru(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DefterTuruDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DefterTuruDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DefterTuruDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.defterTuru).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
