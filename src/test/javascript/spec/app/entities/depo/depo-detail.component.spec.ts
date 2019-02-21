/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { DepoDetailComponent } from 'app/entities/depo/depo-detail.component';
import { Depo } from 'app/shared/model/depo.model';

describe('Component Tests', () => {
    describe('Depo Management Detail Component', () => {
        let comp: DepoDetailComponent;
        let fixture: ComponentFixture<DepoDetailComponent>;
        const route = ({ data: of({ depo: new Depo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [DepoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DepoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.depo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
