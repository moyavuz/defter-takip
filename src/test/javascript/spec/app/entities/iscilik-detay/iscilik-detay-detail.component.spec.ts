/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { IscilikDetayDetailComponent } from 'app/entities/iscilik-detay/iscilik-detay-detail.component';
import { IscilikDetay } from 'app/shared/model/iscilik-detay.model';

describe('Component Tests', () => {
    describe('IscilikDetay Management Detail Component', () => {
        let comp: IscilikDetayDetailComponent;
        let fixture: ComponentFixture<IscilikDetayDetailComponent>;
        const route = ({ data: of({ iscilikDetay: new IscilikDetay(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [IscilikDetayDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IscilikDetayDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IscilikDetayDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.iscilikDetay).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
