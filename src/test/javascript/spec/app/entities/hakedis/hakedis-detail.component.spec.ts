/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { HakedisDetailComponent } from 'app/entities/hakedis/hakedis-detail.component';
import { Hakedis } from 'app/shared/model/hakedis.model';

describe('Component Tests', () => {
    describe('Hakedis Management Detail Component', () => {
        let comp: HakedisDetailComponent;
        let fixture: ComponentFixture<HakedisDetailComponent>;
        const route = ({ data: of({ hakedis: new Hakedis(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [HakedisDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HakedisDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HakedisDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hakedis).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
