/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { AracDetailComponent } from 'app/entities/arac/arac-detail.component';
import { Arac } from 'app/shared/model/arac.model';

describe('Component Tests', () => {
    describe('Arac Management Detail Component', () => {
        let comp: AracDetailComponent;
        let fixture: ComponentFixture<AracDetailComponent>;
        const route = ({ data: of({ arac: new Arac(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [AracDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AracDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AracDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.arac).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
