/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { HakedisTuruDetailComponent } from 'app/entities/hakedis-turu/hakedis-turu-detail.component';
import { HakedisTuru } from 'app/shared/model/hakedis-turu.model';

describe('Component Tests', () => {
    describe('HakedisTuru Management Detail Component', () => {
        let comp: HakedisTuruDetailComponent;
        let fixture: ComponentFixture<HakedisTuruDetailComponent>;
        const route = ({ data: of({ hakedisTuru: new HakedisTuru(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [HakedisTuruDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HakedisTuruDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HakedisTuruDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hakedisTuru).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
