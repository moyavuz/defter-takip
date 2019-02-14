/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PozDetailComponent } from 'app/entities/poz/poz-detail.component';
import { Poz } from 'app/shared/model/poz.model';

describe('Component Tests', () => {
    describe('Poz Management Detail Component', () => {
        let comp: PozDetailComponent;
        let fixture: ComponentFixture<PozDetailComponent>;
        const route = ({ data: of({ poz: new Poz(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PozDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PozDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PozDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.poz).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
