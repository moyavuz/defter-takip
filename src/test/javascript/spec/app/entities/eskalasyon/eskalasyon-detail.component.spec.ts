/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { EskalasyonDetailComponent } from 'app/entities/eskalasyon/eskalasyon-detail.component';
import { Eskalasyon } from 'app/shared/model/eskalasyon.model';

describe('Component Tests', () => {
    describe('Eskalasyon Management Detail Component', () => {
        let comp: EskalasyonDetailComponent;
        let fixture: ComponentFixture<EskalasyonDetailComponent>;
        const route = ({ data: of({ eskalasyon: new Eskalasyon(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [EskalasyonDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EskalasyonDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EskalasyonDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.eskalasyon).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
