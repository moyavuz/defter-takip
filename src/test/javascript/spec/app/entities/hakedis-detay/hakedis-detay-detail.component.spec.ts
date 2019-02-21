/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { HakedisDetayDetailComponent } from 'app/entities/hakedis-detay/hakedis-detay-detail.component';
import { HakedisDetay } from 'app/shared/model/hakedis-detay.model';

describe('Component Tests', () => {
    describe('HakedisDetay Management Detail Component', () => {
        let comp: HakedisDetayDetailComponent;
        let fixture: ComponentFixture<HakedisDetayDetailComponent>;
        const route = ({ data: of({ hakedisDetay: new HakedisDetay(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [HakedisDetayDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HakedisDetayDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HakedisDetayDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hakedisDetay).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
