/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { EskalasyonTuruDetailComponent } from 'app/entities/eskalasyon-turu/eskalasyon-turu-detail.component';
import { EskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';

describe('Component Tests', () => {
    describe('EskalasyonTuru Management Detail Component', () => {
        let comp: EskalasyonTuruDetailComponent;
        let fixture: ComponentFixture<EskalasyonTuruDetailComponent>;
        const route = ({ data: of({ eskalasyonTuru: new EskalasyonTuru(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [EskalasyonTuruDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EskalasyonTuruDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EskalasyonTuruDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.eskalasyonTuru).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
