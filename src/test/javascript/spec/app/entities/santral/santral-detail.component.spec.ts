/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { SantralDetailComponent } from 'app/entities/santral/santral-detail.component';
import { Santral } from 'app/shared/model/santral.model';

describe('Component Tests', () => {
    describe('Santral Management Detail Component', () => {
        let comp: SantralDetailComponent;
        let fixture: ComponentFixture<SantralDetailComponent>;
        const route = ({ data: of({ santral: new Santral(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [SantralDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SantralDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SantralDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.santral).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
