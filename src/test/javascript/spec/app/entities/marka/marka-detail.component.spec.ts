/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MarkaDetailComponent } from 'app/entities/marka/marka-detail.component';
import { Marka } from 'app/shared/model/marka.model';

describe('Component Tests', () => {
    describe('Marka Management Detail Component', () => {
        let comp: MarkaDetailComponent;
        let fixture: ComponentFixture<MarkaDetailComponent>;
        const route = ({ data: of({ marka: new Marka(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MarkaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MarkaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MarkaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.marka).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
