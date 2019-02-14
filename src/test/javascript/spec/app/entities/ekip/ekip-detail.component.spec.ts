/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { EkipDetailComponent } from 'app/entities/ekip/ekip-detail.component';
import { Ekip } from 'app/shared/model/ekip.model';

describe('Component Tests', () => {
    describe('Ekip Management Detail Component', () => {
        let comp: EkipDetailComponent;
        let fixture: ComponentFixture<EkipDetailComponent>;
        const route = ({ data: of({ ekip: new Ekip(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [EkipDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EkipDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EkipDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ekip).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
